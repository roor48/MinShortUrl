package study.min.shorturl.data.handler;

import study.min.shorturl.data.entity.ShortUrlEntity;

public interface ShortUrlHandler {
    ShortUrlEntity saveShortUrlEntity(String shortUrl);
    ShortUrlEntity getShortUrlEntity(String shortUrl);
}
