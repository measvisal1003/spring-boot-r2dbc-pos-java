package backend.Controller;

import backend.Entities.QuantityAdjustment;
import backend.Service.QuantityAdjustmentService;
import backend.Utils.PageResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/quantityAdjustment")
@CrossOrigin("*")
@AllArgsConstructor
public class QuantityAdjustmentController {

    private final QuantityAdjustmentService quantityAdjustmentService;

    @GetMapping
    public Mono<PageResponse<QuantityAdjustment>> findPagination(@RequestParam Integer pageNumber, Integer pageSize) {
        return quantityAdjustmentService.findPagination(pageNumber, pageSize);
    }
}
