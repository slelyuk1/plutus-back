package com.plutus.system.controller;

import com.plutus.system.configuration.security.SecurityConfiguration;
import com.plutus.system.model.request.account.CreateAccountRequest;
import com.plutus.system.model.request.account.FindAccountsRequest;
import com.plutus.system.model.response.AccountInfo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Optional;

@Validated
@RestController
@RequestMapping(SecurityConfiguration.SECURED_API_ENDPOINT + "/account")
public interface AccountController {
    @PostMapping("/create")
    AccountInfo create(@Valid @RequestBody CreateAccountRequest request);

    @GetMapping("/{accountId}")
    AccountInfo getInfo(@PathVariable("accountId") BigInteger maybeAccountId);

    @GetMapping("/")
    AccountInfo getInfo();

    @GetMapping("/all")
    Collection<AccountInfo> getAll(@Valid @RequestBody Optional<FindAccountsRequest> maybeRequest);
}
