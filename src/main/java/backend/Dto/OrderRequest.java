package backend.Dto;

import backend.Entities.PaymentMethod;

public record OrderRequest(
        String code, //product code
        int quantity,
        Long customerId,
        PaymentMethod paymentMethod
) {
}
