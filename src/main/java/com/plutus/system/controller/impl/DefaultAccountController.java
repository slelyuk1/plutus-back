package com.plutus.system.controller.impl;

import com.plutus.system.controller.AccountController;
import com.plutus.system.model.entity.Account;
import com.plutus.system.model.request.account.CreateAccountRequest;
import com.plutus.system.model.request.account.FindAccountsRequest;
import com.plutus.system.model.request.account.FindOneAccountRequest;
import com.plutus.system.model.response.AccountInfo;
import com.plutus.system.service.AccountService;
import com.plutus.system.utils.SecurityHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class DefaultAccountController implements AccountController {
    private final AccountService service;

    @Override
    public AccountInfo create(CreateAccountRequest request) {
        return AccountInfo.fromAccount(service.create(request));
    }

    @PreAuthorize("hasAuthority(T(com.plutus.system.model.SecurityRole).ADMIN.getGrantedAuthority())")
    @Override
    public AccountInfo getInfo(BigInteger accountIdToFind) {
        return findById(accountIdToFind);
    }

    @PreAuthorize("hasAuthority(T(com.plutus.system.model.SecurityRole).ATM.getGrantedAuthority())")
    @Override
    public AccountInfo getInfo() {
        return findById(SecurityHelper.getPrincipalFromSecurityContext());
    }

    @PreAuthorize("hasAuthority(T(com.plutus.system.model.SecurityRole).ADMIN.getGrantedAuthority())")
    @Override
    public Collection<AccountInfo> getAll(Optional<FindAccountsRequest> maybeRequest) {
        Collection<Account> result = maybeRequest.map(service::findAccounts).orElseGet(service::findAllAccounts);
        return result.stream()
                .map(AccountInfo::fromAccount)
                .collect(Collectors.toList());
    }

    private AccountInfo findById(BigInteger accountIdToFind) {
        FindOneAccountRequest findOneAccountRequest = new FindOneAccountRequest();
        findOneAccountRequest.setAccountId(accountIdToFind);
        return service.findAccount(findOneAccountRequest)
                .map(AccountInfo::fromAccount)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Account with id %s was not found!", accountIdToFind)));
    }
}
