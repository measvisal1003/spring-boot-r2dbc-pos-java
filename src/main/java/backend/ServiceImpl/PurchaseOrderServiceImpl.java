package backend.ServiceImpl;

import backend.Dto.PurchaseOrderDetails;
import backend.Dto.PurchaseOrderRequest;
import backend.Dto.PurchaseOrderStatus;
import backend.Entities.PurchaseOrder;
import backend.Entities.PurchaseOrderDetail;
import backend.Entities.Status;
import backend.Entities.Supplier;
import backend.Repository.OrderDetailRepository;
import backend.Repository.PurchaseOrderDetailRepository;
import backend.Repository.PurchaseOrderRepository;
import backend.Repository.SupplierRepository;
import backend.Service.PurchaseOrderService;
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
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final PurchaseOrderDetailRepository purchaseOrderDetailRepository;

    @Override
    public Flux<PurchaseOrder> findAll() {
        return purchaseOrderRepository.findAll();
    }

    @Override
    public Mono<PurchaseOrder> findById(Long id) {
        return purchaseOrderRepository.findById(id);
    }

    @Override
    public Mono<PageResponse<PurchaseOrder>> findPagination(Integer pageNumber, Integer pageSize) {
        return PaginationUtils.fetchPagedResponse(
                r2dbcEntityTemplate,
                PurchaseOrder.class,
                Optional.ofNullable(pageNumber).orElse(PaginationUtils.DEFAULT_PAGE_NUMBER),
                Optional.ofNullable(pageSize).orElse(PaginationUtils.DEFAULT_LIMIT),
                Criteria.where(PurchaseOrder.STATUS_COLUMN).isTrue(),
                Sort.by(Sort.Order.by(PurchaseOrder.ORDER_DATE_COLUMN)).descending()
        );
    }

    @Override
    public Mono<List<PurchaseOrderDetail>> create(PurchaseOrderRequest req) {
        if (req.items() == null || req.items().isEmpty()) {
            return Mono.error(new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Items list cannot be empty"));
        }
        // Check exist
        Mono<Void> supplierExists =
                r2dbcEntityTemplate.select(Supplier.class)
                        .matching(Query.query(Criteria.where(Supplier.ID_COLUMN).is(req.supplierId())))
                        .one()
                        .switchIfEmpty(Mono.error(
                                new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier not found")))
                        .then();

        // Save PurchaseOrder first
        Mono<PurchaseOrder> savedOrder =
                supplierExists.then(
                        purchaseOrderRepository.save(
                                PurchaseOrder.builder()
                                        .supplierId(req.supplierId())
                                        .status(req.status())
                                        .orderDate(LocalDateTime.now())
                                        .build()
                        )
                );

        // Save PurchaseOrderDetail
        return savedOrder.flatMapMany(order ->
                        Flux.fromIterable(req.items())
                                .map(item -> {
                                    if (item.quantity() <= 0) {
                                        throw new ResponseStatusException(
                                                HttpStatus.BAD_REQUEST, "Quantity must be > 0");
                                    }
                                    if (item.unitPrice() == null || item.unitPrice().compareTo(BigDecimal.ZERO) < 0) {
                                        throw new ResponseStatusException(
                                                HttpStatus.BAD_REQUEST, "Unit price must be >= 0");
                                    }

                                    BigDecimal lineTotal =
                                            item.unitPrice().multiply(BigDecimal.valueOf(item.quantity()));

                                    return PurchaseOrderDetail.builder()
                                            .purchaseId(order.getId())
                                            .productId(item.productId())
                                            .quantity(item.quantity())
                                            .unitPrice(item.unitPrice())
                                            .totalPrice(lineTotal)
                                            .build();
                                })
                                .flatMap(purchaseOrderDetailRepository::save)
                )
                .collectList();
    }

    @Override
    public Mono<PurchaseOrder> updateStatus(Long id, Status status) {
        return r2dbcEntityTemplate.update(PurchaseOrder.class)
                .matching(Query.query(Criteria.where(PurchaseOrder.ID_COLUMN).is(id)))
                .apply(Update.update(PurchaseOrder.STATUS_COLUMN, status.getValue()))
                .flatMap(rows -> {
                    if (rows == 0) {
                        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Purchase Order Not Found"));
                    }
                    return r2dbcEntityTemplate.select(PurchaseOrder.class)
                            .matching(Query.query(Criteria.where(PurchaseOrder.ID_COLUMN).is(id)))
                            .one();
                });
    }
}
