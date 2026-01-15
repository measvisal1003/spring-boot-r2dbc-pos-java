package backend.Service;

import backend.Dto.OrderDetails;
import backend.Dto.OrderRequest;
import backend.Entities.OrderDetail;
import backend.Entities.OrderItem;
import backend.Utils.PageResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public interface OrderItemService {

    Flux<OrderDetails> findAll();
    Flux<OrderDetails> findByOrderNo(String orderNo);
    Mono<PageResponse<OrderDetails>> findPagination(Integer pageNumber, Integer pageSize);

    Mono<List<OrderDetail>> createOrder(List<OrderRequest> orderRequest);
}
