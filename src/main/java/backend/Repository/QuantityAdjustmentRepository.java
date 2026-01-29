package backend.Repository;

import backend.Entities.QuantityAdjustment;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuantityAdjustmentRepository extends R2dbcRepository<QuantityAdjustment, Long> {
}
