package com.plutus.system.service;

import com.plutus.system.model.entity.Client;
import com.plutus.system.model.request.CreateClientRequest;
import com.plutus.system.model.response.ClientInfo;

import java.util.Collection;

public interface ClientService {
    Client create(Client toCreate);

    Client getClientById(long clientId);

    Collection<Client> getAllClients();
}
