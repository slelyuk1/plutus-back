package com.plutus.system.model.response;

import com.plutus.system.model.entity.CreditTariff;
import lombok.Value;

import java.math.BigDecimal;
import java.math.BigInteger;

@Value
public class CreditTariffInfo {
    BigInteger id;
    String name;
    Integer percent;
    BigDecimal limit;

    public static CreditTariffInfo fromCreditTariff(CreditTariff creditTariff) {
        return new CreditTariffInfo(creditTariff.getId(), creditTariff.getName(), creditTariff.getPercent(), creditTariff.getLimit());
    }
}
