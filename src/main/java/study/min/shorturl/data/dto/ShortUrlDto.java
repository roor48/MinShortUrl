package study.min.shorturl.data.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import study.min.shorturl.data.entity.ShortUrlEntity;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShortUrlDto {
    @NotNull
    private String shortUrl;
    @NotNull
    private String originUrl;



    public ShortUrlEntity toUrlEntity() {
        return new ShortUrlEntity(shortUrl, originUrl);
    }
}
