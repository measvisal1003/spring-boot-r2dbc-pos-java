package backend.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("image")
public class Image {

    public static final String LABEL = "image";
    public static final String ID_COLUMN = "id";
    public static final String OBJECT_KEY_COLUMN = "object_key";
    public static final String URL_COLUMN = "url";
    public static final String CONTENT_TYPE_COLUMN = "content_type";
    public static final String SIZE_COLUMN = "size";
    public static final String CREATED_AT_COLUMN = "created_at";

    @Id
    @Column(ID_COLUMN)
    private Long id;
    @Column(OBJECT_KEY_COLUMN)
    private String objectKey;
    @Column(URL_COLUMN)
    private String url;
    @Column(CONTENT_TYPE_COLUMN)
    private String contentType;
    @Column(SIZE_COLUMN)
    private Long size;
    @Column(CREATED_AT_COLUMN)
    private LocalDateTime createdAt;

    public static ImageBuilder from(Image image) {
        return Image.builder()
                .id(image.getId())
                .objectKey(image.getObjectKey())
                .url(image.getUrl())
                .contentType(image.getContentType())
                .size(image.getSize())
                .createdAt(image.getCreatedAt());
    }
}
