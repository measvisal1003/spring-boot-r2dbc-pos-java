package backend.Service;

import backend.Entities.Brand;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface BrandService {

    Flux<Brand> findAll();
    Mono<Brand> create(Brand brand);
    Mono<Brand> update(Brand brand);
}
