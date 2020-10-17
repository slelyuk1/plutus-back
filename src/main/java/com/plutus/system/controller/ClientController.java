package com.plutus.system.controller;

import com.plutus.system.model.request.CreateClientRequest;
import com.plutus.system.model.response.ClientInfo;

import java.util.Collection;

public interface ClientController {
    ClientInfo create(CreateClientRequest request);
    ClientInfo getInfo(Long clientId);
    Collection<ClientInfo> getAll();
}
