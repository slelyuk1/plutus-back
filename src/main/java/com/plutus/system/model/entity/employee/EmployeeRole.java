package com.plutus.system.model.entity.employee;

import com.plutus.system.model.SecurityRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum EmployeeRole {
    ADMIN(SecurityRole.ADMIN);
    private final SecurityRole securityRole;

    public static Optional<EmployeeRole> fromSecurityRole(SecurityRole securityRole) {
        return Arrays.stream(values())
                .filter(employeeRole -> employeeRole.getSecurityRole().equals(securityRole))
                .findAny();
    }
}
