package backend.Entities;

import backend.Utils.DateStringUtils;
import backend.Utils.OrderNoGenerator;
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
@Table("orderItem")
public class OrderItem {

    public static final String LABEL = "orderItem";
    public static final String ID_COLUMN = "id";
    public static final String ORDER_NO_COLUMN = "orderNo";
    public static final String USER_ID_COLUMN = "userId";
    public static final String CUSTOMER_ID_COLUMN = "customerId";
    public static final String PAYMENT_ID_COLUMN = "paymentId";
    public static final String IS_PAID_COLUMN = "isPaid";
    public static final String CREATED_DATE_COLUMN = "createdDate";

    @Id
    @Column(ID_COLUMN)
    private Long id;
    @Column(ORDER_NO_COLUMN)
    private String orderNo;
    @Column(USER_ID_COLUMN)
    private Long userId;
    @Column(IS_PAID_COLUMN)
    private boolean isPaid;
    @Column(CREATED_DATE_COLUMN)
    @JsonSerialize(using = DateStringUtils.class)
    private LocalDateTime createdDate;

}
