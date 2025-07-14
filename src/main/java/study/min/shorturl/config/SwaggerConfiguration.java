package study.min.shorturl.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI api() {
        return new OpenAPI()
            .components(new Components())
            .info(info());
    }

    private Info info() {
        return new Info()
            .title("Open API 테스트")
            .description("description")
            .version("0.0.1-SNAPSHOT");
    }
}
