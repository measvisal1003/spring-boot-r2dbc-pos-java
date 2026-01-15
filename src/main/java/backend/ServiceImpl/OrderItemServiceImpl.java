package backend.ServiceImpl;

import backend.Dto.OrderDetails;
import backend.Dto.OrderRequest;
import backend.Entities.OrderDetail;
import backend.Entities.OrderItem;
import backend.Entities.Product;
import backend.Repository.OrderDetailRepository;
import backend.Repository.OrderItemRepository;
import backend.Repository.ProductRepository;
import backend.Service.OrderItemService;
import backend.Service.UserService;
import backend.Utils.OrderNoGenerator;
import backend.Utils.PageResponse;
import backend.Utils.PaginationUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.data.relational.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    @Override
    public Mono<OrderDetails> findAll() {

        Mono<List<OrderDetail>> orderDetailsMono = orderDetailRepository.findAll()
                .collectList();

        Mono<List<OrderItem>> orderItemsMono = orderItemRepository.findAll()
                .collectList();

        return Mono.zip(orderItemsMono, orderDetailsMono)
                .map(tuple -> new OrderDetails(
                        tuple.getT1(),
                        tuple.getT2()
                ));
    }

    @Override
    public Mono<OrderDetails> findByOrderNo(String orderNo) {

        Mono<List<OrderItem>> orderItemsMono =
                orderItemRepository.findByOrderNo(orderNo)
                        .collectList();

        Mono<List<OrderDetail>> orderDetailsMono =
                orderItemsMono.flatMapMany(Flux::fromIterable)
                        .flatMap(item -> orderDetailRepository.findByOrderId(item.getId()))
                        .collectList();

        return Mono.zip(orderItemsMono, orderDetailsMono)
                .map(tuple -> new OrderDetails(tuple.getT1(), tuple.getT2()));
    }

    @Override
    public Mono<PageResponse<OrderItem>> findPagination(Integer pageNumber, Integer pageSize) {
        return PaginationUtils.fetchPagedResponse(
                r2dbcEntityTemplate,
                OrderItem.class,
                null,
                Optional.ofNullable(pageNumber).orElse(PaginationUtils.DEFAULT_PAGE_NUMBER),
                Optional.ofNullable(pageSize).orElse(PaginationUtils.DEFAULT_LIMIT),
                null,
                Sort.by(Sort.Order.desc(OrderItem.CREATED_DATE_COLUMN)
        ));
    }

    @Override
    public Mono<List<OrderDetail>> createOrder(List<OrderRequest> orderRequests) {
        var orderNo = OrderNoGenerator.generateOrderNo();

        // Fetch current user ID
        return userService.currentUser()
            .flatMap(userId ->
                // Create OrderItem
                orderItemRepository.save(
                    OrderItem.builder()
                            .orderNo(orderNo)
                            .userId(userId)
                            .createdDate(LocalDateTime.now())
                            .build()
                )
                .flatMap(orderItem ->
                    Flux.fromIterable(orderRequests)
                        .flatMap(request ->
                            r2dbcEntityTemplate.select(Product.class)
                                .matching(Query.query(
                                        Criteria.where(Product.CODE_COLUMN).is(request.code())))
                                .one()
                                .switchIfEmpty(Mono.error(
                                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Product code not found")))
                                .flatMap(product -> {
                                    if (product.getQuantity() < request.quantity()) {
                                        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient stock"));
                                    }

                                    // Calculate total price
                                    BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(request.quantity()));
                                    // Create OrderDetail
                                    OrderDetail detail = OrderDetail.builder()
                                            .orderId(orderItem.getId())
                                            .productId(product.getId())
                                            .quantity(request.quantity())
                                            .total(totalPrice)
                                            .build();

                                    return orderDetailRepository.save(detail)
                                        .flatMap(saved ->
                                            // Update Product Quantity After OrderDetail is saved
                                            r2dbcEntityTemplate.update(Product.class)
                                                .matching(Query.query(
                                                    Criteria.where(Product.CODE_COLUMN).is(request.code())))
                                                .apply(Update.update(
                                                    Product.QUANTITY_COLUMN,
                                                    product.getQuantity() - request.quantity()))
                                                .thenReturn(saved)
                                        );
                                })
                        )
                        .collectList()
                )
            );
    }


}
