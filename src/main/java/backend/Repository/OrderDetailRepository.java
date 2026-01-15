package backend.Repository;

import backend.Entities.OrderDetail;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface OrderDetailRepository extends R2dbcRepository<OrderDetail, Long> {
    Flux<OrderDetail> findByOrderId(Long id);
}
