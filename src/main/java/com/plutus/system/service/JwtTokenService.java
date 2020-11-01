package com.plutus.system.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface JwtTokenService {
    Optional<String> getTokenFromRequest(@NotNull HttpServletRequest request);

    boolean validateToken(String authToken);

    Optional<Authentication> getAuthenticationFromToken(@NotEmpty String token);

    String getTokenFromAuthentication(@NotNull Authentication auth);
}
