package backend.Entities;

import lombok.Getter;

@Getter
public enum Method {
    ADD("ADD"),
    SUBTRACT("SUBTRACT"),
    SALE("SALE"),
    RETURN("RETURN"),
    ADJUSTMENT("ADJUSTMENT");

    private final String value;

    Method(String value) {
        this.value = value;
    }
}
