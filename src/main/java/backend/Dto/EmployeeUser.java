package backend.Dto;

import java.util.List;

public record EmployeeUser(
        EmployeeDto employee,
        List<UserDto> user
) {
}
