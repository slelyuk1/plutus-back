package com.plutus.system.configuration.security;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class JwtTokenConfiguration {
    private final String secret;
    private final long lifeInMs;

    public JwtTokenConfiguration(@Value("${app.jwt.secret}") String secret,
                                 @Value("${app.jwt.lifeInMs}") long lifeInMs) {
        this.secret = secret;
        this.lifeInMs = lifeInMs;
    }
}
