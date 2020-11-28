package com.plutus.system.controller;

import com.plutus.system.model.request.auth.ATMTokenRequest;
import com.plutus.system.model.request.auth.AdminTokenRequest;
import com.plutus.system.model.response.TokenInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public interface AuthController {
    @PostMapping("/atm/token")
    TokenInfo getTokenForATM(@Valid @RequestBody ATMTokenRequest tokenRequest);

    @PostMapping("/employee/token")
    TokenInfo getTokenForAdmin(@Valid @RequestBody AdminTokenRequest tokenRequest);
}
