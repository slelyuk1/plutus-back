package com.plutus.system.controller.impl;

import com.plutus.system.controller.AccountController;
import com.plutus.system.model.entity.Account;
import com.plutus.system.model.request.CreateAccountRequest;
import com.plutus.system.model.response.AccountInfo;
import com.plutus.system.service.AccountService;
import com.plutus.system.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

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
    public AccountInfo getInfo(Optional<Long> maybeAccountId) {
        long accountId = maybeAccountId.orElse(SecurityUtils.getAccountIdFromSecurityContext(SecurityContextHolder.getContext()));
        Account foundAccount = service.getAccountById(accountId);
        // TODO: 10/19/2020 credit tariff
        return new AccountInfo(foundAccount.getId(), foundAccount.getMoney(), null, foundAccount.getOwner().getId());
    }

    @Override
    public Collection<AccountInfo> getAll(Optional<Long> clientId) {
        throw new UnsupportedOperationException("Getting all account is not supported yet");
    }
}
