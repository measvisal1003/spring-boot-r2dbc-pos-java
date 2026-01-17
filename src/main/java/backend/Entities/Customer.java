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
@Table("customer")
public class Customer {

    public static final String LABEL = "customer";
    public static final String ID_COLUMN = "id";
    public static final String NAME_COLUMN = "name";
    public static final String EMAIL_COLUMN = "email";
    public static final String PHONE_COLUMN = "phone";
    public static final String ADDRESS_COLUMN = "address";
    public static final String IS_ACTIVE_COLUMN = "isActive";
    public static final String CREATED_DATE_COLUMN = "createdDate";
    public static final String UPDATED_DATE_COLUMN = "updatedDate";

    @Id
    @Column(ID_COLUMN)
    private Long id;
    @Column(NAME_COLUMN)
    private String name;
    @Column(EMAIL_COLUMN)
    private String email;
    @Column(PHONE_COLUMN)
    private String phone;
    @Column(ADDRESS_COLUMN)
    private String address;
    @Column(IS_ACTIVE_COLUMN)
    private boolean isActive;
    @Column(CREATED_DATE_COLUMN)
    @JsonSerialize(using = DateStringUtils.class)
    private LocalDateTime createdDate;
    @Column(UPDATED_DATE_COLUMN)
    @JsonSerialize(using = DateStringUtils.class)
    private LocalDateTime updatedDate;

    public CustomerBuilder from(Customer customer) {
        return Customer.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .address(customer.getAddress());
    }

    public static Customer update(Customer customer) {
        customer.setName(customer.getName());
        customer.setEmail(customer.getEmail());
        customer.setPhone(customer.getPhone());
        customer.setAddress(customer.getAddress());
        return customer;
    }
}
