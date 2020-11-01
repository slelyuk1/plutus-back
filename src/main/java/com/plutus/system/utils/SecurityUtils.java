package com.plutus.system.utils;

import com.plutus.system.model.SecurityRole;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.function.Supplier;

@NoArgsConstructor(access = AccessLevel.NONE)
public final class SecurityUtils {
    // TODO: 10/19/2020 make normally
    public static <T> T getPrincipalFromSecurityContext() {
        return (T) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static <T> T requireRole(SecurityRole role, Supplier<T> supplier) {
        SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .filter(authority -> authority.equals(role.getGrantedAuthority()))
                .findAny()
                .orElseThrow(() -> new SecurityException("SecurityContext doesn't have specified role " + role.getRoleString()));
        return supplier.get();
    }
}
