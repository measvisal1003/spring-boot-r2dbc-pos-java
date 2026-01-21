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
@Table("branch")
public class Branch {

    public static final String LABEL = "branch";
    public static final String ID_COLUMN = "id";
    public static final String NAME_COLUMN = "name";
    public static final String ADDRESS_COLUMN = "address";
    public static final String CITY_COLUMN = "city";
    public static final String STATE_COLUMN = "state";
    public static final String PHONE_COLUMN = "phone";
    public static final String IS_ACTIVE_COLUMN = "isActive";
    public static final String CREATED_DATE_COLUMN = "createdDate";
    public static final String UPDATED_DATE_COLUMN = "updatedDate";

    private Long id;
    private String name;
    private String address;
    private String city;
    private String state;
    private String phone;
    private boolean isActive;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}
