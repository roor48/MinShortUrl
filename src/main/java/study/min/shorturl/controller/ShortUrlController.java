package study.min.shorturl.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import study.min.shorturl.data.dto.ShortUrlDto;
import study.min.shorturl.service.ShortUrlService;

// http://locallhost:7070/*
@RestController
public class ShortUrlController {

    Logger LOGGER = LoggerFactory.getLogger(ShortUrlController.class);

    private final ShortUrlService shortUrlService;
    @Autowired
    ShortUrlController(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @PostMapping("/v1/util/shorturl") // http://locallhost:7070/v1/util/shorturl
    public ResponseEntity<ShortUrlDto> post_ShortUrl(@RequestParam("origin") String origin) {

        LOGGER.info("[post_shortUrl] receive Url = {}", origin);

        ShortUrlDto response = shortUrlService.saveUrlDto(origin);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/v1/util/shorturl")
    public ResponseEntity<ShortUrlDto> get_ShortUrl(@RequestParam("origin") @NotBlank String origin) {
        LOGGER.info("[get_ShortUrl] receive Url = {}", origin);

        ShortUrlDto response = shortUrlService.saveUrlDto(origin);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{hash}") // http://locallhost:7070/{UUID}
        public ResponseEntity<ShortUrlDto> getUrlDto(@PathVariable String hash) {

        ShortUrlDto shortUrlDto = shortUrlService.getUrlDto(hash);

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
            .header("Location", shortUrlDto.getResult().getOrigin())
            .build();
    }
}
