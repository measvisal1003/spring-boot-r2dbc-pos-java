package backend.Utils;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Map;

public record ResponseDTO<T>(
        HttpStatus httpStatus,
        Integer statusCode,
        String message,

        ArrayList<Map.Entry<String, String>> errors,
        Object data
) {
}
