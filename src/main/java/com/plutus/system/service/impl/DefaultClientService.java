package com.plutus.system.service.impl;

import com.plutus.system.model.entity.Client;
import com.plutus.system.repository.ClientRepository;
import com.plutus.system.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class DefaultClientService implements ClientService {

    private final ClientRepository repository;

    @Override
    public Client create(Client toCreate) {
        return repository.save(toCreate);
    }

    @Override
    public Client getClientById(long clientId) {
        return repository.getOne(clientId);
    }

    @Override
    public Collection<Client> getAllClients() {
        return repository.findAll();
    }
}
