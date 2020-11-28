package com.plutus.system.controller.impl;

import com.plutus.system.controller.ClientController;
import com.plutus.system.exception.NotExistsException;
import com.plutus.system.model.entity.Client;
import com.plutus.system.model.request.client.CreateClientRequest;
import com.plutus.system.model.request.client.FindClientRequest;
import com.plutus.system.model.response.ClientInfo;
import com.plutus.system.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class DefaultClientController implements ClientController {

    private final ClientService service;

    @Override
    public ClientInfo create(@Valid CreateClientRequest request) {
        return ClientInfo.fromClient(service.create(request));
    }

    @PreAuthorize("hasAuthority(T(com.plutus.system.model.SecurityRole).ADMIN.getGrantedAuthority())")
    @Override
    public ClientInfo getInfo(BigInteger clientId) {
        FindClientRequest request = new FindClientRequest();
        request.setClientId(clientId);
        Client found = service.findClient(request)
                .orElseThrow(() -> new NotExistsException("Client", clientId));
        return ClientInfo.fromClient(found);
    }

    @PreAuthorize("hasAuthority(T(com.plutus.system.model.SecurityRole).ADMIN.getGrantedAuthority())")
    @Override
    public Collection<ClientInfo> getAll() {
        return service.getAllClients().stream()
                .map(ClientInfo::fromClient)
                .collect(Collectors.toList());
    }
}
