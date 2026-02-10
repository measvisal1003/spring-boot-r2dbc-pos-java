package backend.Utils;

import backend.Dto.CategoryProduct;
import backend.Entities.Category;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public class FilteredWithNestedPaginationUtils {

    public static <P, C, R> Mono<PageResponse<R>> fetchFilteredPagination(
            R2dbcEntityTemplate template,
            Class<P> primaryClass,
            Criteria criteria,
            Integer pageNumber,
            Integer pageSize,
            Function<P, Mono<List<C>>> fetchSecondary,
            BiFunction<P, List<C>, R> resultMapper
    ) {
        // 1. Set Defaults
        int page = Optional.ofNullable(pageNumber).orElse(1);
        int size = Optional.ofNullable(pageSize).orElse(10);
        long offset = (long) (page - 1) * size;

        // 2. Build Base Query
        Query baseQuery = Query.query(criteria);

        // 3. Get Total Count (with filters applied)
        Mono<Long> countMono = template.count(baseQuery, primaryClass);

        // 4. Fetch Paginated Content (with filters applied)
        Flux<R> contentFlux = template.select(primaryClass)
                .matching(baseQuery.limit(size).offset(offset))
                .all()
                .flatMap(primary -> fetchSecondary.apply(primary)
                        .map(children -> resultMapper.apply(primary, children))
                );

        return countMono.flatMap(total ->
                contentFlux.collectList()
                        .map(content -> new PageResponse<>(content, page, size, total))
        );
    }
}
