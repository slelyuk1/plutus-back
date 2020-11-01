package com.plutus.system.service.impl;

import com.plutus.system.model.entity.Account;
import com.plutus.system.model.entity.Client;
import com.plutus.system.repository.AccountRepository;
import com.plutus.system.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultAccountService implements AccountService {

    private final AccountRepository repository;
    private final PasswordEncoder encoder;

    @Override
    public Account create(Account account) {
        account.setPin(encoder.encode(account.getPin()));
        return repository.save(account);
    }

    @Override
    public Collection<Account> getClientAccounts(Client client) {
        Account toFind = new Account();
        toFind.setOwner(client);
        return repository.findAll(Example.of(toFind));
    }

    @Override
    public Optional<Account> find(Account similar) {
        return repository.findOne(Example.of(similar));
    }
}
