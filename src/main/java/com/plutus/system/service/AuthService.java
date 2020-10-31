package com.plutus.system.service;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public interface AuthService {
    String generateToken(AbstractAuthenticationToken request);
}
