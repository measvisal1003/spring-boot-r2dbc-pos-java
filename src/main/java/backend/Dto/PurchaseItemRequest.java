package backend.Dto;

import java.math.BigDecimal;

public record PurchaseItemRequest(
        Long productId,
        int quantity,
        BigDecimal unitPrice
) {
}
