package study.min.shorturl.data.dao;

import java.util.Optional;
import study.min.shorturl.data.entity.ShortUrlEntity;

public interface ShortUrlDao {
    ShortUrlEntity saveUrlEntity(ShortUrlEntity shortUrlEntity);
    Optional<ShortUrlEntity> getUrlEntity(String shortUrl);
    Optional<ShortUrlEntity> findByOriginalUrl(String shortUrl);
    boolean existByShortUrl(String shortUrl);
}
