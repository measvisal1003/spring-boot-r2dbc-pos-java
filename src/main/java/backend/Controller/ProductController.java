package backend.Controller;

import backend.Dto.ProductDto;
import backend.Entities.Product;
import backend.Repository.ProductRepository;
import backend.Service.ProductService;
import backend.Utils.PageResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/product")
@CrossOrigin("*")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    public Flux<ProductDto> findAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Product> findById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PostMapping("/create")
    public Mono<Product> create(@RequestBody Product product) {
        return productService.create(product);
    }

    @PutMapping("/update")
    public Mono<Product> update(@RequestBody Product product) {
        return productService.update(product);
    }


    // Additional Method
    //Check existing for generating
    @GetMapping("/check-code/{code}")
    public Mono<Boolean> checkCodeExists(@PathVariable String code) {
        return productService.existsByProductCode(code);
    }

    //Pagination
    @GetMapping
    public Mono<PageResponse<ProductDto>> findPagination(
            @RequestParam("pageNumber") Integer pageNumber, Integer pageSize) {
        return productService.findPagination(pageNumber, pageSize);

    }
}
