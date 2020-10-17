package com.plutus.system.model.response;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public class TransferInfo {
    Long id;
    Long fromId;
    Long toId;
    String transferStatus;
    BigDecimal amount;
    LocalDate createdWhen;
    String description;
}
