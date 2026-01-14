package backend.Entities;

import backend.Dto.CategoryDto;
import backend.Utils.DateStringUtils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("category")
public class Category {

    public static final String LABEL = "category";
    public static final String ID_COLUMN = "categoryId";
    public static final String CATEGORY_CODE_COLUMN = "categoryCode";
    public static final String CATEGORY_NAME_COLUMN = "categoryName";
    public static final String STATUS_COLUMN = "status";
    public static final String CREATED_DATE_COLUMN = "createdDate";
    public static final String UPDATED_DATE_COLUMN = "updatedDate";

    @Id
    @Column(ID_COLUMN)
    private Long id;
    @Column(CATEGORY_CODE_COLUMN)
    private String code;
    @Column(CATEGORY_NAME_COLUMN)
    private String name;
    @Column(STATUS_COLUMN)
    private boolean isActive;
    @Column(CREATED_DATE_COLUMN)
    @JsonSerialize(using = DateStringUtils.class)
    private LocalDateTime createdDate;
    @Column(UPDATED_DATE_COLUMN)
    @JsonSerialize(using = DateStringUtils.class)
    private LocalDateTime updatedDate;

    public static CategoryBuilder from(Category category) {
        return Category.builder()
                .id(category.getId())
                .code(category.getCode())
                .name(category.getName())
                .isActive(category.isActive())
                .createdDate(category.getCreatedDate())
                .updatedDate(category.getUpdatedDate());
    }

    public static Category update(Category category) {
        category.setCode(category.getCode());
        category.setName(category.getName());
        category.setActive(category.isActive());
        return category;
    }
}
