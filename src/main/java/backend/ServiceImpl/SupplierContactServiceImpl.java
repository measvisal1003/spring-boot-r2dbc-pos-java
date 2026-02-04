package backend.ServiceImpl;

import backend.Entities.Supplier;
import backend.Entities.SupplierContact;
import backend.Repository.SupplierContactRepository;
import backend.Repository.SupplierRepository;
import backend.Service.SupplierContactService;
import backend.Utils.PageResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class SupplierContactServiceImpl implements SupplierContactService {

    private final SupplierContactRepository supplierContactRepository;
    private final SupplierRepository supplierRepository;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    @Override
    public Flux<SupplierContact> findAll() {
        return supplierContactRepository.findAll();
    }

    @Override
    public Mono<SupplierContact> findById(Long id) {
        return supplierContactRepository.findById(id);
    }

    @Override
    public Mono<SupplierContact> create(SupplierContact supplierContact) {
        return supplierRepository.findById(supplierContact.getSupplierId())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier not found")))
                .flatMap(supplier -> supplierContactRepository.save(
                        SupplierContact.from(supplierContact)
                                .isActive(true)
                                .createdDate(LocalDateTime.now())
                                .build()
                ));
    }

    @Override
    public Mono<SupplierContact> update(SupplierContact supplierContact) {
        return supplierContactRepository.findById(supplierContact.getId())
                .flatMap(existingSupplierContact -> {
                    SupplierContact.update(existingSupplierContact, supplierContact)
                            .setUpdatedDate(LocalDateTime.now());

                    return supplierContactRepository.save(existingSupplierContact);
                });
    }

    @Override
    public Mono<Long> delete(Long id) {
        return supplierContactRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier contact not found")))
                .flatMap(category ->
                        supplierContactRepository.deleteById(id)
                                .thenReturn(id)
                );
    }

    @Override
    public Mono<PageResponse<SupplierContact>> findPagination(Integer pageNumber, Integer pageSize) {
        return null;
    }
}
