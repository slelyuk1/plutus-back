package com.plutus.system.controller;

import com.plutus.system.model.request.CreateAccountRequest;
import com.plutus.system.model.response.AccountInfo;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/secured/account")
public interface AccountController {
    @PostMapping("/create")
    AccountInfo create(@RequestParam Optional<Long> userId, @Valid @RequestBody CreateAccountRequest request);

    @PostMapping({"/", "/{accountId}"})
    AccountInfo getInfo(@PathVariable Optional<BigInteger> accountId);

    @GetMapping("/all")
    Collection<AccountInfo> getAll(@RequestParam Optional<Long> clientId);
}
