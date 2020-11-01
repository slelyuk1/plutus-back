package com.plutus.system.model.response;

import lombok.Value;

import java.math.BigDecimal;
import java.math.BigInteger;

@Value
public class AccountInfo {
    BigInteger id;
    String number;
    BigDecimal moneyAmount;
    Long creditTariffId;
    BigInteger userId;
}
