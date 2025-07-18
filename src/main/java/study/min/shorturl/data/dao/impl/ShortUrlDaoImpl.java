package study.min.shorturl.data.dao.impl;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import study.min.shorturl.data.dao.ShortUrlDao;
import study.min.shorturl.data.entity.ShortUrlEntity;
import study.min.shorturl.data.repository.ShortUrlRepository;

@Service
public class ShortUrlDaoImpl implements ShortUrlDao {

    private final ShortUrlRepository shortUrlRepository;

    @Autowired
    public ShortUrlDaoImpl(ShortUrlRepository shortUrlRepository) {
        this.shortUrlRepository = shortUrlRepository;
    }

    @Override
    public ShortUrlEntity saveUrlEntity(ShortUrlEntity shortUrlEntity) {
        return shortUrlRepository.save(shortUrlEntity);
    }

    @Override
    public Optional<ShortUrlEntity> getUrlEntity(String hash) {
        return shortUrlRepository.findById(hash);
    }

    @Override
    public Optional<ShortUrlEntity> findByOriginalUrl(String origin) {
        return shortUrlRepository.findByOrigin(origin);
    }

    @Override
    public boolean existByShortUrl(String UUID) {
        return shortUrlRepository.existsById(UUID);
    }
}
