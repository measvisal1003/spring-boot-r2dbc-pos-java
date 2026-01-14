package backend.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class ApiException extends RuntimeException{
    private final HttpStatus status;
    private final Integer code;
    private final String msg;

}
