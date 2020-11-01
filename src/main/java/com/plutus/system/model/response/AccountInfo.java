package com.plutus.system.model.response;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class AccountInfo {
    String number;
    BigDecimal moneyAmount;
    Long creditTariffId;
    Long userId;
}
