package com.plutus.system.model.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CreateAutomaticTransferRequest {
    private String name;
    private Long fromId;
    private Long toId;
    private Long period;
    private LocalDate nextPaymentTime;
    private BigDecimal transferAmount;
    private BigDecimal toPayAmount;
    private String description;
}
