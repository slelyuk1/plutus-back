package com.plutus.system.model.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateClientRequest {
    private String name;
    private String surname;
}
