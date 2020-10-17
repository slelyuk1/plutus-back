package com.plutus.system.service;

import com.plutus.system.model.entity.Account;
import com.plutus.system.model.entity.Client;

import java.util.Collection;

public interface AccountService {
    Account createAccount(Account account);

    Collection<Account> getClientAccounts(Client client);

    Account getAccountById(Long id);
}
