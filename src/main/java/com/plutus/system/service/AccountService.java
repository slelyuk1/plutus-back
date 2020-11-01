package com.plutus.system.service;

import com.plutus.system.model.entity.Account;
import com.plutus.system.model.entity.Client;

import java.util.Collection;
import java.util.Optional;

public interface AccountService {
    Account create(Account account);

    Collection<Account> getClientAccounts(Client client);

    Optional<Account> find(Account similar);
}
