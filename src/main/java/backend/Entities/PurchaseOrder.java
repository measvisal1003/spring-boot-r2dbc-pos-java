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
@Table("purchaseOrder")
public class PurchaseOrder {

    public static final String LABEL = "purchaseOrder";
    public static final String ID_COLUMN = "id";
    public static final String SUPPLIER_ID_COLUMN = "supplierId";
    public static final String ORDER_DATE_COLUMN = "orderDate";
    public static final String STATUS_COLUMN = "status";

    @Id
    @Column(ID_COLUMN)
    private Long id;
    @Column(SUPPLIER_ID_COLUMN)
    private Long supplierId;
    @Column(ORDER_DATE_COLUMN)
    @JsonSerialize(using = DateStringUtils.class)
    private LocalDateTime orderDate;
    @Column(STATUS_COLUMN)
    private Status status;

    public static PurchaseOrderBuilder from(PurchaseOrder purchaseOrder) {
        return PurchaseOrder.builder()
                .id(purchaseOrder.getId())
                .supplierId(purchaseOrder.getSupplierId())
                .orderDate(purchaseOrder.getOrderDate())
                .status(purchaseOrder.getStatus());
    }
}
