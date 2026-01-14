package backend.Dto;

import backend.Entities.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {

    private Long id;
    private String code;
    private String name;
    private boolean isActive;

    public static Category update(Category category, CategoryDto categoryDto) {
        category.setCode(categoryDto.getCode());
        category.setName(categoryDto.getName());
        category.setActive(categoryDto.isActive());
        return category;
    }
}
