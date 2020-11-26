package com.plutus.system.service;

import com.plutus.system.model.entity.Account;
import com.plutus.system.model.entity.Client;
import com.plutus.system.model.request.CreateAccountRequest;
import com.plutus.system.model.request.FindAccountRequest;
import com.plutus.system.model.request.FindClientRequest;

import java.util.Collection;
import java.util.Optional;

public interface AccountService {
    Account create(CreateAccountRequest request);

    Collection<Account> getClientAccounts(Client client);

    Optional<Account> find(FindAccountRequest request);

    Collection<Account> findClientAccounts(Optional<FindClientRequest> request);
}
