package com.plutus.system.controller;

import com.plutus.system.configuration.security.SecurityConfiguration;
import com.plutus.system.model.request.client.CreateClientRequest;
import com.plutus.system.model.response.ClientInfo;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping(SecurityConfiguration.SECURED_API_ENDPOINT + "/client")
public interface ClientController {
    @PostMapping("/create")
    ClientInfo create(@Valid @RequestBody CreateClientRequest request);

    @GetMapping({"/", "/{clientId}"})
    ClientInfo getInfo(@PathVariable("clientId") Optional<BigInteger> maybeClientId);

    @GetMapping("/all")
    Collection<ClientInfo> getAll();
}
