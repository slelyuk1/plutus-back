package com.plutus.system.model.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AdminTokenRequest {
    @NotEmpty
    private String login;
    @NotEmpty
    private String password;
}
