package backend.ServiceImpl;

import backend.Entities.Image;
import backend.Repository.ImageRepository;
import backend.Service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;


    @Override
    public Mono<Image> create(Image image) {
        return imageRepository.save(Image.from(image)
                .createdAt(LocalDateTime.now())
                .build());
    }
}
