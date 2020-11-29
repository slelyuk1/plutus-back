package com.plutus.system.service.impl;

import com.plutus.system.model.SecurityRole;
import com.plutus.system.model.entity.Client;
import com.plutus.system.model.request.client.CreateClientRequest;
import com.plutus.system.model.request.client.FindClientRequest;
import com.plutus.system.repository.ClientRepository;
import com.plutus.system.service.ClientService;
import com.plutus.system.utils.SecurityHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultClientService implements ClientService {

    private final ClientRepository repository;

    @Override
    public Client create(CreateClientRequest request) {
        Client toCreate = new Client();
        toCreate.setName(request.getName());
        toCreate.setSurname(request.getSurname());
        toCreate.setEmail(request.getEmail());
        return repository.save(toCreate);
    }

    @Override
    public Optional<Client> findClient(FindClientRequest request) {
        if (request.getClientId() != null) {
            return repository.findById(request.getClientId());
        }
        Client toSearch = new Client();
        toSearch.setEmail(request.getEmail());
        return repository.findOne(Example.of(toSearch));
    }

    @Override
    public Collection<Client> getAllClients() {
        return repository.findAll();
    }
}
