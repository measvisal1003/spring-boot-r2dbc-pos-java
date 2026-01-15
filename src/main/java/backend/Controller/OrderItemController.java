package backend.Controller;

import backend.Dto.OrderDetails;
import backend.Dto.OrderRequest;
import backend.Entities.OrderDetail;
import backend.Entities.OrderItem;
import backend.Service.OrderItemService;
import backend.Utils.StringUtil;
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


    @GetMapping("/all")
    public Mono<OrderDetails> findAll() {
        return orderItemService.findAll();
    }

    @GetMapping("/{orderNo}")
    public Mono<OrderDetails> findByOrderNo(@PathVariable @RequestParam(defaultValue = "orderNo") String orderNo) {
        return orderItemService.findByOrderNo(orderNo);
    }

    @PostMapping("/create")
    public Mono<List<OrderDetail>> create(@RequestBody List<OrderRequest> orderRequest) {
        return orderItemService.createOrder(orderRequest);
    }

}
