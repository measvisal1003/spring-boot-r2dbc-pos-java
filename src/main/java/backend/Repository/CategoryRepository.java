package backend.Repository;

import backend.Entities.Category;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CategoryRepository extends R2dbcRepository<Category, Long> {
    Mono<Boolean> existsByCode(String code);
}
