package backend.Dto;

import backend.Entities.PurchaseOrder;

import java.util.List;

public record PurchaseOrderDetails(
        PurchaseOrder purchaseOrder,
        List<PurchaseOrderDetails> purchaseOrderDetails
) {
}
