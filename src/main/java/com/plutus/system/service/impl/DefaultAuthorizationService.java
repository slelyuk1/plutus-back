package com.plutus.system.service.impl;

import com.plutus.system.service.AuthorizationService;
import com.plutus.system.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultAuthorizationService implements AuthorizationService {

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication generateAuthorizationToken(Authentication authenticationToken) {
        return authenticationManager.authenticate(authenticationToken);
    }
}
