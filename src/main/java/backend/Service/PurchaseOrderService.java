package backend.Service;

import backend.Dto.PurchaseOrderRequest;
import backend.Entities.PurchaseOrder;
import backend.Entities.PurchaseOrderDetail;
import backend.Entities.Status;
import backend.Utils.PageResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public interface PurchaseOrderService {

    Flux<PurchaseOrder> findAll();
    Mono<PurchaseOrder> findById(Long id);
    Mono<PageResponse<PurchaseOrder>> findPagination(Integer pageNumber, Integer pageSize);
    Mono<List<PurchaseOrderDetail>> create(PurchaseOrderRequest req);
    public Mono<PurchaseOrder> updateStatus(Long id, Status status) ;
}
