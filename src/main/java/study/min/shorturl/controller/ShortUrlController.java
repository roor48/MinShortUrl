package study.min.shorturl.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import study.min.shorturl.data.dto.ShortUrlDto;
import study.min.shorturl.service.ShortUrlService;

// http://locallhost:7070/*
@RestController
public class ShortUrlController {

    private final ShortUrlService shortUrlService;
    @Autowired
    ShortUrlController(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @PostMapping("/v1/util/shorturl") // http://locallhost:7070/v1/util/shorturl
    public ResponseEntity<ShortUrlDto> shortUrl(@RequestBody String origin) {

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
