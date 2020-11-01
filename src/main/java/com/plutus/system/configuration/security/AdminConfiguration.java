package com.plutus.system.configuration.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.employee.admin")
public class AdminConfiguration {
    private String login;
    private String password;
}
