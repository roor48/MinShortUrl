package study.min.shorturl.controller;

import jakarta.validation.Valid;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import study.min.shorturl.data.dto.ShortUrlDto;
import study.min.shorturl.service.ShortUrlService;

import java.util.UUID;

// http://locallhost:7070/*
@RestController
public class ShortUrlController {

    private final ShortUrlService shortUrlService;
    @Autowired
    ShortUrlController(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @PostMapping("shortUrl") // http://locallhost:7070/shortUrl
    public ResponseEntity<ShortUrlDto> shortUrl(@Valid @RequestBody String originUrl) {

        ShortUrlDto response = shortUrlService.saveUrlDto(originUrl);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{UUID}") // http://locallhost:7070/{UUID}
    public ResponseEntity<ShortUrlDto> getUrlDto(@PathVariable String UUID) {

        ShortUrlDto shortUrlDto = shortUrlService.getUrlDto(UUID);

        if (shortUrlDto.getOriginUrl() == null || shortUrlDto.getOriginUrl().isBlank() ||
            shortUrlDto.getShortUrl() == null || shortUrlDto.getShortUrl().isBlank())
        {
            return  ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.FOUND) // 또는 HttpStatus.MOVED_PERMANENTLY
            .header("Location", shortUrlDto.getOriginUrl())
            .build();
    }
}
