package study.min.shorturl.service;

import study.min.shorturl.data.dto.ShortUrlDto;

public interface ShortUrlService {
    ShortUrlDto saveUrlDto(String originUrl);
    ShortUrlDto getUrlDto(String originUrl);
}
