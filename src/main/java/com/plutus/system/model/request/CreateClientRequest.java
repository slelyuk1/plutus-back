package com.plutus.system.model.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateClientRequest {
    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;

    // TODO: 11/1/2020 Email validator
    @NotEmpty
    private String email;


}
