package backend.Entities;

import lombok.Getter;

@Getter
public enum PaymentMethod {
    KHQR("KHQR"),
    CASH("CASH");

    private final String value;

    PaymentMethod(String value) {
        this.value = value;
    }

}
