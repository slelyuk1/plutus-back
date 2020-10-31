package com.plutus.system.service.impl;

import com.plutus.system.service.AuthService;
import com.plutus.system.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultAuthService implements AuthService {

    private final JwtTokenService tokenService;
    private final AuthenticationManager authenticationManager;

    @Override
    public String generateToken(AbstractAuthenticationToken token) {
        return tokenService.getTokenFromAuthentication(authenticationManager.authenticate(token));
    }
}
