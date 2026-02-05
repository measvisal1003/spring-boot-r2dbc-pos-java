package backend.Dto;

import backend.Entities.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String natId;
    private String nssfId;
    private String imageUrl;
    private boolean isActive;
}
