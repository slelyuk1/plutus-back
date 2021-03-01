package com.plutus.system.service;

import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import java.util.Optional;

public interface JwtTokenService {
    Optional<String> getTokenFromRequest(@NonNull HttpServletRequest request);

    boolean validateToken(String authToken);

    Optional<Authentication> getAuthenticationFromToken(@NotEmpty String token);

    String getTokenFromAuthentication(@NonNull Authentication auth);
}
