package com.plutus.system.model.response;

import lombok.Value;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Value
public class ClientInfo {
    BigInteger id;
    String name;
    String surname;
    LocalDateTime createdWhen;
}
