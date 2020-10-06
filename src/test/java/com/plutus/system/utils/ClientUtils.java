package com.plutus.system.utils;

import com.plutus.system.model.entity.Client;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ClientUtils {
    public static final String CLIENT_TEST_NAME_OR_SURNAME = "Test";
    public static final String CLIENT_TEST_EMAIL = "test@gmail.com";

    public static Client createTestClientForPersistence() {
        Client clientForTest = new Client();
        clientForTest.setName(CLIENT_TEST_NAME_OR_SURNAME);
        clientForTest.setSurname(CLIENT_TEST_NAME_OR_SURNAME);
        clientForTest.setEmail(CLIENT_TEST_EMAIL);
        return clientForTest;
    }
}
