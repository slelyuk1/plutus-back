package com.plutus.system.model.request;

import com.plutus.system.validation.annotation.Email;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateClientRequest {
    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;
    @Email
    private String email;
}
