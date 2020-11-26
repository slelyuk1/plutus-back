package com.plutus.system.service.impl;

import com.plutus.system.model.SecurityRole;
import com.plutus.system.model.entity.Account;
import com.plutus.system.model.entity.Client;
import com.plutus.system.model.entity.CreditTariff;
import com.plutus.system.model.request.CreateAccountRequest;
import com.plutus.system.model.request.FindAccountRequest;
import com.plutus.system.model.request.FindClientRequest;
import com.plutus.system.repository.AccountRepository;
import com.plutus.system.service.AccountService;
import com.plutus.system.utils.SecurityHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class DefaultAccountService implements AccountService {

    private final AccountRepository repository;
    private final PasswordEncoder encoder;

    @PreAuthorize("hasAuthority(T(com.plutus.system.model.SecurityRole).ADMIN.getGrantedAuthority())")
    @Override
    public Account create(CreateAccountRequest request) {
        Account toCreate = new Account();
        toCreate.setNumber(request.getNumber());
        toCreate.setPin(encoder.encode(request.getPin()));

        Client owner = new Client(request.getOwnerId());
        toCreate.setOwner(owner);

        CreditTariff creditTariff = new CreditTariff(request.getCreditTariffId());
        toCreate.setCreditTariff(creditTariff);

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
            return findAccountById(request.getAccountId());
        }
        Account searcher = new Account();
        searcher.setNumber(request.getAccountNumber());
        return SecurityHelper.requireRole(SecurityRole.ADMIN, () -> repository.findOne(Example.of(searcher)));
    }

    @PreAuthorize("hasAnyAuthority(T(com.plutus.system.model.SecurityRole).ADMIN.getGrantedAuthority(), " +
            "T(com.plutus.system.model.SecurityRole).ADMIN.getGrantedAuthority())")
    @Override
    public Collection<Account> findClientAccounts(Optional<FindClientRequest> maybeRequest) {
        if (maybeRequest.isPresent()) {
            FindClientRequest request = maybeRequest.get();
            Client forSearch = new Client(request.getClientId());
            forSearch.setEmail(request.getEmail());
            if (SecurityHelper.getPrincipalFromSecurityContext().equals(forSearch.getId())) {
                return repository.findAccountsByOwner(forSearch);
            }
            return SecurityHelper.requireRole(SecurityRole.ADMIN, () -> repository.findAccountsByOwner(forSearch));
        }
        return SecurityHelper.requireRole(SecurityRole.ADMIN, repository::findAll);
    }

    private Optional<Account> findAccountById(BigInteger id) {
        BigInteger principalId = SecurityHelper.getPrincipalFromSecurityContext();
        if (principalId.equals(id)) {
            return repository.findById(id);
        }
        return SecurityHelper.requireRole(SecurityRole.ADMIN, () -> repository.findById(id));
    }
}
