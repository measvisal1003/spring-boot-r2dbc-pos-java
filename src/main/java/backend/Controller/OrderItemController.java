package backend.Controller;

import backend.Dto.OrderRequest;
import backend.Entities.OrderDetail;
import backend.Entities.OrderItem;
import backend.Service.OrderItemService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/order")
@CrossOrigin("*")
public class OrderItemController {

    private final OrderItemService orderItemService;

    @PostMapping("/create")
    public Mono<List<OrderDetail>> create(@RequestBody List<OrderRequest> orderRequest) {
        return orderItemService.createOrder(orderRequest);
    }

}
