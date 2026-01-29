package backend.Controller;

import backend.Entities.Image;
import backend.Service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/image")
@CrossOrigin("*")
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/create")
    public Mono<Image> create(@RequestBody Image image) {
        return imageService.create(image);
    }


}
