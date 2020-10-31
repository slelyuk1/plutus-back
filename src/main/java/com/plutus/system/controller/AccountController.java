package com.plutus.system.controller;

import com.plutus.system.model.request.CreateAccountRequest;
import com.plutus.system.model.response.AccountInfo;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/secured/account")
public interface AccountController {
    @PostMapping("/create")
    AccountInfo create(@RequestParam Optional<Long> userId, @Valid @RequestBody CreateAccountRequest request);

    @GetMapping({"/", "/{accountId}"})
    AccountInfo getInfo(@PathVariable Optional<Long> accountId);

    @GetMapping("/all")
    Collection<AccountInfo> getAll(@RequestParam Optional<Long> clientId);
}
