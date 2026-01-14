package backend.Utils;

public record ErrorResponse(
        String status,
        Integer code,
        String message
) {
}
