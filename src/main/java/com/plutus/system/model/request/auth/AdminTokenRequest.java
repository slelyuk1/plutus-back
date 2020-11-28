package com.plutus.system.model.request.auth;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AdminTokenRequest {
    @NotEmpty
    private String login;
    @NotEmpty
    private String password;
}
