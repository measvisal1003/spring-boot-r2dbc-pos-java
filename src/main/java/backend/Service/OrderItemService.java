package backend.Service;

import backend.Dto.OrderRequest;
import backend.Entities.OrderDetail;
import backend.Entities.OrderItem;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public interface OrderItemService {

    Mono<List<OrderDetail>> createOrder(List<OrderRequest> orderRequest);
}
