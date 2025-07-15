package study.min.shorturl.service.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import study.min.shorturl.component.ServerInfo;
import study.min.shorturl.data.dto.ShortUrlDto;
import study.min.shorturl.data.dto.ShortUrlDto.Result;
import study.min.shorturl.data.entity.ShortUrlEntity;
import study.min.shorturl.data.handler.ShortUrlHandler;
import study.min.shorturl.service.ShortUrlService;

import java.util.Optional;

@Service
public class ShortUrlServiceImpl implements ShortUrlService {


    private Logger LOGGER = LoggerFactory.getLogger(ShortUrlServiceImpl.class);

    private final ShortUrlHandler shortUrlHandler;
    private final ServerInfo serverInfo;

    // URL 정규식 패턴
    private static final String URL_REGEX = "^(http(s)?://)?([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}(:[0-9]{1,5})?(/\\S*)?$";
    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);

    @Autowired
    ShortUrlServiceImpl(ShortUrlHandler shortUrlHandler, ServerInfo serverInfo) {
        this.shortUrlHandler = shortUrlHandler;
        this.serverInfo = serverInfo;
    }

    @Override
    public ShortUrlDto saveUrlDto(String originUrl) {

        LOGGER.info("[saveUrlDto] create short url or get short url");

        // null 또는 빈 문자열 검증
        if (originUrl == null || originUrl.isBlank()) {
            LOGGER.error("[saveUrlDto] originUrl is null or empty");
            throw new IllegalArgumentException("Original URL cannot be empty.");
        }
        // 2. URL 형식 검증 추가
        if (!isValidUrl(originUrl)) {
            LOGGER.error("[saveUrlDto] originUrl is not valid");
            throw new IllegalArgumentException("Invalid URL format. Please provide a valid http(s) URL.");
        }

        // 3. URL 정규화 (선택 사항이지만, 중복 방지를 위해 권장)
        String normalizedUrl = normalizeUrl(originUrl);

        ShortUrlEntity shortUrlEntity = shortUrlHandler.saveShortUrlEntity(normalizedUrl);

        Result result = new Result(
            shortUrlEntity.getHash(),
            serverInfo.getBaseUrl() + shortUrlEntity.getHash(),
            shortUrlEntity.getOrigin()
        );

        ShortUrlDto shortUrlDto = new ShortUrlDto(
            "ok",
            result,
            "200"
        );

        LOGGER.info("[saveUrlDto] ShortUrlDto = {}", shortUrlDto);

        return shortUrlDto;
    }

    @Override
    public ShortUrlDto getUrlDto(String UUID) {

        ShortUrlEntity shortUrlEntity = shortUrlHandler.getShortUrlEntity(UUID);

        Result result = new Result(
            shortUrlEntity.getHash(),
        serverInfo.getBaseUrl() + shortUrlEntity.getHash(),
            shortUrlEntity.getOrigin()
        );

        ShortUrlDto shortUrlDto = new ShortUrlDto(
            "ok",
            result,
            "200"
        );

        LOGGER.info("[getUrlDto] ShortUrlDto = {}", shortUrlDto);

        return shortUrlDto;
    }

    // URL 형식 검증 메서드
    private boolean isValidUrl(String url) {
        Matcher matcher = URL_PATTERN.matcher(url);
        return matcher.matches();
    }

    // URL 정규화 메서드
    private String normalizeUrl(String url) {

        LOGGER.info("[normalizeUrl] Start Normalize Url");

        try {
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "https://" + url;
            }
            URI uri = new URI(url);
            String scheme = uri.getScheme(); // http 또는 https
            String host = uri.getHost();     // www.example.com 또는 example.com
            int port = uri.getPort(); // 포트 번호 추출
            String path = uri.getPath();     // /path/to/resource
            String query = uri.getQuery();   // ?param=value
            String fragment = uri.getFragment(); // #fragment

            // 1. 호스트에서 'www.' 제거
            if (host != null && host.startsWith("www.")) {
                host = host.substring(4); // "www." 부분만 제거
            }

            // 2. 프로토콜을 항상 'https'로 통일 (선택 사항)


            // 3. 경로 마지막 슬래시 제거 (선택 사항)
            if (path != null && path.endsWith("/")) {
                path = path.substring(0, path.length() - 1);
            }
            // 경로가 비어있는 경우 "/"로 설정 (예: http://example.com -> http://example.com/)
            if (path == null || path.isEmpty()) {
                path = "/";
            }


            LOGGER.info("[normalizeUrl] Finished Normalize Url");
            // 재구성된 URI 반환
            // URI.Builder를 사용하면 더 안전하고 유연하게 URL을 재구성할 수 있습니다.
            return new URI(scheme, uri.getUserInfo(), host, port, path, query, fragment).toString();

        } catch (URISyntaxException e) {
            // 유효하지 않은 URL 형식일 경우, 그대로 반환하거나 예외 처리
            // 이 메서드 호출 전에 isValidUrl에서 걸러지므로, 여기서는 발생할 확률이 낮습니다.
            System.err.println("URL normalization failed for: " + url + " - " + e.getMessage());
            return url; // 정규화 실패 시 원본 URL 반환
        }
    }
}
