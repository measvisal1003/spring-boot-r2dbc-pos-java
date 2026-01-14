package backend.Mapper;

import backend.Dto.EmployeeDto;
import backend.Dto.UserDto;
import backend.Entities.Employee;
import backend.Entities.User;

public class UserMapper {

    // Convert from Entity to Dto
    public static UserDto toDto(User user) {
        if (user == null) {
            return null;
        }

        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());
//        dto.setActive(user.isActive());
        return dto;
    }

    // Convert from DTO to Entity
    public static User toEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }

        User entity = new User();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setRole(entity.getRole());
        entity.setActive(entity.isActive());

        return entity;
    }
}
