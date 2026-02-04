package backend.Controller;

import backend.Entities.SupplierContact;
import backend.Service.SupplierContactService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/supplierContact")
@CrossOrigin("*")
@AllArgsConstructor
public class SupplierContactController {

    private final SupplierContactService supplierContactService;

    @GetMapping("/all")
    Flux<SupplierContact> findAll() {
        return supplierContactService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<SupplierContact> findById(@PathVariable Long id) {
        return supplierContactService.findById(id);
    }

    @PostMapping("/create")
    public Mono<SupplierContact> create(@RequestBody SupplierContact supplierContact) {
        return supplierContactService.create(supplierContact);
    }

    @PutMapping("/update")
    public Mono<SupplierContact> update(@RequestBody SupplierContact supplierContact) {
        return supplierContactService.update(supplierContact);
    }
}
