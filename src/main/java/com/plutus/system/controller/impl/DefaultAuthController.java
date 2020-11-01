package com.plutus.system.controller.impl;

import com.plutus.system.controller.AuthController;
import com.plutus.system.model.request.ATMTokenRequest;
import com.plutus.system.model.request.AdminTokenRequest;
import com.plutus.system.model.response.TokenInfo;
import com.plutus.system.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class DefaultAuthController implements AuthController {

    private final AuthService service;

    @Override
    public TokenInfo getTokenForATM(ATMTokenRequest tokenRequest, HttpServletRequest httpRequest) {
        AbstractAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(tokenRequest.getAccountNumber(), tokenRequest.getPin());
        String jwtToken = service.generateAuthorizationToken(authToken);
        String csrfToken = service.generateCsrfToken(httpRequest);
        return new TokenInfo(jwtToken, csrfToken);
    }

    @Override
    public TokenInfo getTokenForAdmin(AdminTokenRequest tokenRequest, HttpServletRequest httpRequest) {
        AbstractAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(tokenRequest.getLogin(), tokenRequest.getPassword());
        String jwtToken = service.generateAuthorizationToken(authToken);
        String csrfToken = service.generateCsrfToken(httpRequest);
        return new TokenInfo(jwtToken, csrfToken);
    }
}
