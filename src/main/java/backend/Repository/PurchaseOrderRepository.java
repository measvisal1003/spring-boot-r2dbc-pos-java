package backend.Repository;

import backend.Entities.PurchaseOrder;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderRepository extends R2dbcRepository<PurchaseOrder, Long> {
}
