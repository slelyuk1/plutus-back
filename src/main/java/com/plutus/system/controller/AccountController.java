package com.plutus.system.controller;

import com.plutus.system.model.request.CreateAccountRequest;
import com.plutus.system.model.response.AccountInfo;

import java.util.Collection;

public interface AccountController {
    AccountInfo createAccount(CreateAccountRequest request);
    Collection<AccountInfo> getAll(Long clientId);
    AccountInfo getInfo(Long accountId);
}
