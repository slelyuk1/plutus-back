package com.plutus.system.controller.impl;

import com.plutus.system.controller.ClientController;
import com.plutus.system.model.entity.Client;
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
        Client created = service.create(request);
        return clientToClientInfo(created);
    }

    @Override
    public ClientInfo getInfo(Optional<BigInteger> maybeClientId) {
        BigInteger clientId = maybeClientId.orElse(SecurityHelper.getPrincipalFromSecurityContext());
        Client client = service.getClientById(clientId);
        return clientToClientInfo(client);
    }

    @Override
    public Collection<ClientInfo> getAll() {
        return service.getAllClients().stream()
                .map(DefaultClientController::clientToClientInfo)
                .collect(Collectors.toList());
    }

    private static ClientInfo clientToClientInfo(Client client) {
        return new ClientInfo(client.getId(), client.getName(), client.getSurname(), client.getCreatedWhen());
    }
}
