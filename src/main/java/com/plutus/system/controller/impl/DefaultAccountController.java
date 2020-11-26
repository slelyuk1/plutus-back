package com.plutus.system.controller.impl;

import com.plutus.system.controller.AccountController;
import com.plutus.system.model.request.CreateAccountRequest;
import com.plutus.system.model.request.FindAccountRequest;
import com.plutus.system.model.request.FindClientRequest;
import com.plutus.system.model.response.AccountInfo;
import com.plutus.system.service.AccountService;
import com.plutus.system.utils.SecurityHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @Override
    public AccountInfo getInfo(Optional<BigInteger> maybeAccountId) {
        BigInteger accountIdToFind = maybeAccountId
                .orElseGet(SecurityHelper::getPrincipalFromSecurityContext);
        FindAccountRequest findAccountRequest = new FindAccountRequest();
        findAccountRequest.setAccountId(accountIdToFind);
        return service.find(findAccountRequest)
                .map(AccountInfo::fromAccount)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Account with id %s was not found!", accountIdToFind)));
    }

    // todo think through and refactor security code
    @Override
    public Collection<AccountInfo> getAll(Optional<BigInteger> maybeClientId) {
        Optional<FindClientRequest> maybeFindRequest = maybeClientId
                .map(clientId -> {
                    FindClientRequest request = new FindClientRequest();
                    request.setClientId(clientId);
                    return request;
                });
        return service.findClientAccounts(maybeFindRequest).stream()
                .map(AccountInfo::fromAccount)
                .collect(Collectors.toList());
    }
}
