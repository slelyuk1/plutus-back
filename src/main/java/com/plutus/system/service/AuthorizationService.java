package com.plutus.system.service;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;

public interface AuthorizationService {
    Authentication generateAuthorizationToken(Authentication authenticationToken);
}
