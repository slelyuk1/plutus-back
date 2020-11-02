package com.plutus.system.service;

import com.plutus.system.model.entity.Client;
import com.plutus.system.model.request.CreateClientRequest;
import com.plutus.system.model.request.FindClientRequest;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Optional;

public interface ClientService {
    Client create(CreateClientRequest request);

    Client getClientById(BigInteger clientId);

    Optional<Client> findClient(FindClientRequest request);

    Collection<Client> getAllClients();
}
