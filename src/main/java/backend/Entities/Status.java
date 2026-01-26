package backend.Entities;

import lombok.Getter;

@Getter
public enum Status {

    PENDING("PENDING"),
    ORDERED("ORDERED"),
    RECEIVED("RECEIVED");

    public final String value;

    Status(String value) {
        this.value = value;
    }
}
