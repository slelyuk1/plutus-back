package com.plutus.system.service.impl;

import com.plutus.system.model.SecurityRole;
import com.plutus.system.model.entity.Client;
import com.plutus.system.repository.ClientRepository;
import com.plutus.system.service.ClientService;
import com.plutus.system.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class DefaultClientService implements ClientService {

    private final ClientRepository repository;

    @PreAuthorize("hasAuthority(T(com.plutus.system.model.SecurityRole).ADMIN.getGrantedAuthority())")
    @Override
    public Client create(Client toCreate) {

        return repository.save(toCreate);
    }

    @Override
    public Client getClientById(BigInteger clientId) {
        BigInteger principalId = SecurityUtils.getPrincipalFromSecurityContext();
        if (principalId.equals(clientId)) {
            return repository.getOne(principalId);
        }
        return SecurityUtils.requireRole(SecurityRole.ADMIN, () -> repository.getOne(clientId));
    }

    @PreAuthorize("hasAuthority(T(com.plutus.system.model.SecurityRole).ADMIN.getGrantedAuthority())")
    @Override
    public Collection<Client> getAllClients() {
        return repository.findAll();
    }
}
