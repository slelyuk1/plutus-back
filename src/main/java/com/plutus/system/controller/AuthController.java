package com.plutus.system.controller;

import com.plutus.system.model.request.ATMTokenRequest;
import com.plutus.system.model.response.TokenInfo;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public interface AuthController {
    @PostMapping("/token")
    TokenInfo getToken(@Valid @RequestBody ATMTokenRequest tokenRequest, HttpServletRequest httpRequest);
}
