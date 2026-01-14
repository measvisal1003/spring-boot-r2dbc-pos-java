package backend.Utils;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
public class ValidationHelper {

    private final static String errorMsg = "not a valid representation! Please provide a valid value and none null ";

    public Mono<Long> validateId(Long id) {
        return id == null ?
                Mono.error(new IllegalArgumentException("id is required")) :
                Mono.just(id);
    }

    public Mono<String> validateString(String string) {
        return (string == null || string.trim().isEmpty()) ?
                Mono.error(new IllegalArgumentException(errorMsg)) :
                Mono.just(string);
    }

    public Mono<BigDecimal> validateBigDecimal(BigDecimal number) {
        return number == null ?
                Mono.error(new IllegalArgumentException(errorMsg)) :
                Mono.just(number);
    }
}
