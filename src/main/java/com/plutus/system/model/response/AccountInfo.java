package com.plutus.system.model.response;

import com.plutus.system.model.entity.Account;
import lombok.Value;

import java.math.BigDecimal;
import java.math.BigInteger;

@Value
public class AccountInfo {
    BigInteger id;
    String number;
    BigDecimal moneyAmount;
    Long creditTariffId;
    BigInteger clientId;

    public static AccountInfo fromAccount(Account account) {
        return new AccountInfo(account.getId(), account.getNumber(), account.getMoney(), null, account.getOwner().getId());
    }
}
