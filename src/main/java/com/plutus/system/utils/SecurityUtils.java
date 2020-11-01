package com.plutus.system.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContext;

@NoArgsConstructor(access = AccessLevel.NONE)
public final class SecurityUtils {
    // TODO: 10/19/2020 make normally
    public static <T> T getAccountIdFromSecurityContext(SecurityContext context) {
        return (T) context.getAuthentication().getPrincipal();
    }
}
