package com.plutus.system.model.response;

import lombok.Value;

import java.time.LocalDate;

@Value
public class ClientInfo {
    Long id;
    String name;
    String surname;
    LocalDate createdWhen;
}
