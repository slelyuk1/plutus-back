package com.plutus.system.controller.impl;

import com.plutus.system.controller.AuthController;
import com.plutus.system.model.request.ATMTokenRequest;
import com.plutus.system.model.response.TokenInfo;
import com.plutus.system.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DefaultAuthController implements AuthController {

    private final AuthService service;

    @Override
    public TokenInfo getToken(ATMTokenRequest request) {
        AbstractAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getAccountId(), request.getPin());
        return new TokenInfo(service.generateToken(authenticationToken));
    }
}
