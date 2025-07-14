package study.min.shorturl.data.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ShortUrlDto {

    private String message;
    private Result result;
    private String code;

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Result {
        @NotNull(message = "Hash cannot be null.")
        @NotBlank(message = "Hash cannot be empty.")
        String hash;
        @NotNull
        @NotBlank
        private String url;
        @NotNull
        @NotBlank
        private String origin;
    }
}
