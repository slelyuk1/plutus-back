package com.plutus.system.model.response;

import lombok.Value;

@Value
public class TokenInfo {
    String jwtToken;
    String csrfToken;
}
