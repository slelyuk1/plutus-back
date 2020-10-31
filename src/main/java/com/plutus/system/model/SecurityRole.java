package com.plutus.system.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum SecurityRole {
    ADMIN("ADMIN"), ATM("ATM");

    private final GrantedAuthority grantedAuthority;

    SecurityRole(String roleName) {
        grantedAuthority = new SimpleGrantedAuthority(roleName);
    }

    public String getRoleString() {
        return grantedAuthority.getAuthority();
    }

    public static Optional<SecurityRole> fromString(String roleName) {
        return Arrays.stream(SecurityRole.values())
                .filter(securityRole -> securityRole.getRoleString().equals(roleName))
                .findAny();
    }
}
