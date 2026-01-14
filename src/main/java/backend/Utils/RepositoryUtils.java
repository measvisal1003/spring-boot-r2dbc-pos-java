package backend.Utils;

import backend.Exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@AllArgsConstructor
public class RepositoryUtils {

    private final ValidationHelper validationHelper;
    private final StringUtil stringUtil;
    private final static String errorMsg = "not a valid representation! Please provide a valid value and none null ";

    public <T>Flux<T> findAllActive(
            R2dbcEntityTemplate r2dbcEntityTemplate,
            Class<T> entityClass,
            String inactiveColumn,
            String label
    ) {
        Query query = Query.query(Criteria.where(inactiveColumn).isTrue());
        return r2dbcEntityTemplate.select(query,
                                          entityClass)
                .onErrorResume(e -> Flux.error(new ResourceNotFoundException(label)));
    }

    public <T>Mono<T> findById(
            R2dbcEntityTemplate r2dbcEntityTemplate,
            Class<T> entityClass,
            String idColumn,
            Long id,
            String inactiveColumn,
            String label
    ) {
        return validationHelper.validateId(id)
                .flatMap(validId -> {
                    Query query = Query.query(Criteria.where(idColumn).is(validId).and(inactiveColumn).isTrue());
                    return r2dbcEntityTemplate.selectOne(query,
                                                         entityClass)
                            .switchIfEmpty(Mono.error(new ResourceNotFoundException(label,
                                                                                    String.valueOf(validId))))
                            .onErrorResume(e -> Mono.error(new ResourceNotFoundException(label,
                                                                                         String.valueOf(validId))));
                });
    }
}
