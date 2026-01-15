package backend.Utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class PaginationUtils {
    public static final int DEFAULT_LIMIT = 10;
    public static final int DEFAULT_PAGE_NUMBER = 1;

    public static Pageable createPageable(Integer pageNumber, Integer limit, Sort sort) {
        int pageSize = (limit != null) ? limit : DEFAULT_LIMIT;
        return PageRequest.of(pageNumber != null ? pageNumber : DEFAULT_PAGE_NUMBER,
                pageSize,
                sort);
    }

    public static long calculateOffset(Integer pageNumber, Integer pageSize) {
        return (long) (pageNumber != null ? pageNumber -1 : DEFAULT_PAGE_NUMBER -1) * (pageSize != null ? pageSize :
                DEFAULT_LIMIT);
    }

    public static <T, R> Mono<PageResponse<R>> fetchPagedResponse(
            R2dbcEntityTemplate r2dbcEntityTemplate,
            Class<T> entityClass,
            Function<T, R> mapper,
            int pageNumber,
            int pageSize,
            String inActiveColumn,
            Sort sort
    ) {
        Pageable pageable = createPageable(pageNumber, pageSize, sort);
        long offset = calculateOffset(pageable.getPageNumber(), pageable.getPageSize());

        Mono<Long> count = r2dbcEntityTemplate.count(Query.query(Criteria.where(inActiveColumn).isTrue()), entityClass);
        Query query = Query.query(Criteria.where(inActiveColumn).isTrue())
                .offset(offset)
                .sort(pageable.getSort())
                .limit(pageable.getPageSize());
        return count.flatMap(totalRecords -> r2dbcEntityTemplate.select(query, entityClass)
                .map(mapper)
                .collectList()
                .map(results -> new PageResponse<>(results,
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        totalRecords))
        );
    }

    public static <T, R> Mono<PageResponse<R>> fetchNestedPageResponse(
            R2dbcEntityTemplate r2dbcEntityTemplate,
            Class<T> entityClass,
            Function<T, R> mapper,
            int pageNumber,
            int pageSize,
            Criteria criteria,
            Sort sort
    ) {
        Pageable pageable = createPageable(pageNumber, pageSize, sort);
        long offset = calculateOffset(pageable.getPageNumber(), pageable.getPageSize());

        Mono<Long> count = r2dbcEntityTemplate.count(Query.query(criteria), entityClass);
        Query query = Query.query(criteria)
                .offset(offset)
                .sort(pageable.getSort())
                .limit(pageable.getPageSize());
        return count.flatMap(totalRecords -> r2dbcEntityTemplate.select(query, entityClass)
                .map(mapper)
                .collectList()
                .map(results -> new PageResponse<>(
                        results,
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        totalRecords))
        );
    }

}
