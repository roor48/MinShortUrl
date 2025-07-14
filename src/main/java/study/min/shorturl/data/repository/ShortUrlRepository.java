package study.min.shorturl.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.min.shorturl.data.entity.ShortUrlEntity;

import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<ShortUrlEntity, String> {
    Optional<ShortUrlEntity> findByOriginUrl(String originalUrl);
}
