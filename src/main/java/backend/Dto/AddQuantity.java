package backend.Dto;

import backend.Entities.Method;

public record AddQuantity(
        Method method,
        int addQuantity
) {
}
