package com.plutus.system.model.response;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public class CreditTariffInfo {
    Long id;
    Integer percent;
    BigDecimal limit;
    LocalDate createdWhen;
}
