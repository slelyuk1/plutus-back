package com.plutus.system.configuration.security;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.jwt")
public class JwtTokenConfiguration {
    private String secret;
    private long lifeInMs;
}
