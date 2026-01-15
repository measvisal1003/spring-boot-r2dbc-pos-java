package backend.ServiceImpl;

import backend.Dto.CategoryDto;
import backend.Entities.Category;
import backend.Mapper.CategoryMapper;
import backend.Repository.CategoryRepository;
import backend.Service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    @Override
    public Flux<CategoryDto> findAll() {
        return categoryRepository.findAll()
                .map(CategoryMapper::toDto);
    }

    @Override
    public Mono<Category> findById(Long id) {
        return categoryRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Category not found with id: " + id
                        )
                ));
    }

    @Override
    public Mono<Category> create(Category category) {
        return categoryRepository.existsByCode(category.getCode())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new ResponseStatusException(
                                HttpStatus.CONFLICT,
                                "Category code already exists!"
                        ));
                    }

                    return categoryRepository.save(
                            Category.from(category)
                                    .createdDate(LocalDateTime.now())
                                    .isActive(true)
                                    .build()
                    );
                });
    }


    @Override
    public Mono<Category> update(Category category) {
        return categoryRepository.findById(category.getId())
                .flatMap(existingCategory -> {
                    Category.update(existingCategory)
                            .setUpdatedDate(LocalDateTime.now());
                    return categoryRepository.save(existingCategory);
                });
    }
}
