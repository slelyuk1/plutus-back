package com.plutus.system.service;

import com.plutus.system.model.entity.Account;
import com.plutus.system.model.entity.Client;
import com.plutus.system.model.request.account.CreateAccountRequest;
import com.plutus.system.model.request.account.FindAccountRequest;
import com.plutus.system.model.request.client.FindClientRequest;

import java.util.Collection;
import java.util.Optional;

public interface AccountService {
    Account create(CreateAccountRequest request);

    Collection<Account> getClientAccounts(Client client);

    Optional<Account> find(FindAccountRequest request);

    Collection<Account> findClientAccounts(Optional<FindClientRequest> request);
}
