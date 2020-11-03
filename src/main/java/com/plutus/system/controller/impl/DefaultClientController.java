package com.plutus.system.controller.impl;

import com.plutus.system.controller.ClientController;
import com.plutus.system.model.request.CreateClientRequest;
import com.plutus.system.model.response.ClientInfo;
import com.plutus.system.service.ClientService;
import com.plutus.system.utils.SecurityHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class DefaultClientController implements ClientController {

    private final ClientService service;

    @Override
    public ClientInfo create(@Valid CreateClientRequest request) {
        return ClientInfo.fromClient(service.create(request));
    }

    @Override
    public ClientInfo getInfo(Optional<BigInteger> maybeClientId) {
        BigInteger clientId = maybeClientId.orElse(SecurityHelper.getPrincipalFromSecurityContext());
        return ClientInfo.fromClient(service.getClientById(clientId));
    }

    @Override
    public Collection<ClientInfo> getAll() {
        return service.getAllClients().stream()
                .map(ClientInfo::fromClient)
                .collect(Collectors.toList());
    }
}
