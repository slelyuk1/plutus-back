package com.plutus.system.service.impl;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigInteger;

public class DefaultAuthenticationProvider extends DaoAuthenticationProvider {
    @Override
    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {
        Authentication previous = super.createSuccessAuthentication(principal, authentication, user);
        UserDetails details = (UserDetails) previous.getPrincipal();
        return new UsernamePasswordAuthenticationToken(new BigInteger(details.getUsername()), previous.getCredentials(), previous.getAuthorities());
    }
}
