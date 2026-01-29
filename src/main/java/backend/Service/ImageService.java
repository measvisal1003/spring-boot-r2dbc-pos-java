package backend.Service;

import backend.Entities.Image;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface ImageService {

    Mono<Image> create(Image image);
}
