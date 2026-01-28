package backend.Entities;

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
@Table("brand")
public class Brand {

    public static final String LABEL = "brand";
    public static final String ID_COLUMN = "id";
    public static final String NAME_COLUMN = "name";
    public static final String IS_ACTIVE_COLUMN = "isActive";
    public static final String CREATED_DATE_COLUMN = "createdDate";
    public static final String UPDATED_DATE_COLUMN = "updatedDate";

    @Id
    @Column(ID_COLUMN)
    private Long id;
    @Column(NAME_COLUMN)
    private String name;
    @Column(IS_ACTIVE_COLUMN)
    private boolean isActive;
    @Column(CREATED_DATE_COLUMN)
    @JsonSerialize(using = DateStringUtils.class)
    private LocalDateTime createdDate;
    @Column(UPDATED_DATE_COLUMN)
    @JsonSerialize(using = DateStringUtils.class)
    private LocalDateTime updatedDate;

    public static BrandBuilder from(Brand brand){
        return Brand.builder()
                .id(brand.getId())
                .name(brand.getName())
                .isActive(brand.isActive());
    }

    public static Brand update(Brand existingBrand, Brand updatedBrand) {
        existingBrand.setName(updatedBrand.getName());
        existingBrand.setActive(updatedBrand.isActive());
        return existingBrand;
    }

}
