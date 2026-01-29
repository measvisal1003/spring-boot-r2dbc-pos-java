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

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(QuantityAdjustment.LABEL)
public class QuantityAdjustment {

    public static final String LABEL = "quantityAdjustment";
    public static final String ID_COLUMN = "id";
    public static final String PRODUCT_NAME_COLUMN = "productName";
    public static final String USER_ID_COLUMN = "userId";
    public static final String METHOD_COLUMN = "method";
    public static final String QUANTITY_COLUMN = "quantity";
    public static final String IS_COMPLETE_COLUMN = "isComplete";
    public static final String CREATED_DATE_COLUMN = "createdDate";

    @Id
    @Column(ID_COLUMN)
    private Long id;
    @Column(PRODUCT_NAME_COLUMN)
    private String productName;
    @Column(USER_ID_COLUMN)
    private Long userId;
    @Column(METHOD_COLUMN)
    private Method method;
    @Column(QUANTITY_COLUMN)
    private int quantity;
    @Column(IS_COMPLETE_COLUMN)
    private boolean isComplete;
    @Column(CREATED_DATE_COLUMN)
    @JsonSerialize(using = DateStringUtils.class)
    private LocalDateTime createdDate;

    public static QuantityAdjustmentBuilder from(QuantityAdjustment quantityAdjustment) {
        return QuantityAdjustment.builder()
                .id(quantityAdjustment.getId())
                .productName(quantityAdjustment.getProductName())
                .userId(quantityAdjustment.getUserId())
                .method(quantityAdjustment.getMethod())
                .quantity(quantityAdjustment.getQuantity())
                .isComplete(quantityAdjustment.isComplete())
                .createdDate(quantityAdjustment.getCreatedDate());
    }
}
