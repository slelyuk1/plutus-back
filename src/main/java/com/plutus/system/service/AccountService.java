package com.plutus.system.service;

import com.plutus.system.model.entity.Account;
import com.plutus.system.model.request.account.CreateAccountRequest;
import com.plutus.system.model.request.account.FindAccountsRequest;
import com.plutus.system.model.request.account.FindOneAccountRequest;

import java.util.Collection;
import java.util.Optional;

public interface AccountService {
    Account create(CreateAccountRequest request);

    Optional<Account> findAccount(FindOneAccountRequest request);

    Collection<Account> findAccounts(FindAccountsRequest request);

    Collection<Account> findAllAccounts();
}
