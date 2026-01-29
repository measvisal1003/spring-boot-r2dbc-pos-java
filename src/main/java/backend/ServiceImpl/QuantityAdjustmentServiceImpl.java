package backend.ServiceImpl;

import backend.Entities.QuantityAdjustment;
import backend.Repository.QuantityAdjustmentRepository;
import backend.Service.QuantityAdjustmentService;
import backend.Utils.PageResponse;
import backend.Utils.PaginationUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@AllArgsConstructor
public class QuantityAdjustmentServiceImpl implements QuantityAdjustmentService {

    private final QuantityAdjustmentRepository quantityAdjustmentRepository;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    @Override
    public Mono<PageResponse<QuantityAdjustment>> findPagination(Integer pageNumber, Integer pageSize) {
        return PaginationUtils.fetchPagedResponse(
                r2dbcEntityTemplate,
                QuantityAdjustment.class,
                Optional.ofNullable(pageNumber).orElse(PaginationUtils.DEFAULT_PAGE_NUMBER),
                Optional.ofNullable(pageSize).orElse(PaginationUtils.DEFAULT_LIMIT),
                Criteria.where(QuantityAdjustment.IS_COMPLETE_COLUMN).isTrue(),
                Sort.by(Sort.Order.desc(QuantityAdjustment.CREATED_DATE_COLUMN))
        );
    }
}
