package backend.Controller;

import backend.Dto.PurchaseOrderRequest;
import backend.Dto.PurchaseOrderStatus;
import backend.Entities.PurchaseOrder;
import backend.Entities.PurchaseOrderDetail;
import backend.Entities.Status;
import backend.Service.PurchaseOrderDetailService;
import backend.Service.PurchaseOrderService;
import backend.Utils.PageResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/purchase/order")
@CrossOrigin("*")
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;
    private final PurchaseOrderDetailService purchaseOrderDetailService;

    @GetMapping("/all")
    public Flux<PurchaseOrder> findAll() {
        return purchaseOrderService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<PurchaseOrder> findById(@PathVariable Long id) {
        return purchaseOrderService.findById(id);
    }

    @GetMapping
    public Mono<PageResponse<PurchaseOrder>> findPagination(@RequestParam Integer pageNumber, Integer pageSize) {
        return purchaseOrderService.findPagination(pageNumber, pageSize);
    }

    @PostMapping("/create")
    public Mono<List<PurchaseOrderDetail>> createPurchaseOrder(@RequestBody PurchaseOrderRequest request) {
        return purchaseOrderService.create(request);
    }

    @PutMapping("/{id}/status")
    public Mono<PurchaseOrder> updateStatus(@PathVariable Long id, @RequestBody PurchaseOrderStatus status) {
        return purchaseOrderService.updateStatus(id, status.status());
    }

}
