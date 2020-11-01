package com.plutus.system.controller.impl;

import com.plutus.system.controller.AccountController;
import com.plutus.system.model.entity.Account;
import com.plutus.system.model.request.CreateAccountRequest;
import com.plutus.system.model.response.AccountInfo;
import com.plutus.system.service.AccountService;
import com.plutus.system.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class DefaultAccountController implements AccountController {
    private final AccountService service;

    @Override
    public AccountInfo create(Optional<Long> userId, CreateAccountRequest request) {
        throw new UnsupportedOperationException("Creation of account is not supported yet");
    }

    @Override
    public AccountInfo getInfo(Optional<BigInteger> maybeAccountId) {
        BigInteger accountId = maybeAccountId.orElse(SecurityUtils.getPrincipalFromSecurityContext());
        Account toFind = new Account();
        toFind.setId(accountId);
        return service.find(toFind)
                .map(DefaultAccountController::accountToAccountInfo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Account with id %s was not found!", accountId)));
    }

    @PreAuthorize("hasAuthority(T(com.plutus.system.model.SecurityRole).ADMIN.getGrantedAuthority())")
    @Override
    public Collection<AccountInfo> getAll(Optional<Long> clientId) {
        throw new UnsupportedOperationException();
    }

    public static AccountInfo accountToAccountInfo(Account account) {
        return new AccountInfo(account.getId(), account.getNumber(), account.getMoney(), null, account.getOwner().getId());
    }
}
