package com.plutus.system.service;

import com.plutus.system.model.response.TokenInfo;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {
    String generateAuthorizationToken(AbstractAuthenticationToken authenticationToken);
    String generateCsrfToken(HttpServletRequest request);
}
