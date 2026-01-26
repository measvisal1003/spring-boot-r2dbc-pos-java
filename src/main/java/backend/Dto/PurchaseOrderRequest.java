package backend.Dto;

import backend.Entities.Status;

import java.math.BigDecimal;
import java.util.List;

public record PurchaseOrderRequest(

        Long supplierId,
        Status status,
        List<PurchaseItemRequest> items

) {
}
