package com.plutus.system.service.impl;

import com.plutus.system.service.AuthService;
import com.plutus.system.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class DefaultAuthService implements AuthService {

    private final JwtTokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final CsrfTokenRepository csrfTokenRepository;

    @Override
    public String generateAuthorizationToken(AbstractAuthenticationToken authenticationToken) {
        return tokenService.getTokenFromAuthentication(authenticationManager.authenticate(authenticationToken));
    }

    @Override
    public String generateCsrfToken(HttpServletRequest request) {
        return csrfTokenRepository.loadToken(request).getToken();
    }
}
