package backend.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("inventory")
public class Inventory {

    public static final String LABEL = "inventory";
    public static final String ID_COLUMN = "id";
    public static final String PRODUCT_ID_COLUMN = "productId";
    public static final String BRANCH_ID_COLUMN = "branchId";
    public static final String QUANTITY_COLUMN = "quantity";
    public static final String IS_ACTIVE_COLUMN = "isActive";
    public static final String CREATED_DATE_COLUMN = "createdDate";
    public static final String UPDATED_DATE_COLUMN = "updatedDate";

    private Long id;
    private Long productId;
    private Long branchId;
    private int quantity;
    private boolean isActive;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
