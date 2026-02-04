package backend.Service;

import backend.Entities.SupplierContact;
import backend.Utils.PageResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface SupplierContactService {

    Flux<SupplierContact> findAll();
    Mono<SupplierContact> findById(Long id);
    Mono<SupplierContact> create(SupplierContact supplierContact);
    Mono<SupplierContact> update(SupplierContact supplierContact);
    Mono<Long> delete(Long id);

    Mono<PageResponse<SupplierContact>> findPagination(Integer pageNumber, Integer pageSize);
}
