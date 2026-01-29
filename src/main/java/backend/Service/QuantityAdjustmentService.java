package backend.Service;

import backend.Entities.QuantityAdjustment;
import backend.Utils.PageResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface QuantityAdjustmentService {

    Mono<PageResponse<QuantityAdjustment>> findPagination(Integer pageNumber, Integer pageSize);

}
