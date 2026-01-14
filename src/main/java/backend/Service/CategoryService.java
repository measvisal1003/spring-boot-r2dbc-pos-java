package backend.Service;

import backend.Dto.CategoryDto;
import backend.Entities.Category;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface CategoryService {

    Flux<CategoryDto> findAll();
    Mono<Category> findById(Long id);
    Mono<Category> create(Category category);
    Mono<CategoryDto> update(CategoryDto categoryDto);

}
