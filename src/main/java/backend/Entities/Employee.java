package backend.Entities;

import backend.Dto.EmployeeDto;
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
@Table("employee")
public class Employee {

    public final static String ID_COLUMN = "id";
    public final static String FIRST_NAME_COLUMN = "firstName";
    public final static String LAST_NAME_COLUMN = "lastName";
    public final static String PHONE_COLUMN = "phone";
    public final static String EMAIL_COLUMN = "email";
    public final static String NAT_ID_COLUMN = "natId";
    public final static String NSSF_ID_COLUMN = "nssfId";
    public final static String BRANCH_ID_COLUMN = "branchId";
    public final static String IS_ACTIVE_COLUMN = "isActive";
    public static final String CREATED_DATE_COLUMN = "createdDate";
    public static final String UPDATED_DATE_COLUMN = "updatedDate";
    public static final String IMAGE_KEY_COLUMN = "imageKey";
    public static final String IMAGE_URL_COLUMN = "imageUrl";

    @Id
    @Column(ID_COLUMN)
    private Long id;
    @Column(FIRST_NAME_COLUMN)
    private String firstName;
    @Column(LAST_NAME_COLUMN)
    private String lastName;
    @Column(PHONE_COLUMN)
    private String phone;
    @Column(EMAIL_COLUMN)
    private String email;
    @Column(NAT_ID_COLUMN)
    private String natId;
    @Column(NSSF_ID_COLUMN)
    private String nssfId;
    @Column(IS_ACTIVE_COLUMN)
    private boolean isActive;
    @Column(CREATED_DATE_COLUMN)
    @JsonSerialize(using = DateStringUtils.class)
    private LocalDateTime createdDate;
    @Column(UPDATED_DATE_COLUMN)
    @JsonSerialize(using = DateStringUtils.class)
    private LocalDateTime updatedDate;
    @Column(IMAGE_KEY_COLUMN)
    private String imageKey;
    @Column(IMAGE_URL_COLUMN)
    private String imageUrl;


    public static EmployeeBuilder from(Employee employee) {
        return Employee.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .phone(employee.getPhone())
                .email(employee.getEmail())
                .natId(employee.getNatId())
                .nssfId(employee.getNssfId())
                .isActive(employee.isActive())
                .createdDate(employee.getCreatedDate())
                .updatedDate(employee.getUpdatedDate());
    }

    public static Employee update(Employee existingEmployee, Employee updatedEmployee) {
        existingEmployee.setFirstName(updatedEmployee.getFirstName());
        existingEmployee.setLastName(updatedEmployee.getLastName());
        existingEmployee.setPhone(updatedEmployee.getPhone());
        existingEmployee.setEmail(updatedEmployee.getEmail());
        existingEmployee.setNatId(updatedEmployee.getNatId());
        existingEmployee.setNssfId(updatedEmployee.getNssfId());
        existingEmployee.setActive(updatedEmployee.isActive());
        return existingEmployee;
    }

}
