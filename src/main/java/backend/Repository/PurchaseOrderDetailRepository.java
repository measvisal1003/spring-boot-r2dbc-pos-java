package backend.Repository;

import backend.Entities.PurchaseOrderDetail;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderDetailRepository extends R2dbcRepository<PurchaseOrderDetail, Long> {
}
