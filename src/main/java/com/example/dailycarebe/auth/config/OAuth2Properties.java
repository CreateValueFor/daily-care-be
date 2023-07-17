package com.example.dailycarebe.auth.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "oauth2")
public class OAuth2Properties {
    private String url;
    private String realm;
    private String clientId;
    private String clientSecret;
    private String issuerUri;
    private String signingKey;
    private String publicKey;
    private int accessTokenDuration = 60 * 60 * 12; // default 12 hours.
    private int refreshTokenDuration = 60 * 60 * 24 * 30; // default 30 days.
}
