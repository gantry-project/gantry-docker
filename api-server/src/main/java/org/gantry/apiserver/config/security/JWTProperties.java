package org.gantry.apiserver.config.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Component
@ConfigurationProperties(prefix = "gantry.jwt")
public class JWTProperties {
    public static String AUTHZ_HEADER = "Authorization";
    public static String REFRESH_HEADER = "refresh-token";
    public static String BEARER_PREFIX = "Bearer ";

    private String secret = "default-secret";

    private boolean secretGenerating = true;

    private String generatedSecretPostfix = LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE);
//    private String generatedSecretPostfix = UUID.randomUUID().toString();

    private long expirationSecond = 60*60L;

//    public String getSecret() {
//        return secretGenerating ? secret + "-" + generatedSecretPostfix : secret;
//    }
}
