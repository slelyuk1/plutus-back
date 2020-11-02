package com.plutus.system.configuration.security;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Getter
@Configuration
public class AdminConfiguration {
    private final String login;
    private final String password;

    public AdminConfiguration(@Value("${app.employee.admin.login}") String login,
                              @Value("${app.employee.admin.password}") String password) {
        this.login = login;
        this.password = password;
    }
}
