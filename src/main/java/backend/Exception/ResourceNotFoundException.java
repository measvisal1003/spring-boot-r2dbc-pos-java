package backend.Exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends ApiException{
    public ResourceNotFoundException(String resourceName, String id) {
        super(
                HttpStatus.NOT_FOUND,
                HttpStatus.NOT_FOUND.value(),
                String.format("%s with id: %s not found", resourceName, id)
        );
    }

    public ResourceNotFoundException(String resourceName) {
        super(
                HttpStatus.NOT_FOUND,
                HttpStatus.NOT_FOUND.value(),
                String.format("%s not found", resourceName)
        );
    }
}
