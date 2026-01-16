package backend.Dto;

import backend.Entities.Category;
import backend.Entities.Product;

import java.util.List;

public record CategoryProduct(
        Category category,
        List<Product> products
) {
}
