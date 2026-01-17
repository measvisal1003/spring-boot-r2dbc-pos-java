package backend.Mapper;

import backend.Dto.CustomerDto;
import backend.Dto.UserDto;
import backend.Entities.Customer;
import backend.Entities.User;

public class CustomerMapper {

    // Convert from Entity to Dto
    public static CustomerDto toDto(Customer entity) {
        if (entity == null) {
            return null;
        }

        CustomerDto dto = new CustomerDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setAddress(entity.getAddress());

        return dto;
    }

    // Convert from DTO to Entity
    public static Customer toEntity(CustomerDto dto) {
        if (dto == null) {
            return null;
        }

        Customer entity = new Customer();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setAddress(entity.getAddress());
        entity.setActive(entity.isActive());

        return entity;
    }
}
