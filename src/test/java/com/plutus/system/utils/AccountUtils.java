package com.plutus.system.utils;

import com.plutus.system.model.entity.Account;
import com.plutus.system.model.entity.Client;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountUtils {
    public static final String TEST_PIN = "1234";

    public static Account createTestAccountForPersistence(Client owner) {
        Account account = new Account();
        account.setPin(TEST_PIN);
        account.setOwner(owner);
        return account;
    }
}
