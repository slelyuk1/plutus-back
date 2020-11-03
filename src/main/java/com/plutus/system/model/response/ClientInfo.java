package com.plutus.system.model.response;

import com.plutus.system.model.entity.Client;
import lombok.Value;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Value
public class ClientInfo {
    BigInteger id;
    String name;
    String surname;
    String email;
    LocalDateTime createdWhen;

    public static ClientInfo fromClient(Client client) {
        return new ClientInfo(client.getId(), client.getName(), client.getSurname(), client.getEmail(), client.getCreatedWhen());
    }
}
