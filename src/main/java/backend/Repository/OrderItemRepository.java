package backend.Repository;

import backend.Entities.OrderItem;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface OrderItemRepository extends R2dbcRepository<OrderItem, Long> {
    Flux<OrderItem> findByOrderNo(String orderNo);
}
