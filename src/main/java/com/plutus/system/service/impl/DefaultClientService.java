package com.plutus.system.service.impl;

import com.plutus.system.model.SecurityRole;
import com.plutus.system.model.entity.Client;
import com.plutus.system.model.request.CreateClientRequest;
import com.plutus.system.model.request.FindClientRequest;
import com.plutus.system.repository.ClientRepository;
import com.plutus.system.service.ClientService;
import com.plutus.system.utils.SecurityHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultClientService implements ClientService {

    private final ClientRepository repository;

    @PreAuthorize("hasAuthority(T(com.plutus.system.model.SecurityRole).ADMIN.getGrantedAuthority())")
    @Override
    public Client create(CreateClientRequest request) {
        Client toCreate = new Client();
        toCreate.setName(request.getName());
        toCreate.setSurname(request.getSurname());
        toCreate.setEmail(request.getEmail());
        return repository.save(toCreate);
    }

    @Override
    public Optional<Client> getClientById(BigInteger clientId) {
        BigInteger principalId = SecurityHelper.getPrincipalFromSecurityContext();
        if (principalId.equals(clientId)) {
            return repository.findById(principalId);
        }
        return SecurityHelper.requireRole(SecurityRole.ADMIN, () -> repository.findById(clientId));
    }

    @Override
    public Optional<Client> findClient(FindClientRequest request) {
        BigInteger principalId = SecurityHelper.getPrincipalFromSecurityContext();
        if (request.getClientId() != null) {
            return getClientById(request.getClientId());
        }
        Client toSearch = new Client();
        toSearch.setEmail(request.getEmail());
        return SecurityHelper.requireRole(SecurityRole.ADMIN, () -> repository.findOne(Example.of(toSearch)));
    }

    @PreAuthorize("hasAuthority(T(com.plutus.system.model.SecurityRole).ADMIN.getGrantedAuthority())")
    @Override
    public Collection<Client> getAllClients() {
        return repository.findAll();
    }
}
