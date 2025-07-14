package study.min.shorturl.data.handler.impl;

import jakarta.transaction.Transactional;
import java.util.Optional;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import study.min.shorturl.data.dao.ShortUrlDao;
import study.min.shorturl.data.entity.ShortUrlEntity;
import study.min.shorturl.data.handler.ShortUrlHandler;

@Service
@Transactional
public class ShortUrlHandlerImpl implements ShortUrlHandler {

    private static final int MAX_RETRIES = 10; // UUID 생성 재시도 최대 횟수

    private final ShortUrlDao shortUrlDao;

    @Autowired
    public ShortUrlHandlerImpl(ShortUrlDao shortUrlDao) {
        this.shortUrlDao = shortUrlDao;
    }

    @Override
    public ShortUrlEntity saveShortUrlEntity(String origin) {
        Optional<ShortUrlEntity> foundEntity = shortUrlDao.findByOriginalUrl(origin);

        if (foundEntity.isPresent())
            return foundEntity.get();

        return shortUrlDao.saveUrlEntity(new ShortUrlEntity(createHash(), origin));
    }

    @Override
    public ShortUrlEntity getShortUrlEntity(String hash) {
        Optional<ShortUrlEntity> shortUrlEntity = shortUrlDao.getUrlEntity(hash);

        if (shortUrlEntity.isPresent())
            return shortUrlEntity.get();

        return new ShortUrlEntity();
    }

    private String createHash() {
        String randomHash;

        int retryCount = 0;
        do {
            retryCount++;
            if (retryCount > MAX_RETRIES) {
                throw new RuntimeException("Failed to generate a unique short URL code after " + MAX_RETRIES + " retries. All possible codes might be exhausted or collision rate is too high.");
            }
            randomHash = RandomStringUtils.randomAlphabetic(7);
        } while(shortUrlDao.existByShortUrl(randomHash));

        System.out.println("[ShortUrlHandlerImpl] Created New UUID! " + randomHash);
        return randomHash;
    }
}
