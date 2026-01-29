package backend.ServiceImpl;

import backend.Entities.Supplier;
import backend.Repository.SupplierRepository;
import backend.Service.SupplierService;
import backend.Utils.PageResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Override
    public Mono<PageResponse<Supplier>> findPagination(Integer pageNumber, Integer pageSize) {
        return null;
    }

    @Override
    public Mono<Supplier> create(Supplier supplier) {
        return supplierRepository.save(Supplier.from(supplier)
                        .isActive(true)
                        .createdDate(LocalDateTime.now())
                        .build()
        );
    }
}
