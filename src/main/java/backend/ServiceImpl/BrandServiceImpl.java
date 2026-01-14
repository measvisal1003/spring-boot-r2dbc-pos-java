package backend.ServiceImpl;

import backend.Entities.Brand;
import backend.Repository.BrandRepository;
import backend.Service.BrandService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Override
    public Flux<Brand> findAll() {
        return brandRepository.findAll();
    }

    @Override
    public Mono<Brand> create(Brand brand) {
        return brandRepository.save(Brand.from(brand).build());
    }

    @Override
    public Mono<Brand> update(Brand brand) {
        return null;
    }
}
