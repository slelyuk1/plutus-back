package com.plutus.system.service.impl;

import com.plutus.system.model.SecurityRole;
import com.plutus.system.model.entity.Account;
import com.plutus.system.model.entity.Client;
import com.plutus.system.model.request.CreateAccountRequest;
import com.plutus.system.model.request.FindAccountRequest;
import com.plutus.system.model.request.FindClientRequest;
import com.plutus.system.repository.AccountRepository;
import com.plutus.system.repository.ClientRepository;
import com.plutus.system.service.AccountService;
import com.plutus.system.utils.SecurityHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultAccountService implements AccountService {

    private final AccountRepository repository;
    private final ClientRepository clientRepository;
    private final PasswordEncoder encoder;

    @PreAuthorize("hasAuthority(T(com.plutus.system.model.SecurityRole).ADMIN.getGrantedAuthority())")
    @Override
    public Account create(CreateAccountRequest request) {
        Account toCreate = new Account();
        toCreate.setNumber(request.getNumber());
        toCreate.setPin(encoder.encode(request.getPin()));

        Client owner = new Client(request.getOwnerId());
        toCreate.setOwner(owner);

        return repository.save(toCreate);
    }

    @Override
    public Collection<Account> getClientAccounts(Client client) {
        BigInteger currentClientId = SecurityHelper.getPrincipalFromSecurityContext();
        Account accountToFind = new Account();
        accountToFind.setOwner(client);
        if (currentClientId.equals(client.getId())) {
            return repository.findAll(Example.of(accountToFind));
        }
        return SecurityHelper.requireRole(SecurityRole.ADMIN, () -> repository.findAll(Example.of(accountToFind)));
    }

    @Override
    public Optional<Account> find(FindAccountRequest request) {
        if (request.getAccountId() != null) {
            return Optional.of(findAccountById(request.getAccountId()));
        }
        Account searcher = new Account();
        searcher.setNumber(request.getAccountNumber());
        return SecurityHelper.requireRole(SecurityRole.ADMIN, () -> repository.findOne(Example.of(searcher)));
    }

    @Override
    public Collection<Account> findClientAccounts(FindClientRequest request) {
        // TODO: 11/2/2020 Finish
        return SecurityHelper.requireRole(SecurityRole.ADMIN, () -> repository.findAccountsByOwner(new Client(request.getClientId())));
    }

    private Account findAccountById(BigInteger id) {
        BigInteger principalId = SecurityHelper.getPrincipalFromSecurityContext();
        if (principalId.equals(id)) {
            // TODO: 11/2/2020 Exception may be thrown
            return repository.getOne(id);
        }
        return SecurityHelper.requireRole(SecurityRole.ADMIN, () -> repository.getOne(id));
    }
}
