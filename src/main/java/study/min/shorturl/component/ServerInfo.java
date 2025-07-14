package study.min.shorturl.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServerInfo {

    @Value("${server.port}")
    private String serverPort;

    @Value("${server.address:localhost}")
    private String serverAddress;

    public String getBaseUrl() {
        return  "http://" + serverAddress + ":" + serverPort + "/";
    }
}
