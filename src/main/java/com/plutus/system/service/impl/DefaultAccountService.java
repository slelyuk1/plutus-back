package com.plutus.system.service.impl;

import com.plutus.system.exception.NotExistsException;
import com.plutus.system.model.SecurityRole;
import com.plutus.system.model.entity.Account;
import com.plutus.system.model.entity.Client;
import com.plutus.system.model.entity.CreditTariff;
import com.plutus.system.model.request.account.CreateAccountRequest;
import com.plutus.system.model.request.account.FindAccountsRequest;
import com.plutus.system.model.request.account.FindOneAccountRequest;
import com.plutus.system.model.request.client.FindClientRequest;
import com.plutus.system.repository.AccountRepository;
import com.plutus.system.service.AccountService;
import com.plutus.system.service.ClientService;
import com.plutus.system.service.CreditTariffService;
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

    private final AccountRepository accountRepository;
    private final ClientService clientService;
    private final CreditTariffService creditTariffService;
    private final PasswordEncoder encoder;

    @PreAuthorize("hasAuthority(T(com.plutus.system.model.SecurityRole).ADMIN.getGrantedAuthority())")
    @Override
    public Account create(CreateAccountRequest request) {
        Account toCreate = new Account();
        toCreate.setNumber(request.getNumber());
        toCreate.setPin(encoder.encode(request.getPin()));

        FindClientRequest findClientRequest = new FindClientRequest();
        findClientRequest.setClientId(request.getOwnerId());
        Client owner = clientService.findClient(findClientRequest)
                .orElseThrow(() -> new NotExistsException("Client", request.getOwnerId()));
        toCreate.setOwner(owner);

        CreditTariff creditTariff = creditTariffService.getById(request.getCreditTariffId())
                .orElseThrow(() -> new NotExistsException("Credit tariff", request.getCreditTariffId()));
        toCreate.setCreditTariff(creditTariff);

        return accountRepository.save(toCreate);
    }

    @Override
    public Optional<Account> findAccount(FindOneAccountRequest request) {
        if (request.getAccountId() != null) {
            return findAccountById(request.getAccountId());
        }
        Account searcher = new Account();
        searcher.setNumber(request.getAccountNumber());
        return accountRepository.findOne(Example.of(searcher));
    }

    @Override
    public Collection<Account> findAccounts(FindAccountsRequest request) {
        Account searcher = new Account();
        FindClientRequest findClientRequest = new FindClientRequest();
        findClientRequest.setClientId(request.getClientId());
        Client owner = clientService.findClient(findClientRequest)
                .orElseThrow(() -> new NotExistsException("Client", request.getClientId()));
        searcher.setOwner(owner);
        return accountRepository.findAll(Example.of(searcher));
    }

    @Override
    public Collection<Account> findAllAccounts() {
        return accountRepository.findAll();
    }

    private Optional<Account> findAccountById(BigInteger id) {
        BigInteger principalId = SecurityHelper.getPrincipalFromSecurityContext();
        if (principalId.equals(id)) {
            return accountRepository.findById(id);
        }
        return SecurityHelper.requireRole(SecurityRole.ADMIN, () -> accountRepository.findById(id));
    }
}
