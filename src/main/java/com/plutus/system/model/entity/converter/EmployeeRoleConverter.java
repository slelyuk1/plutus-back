package com.plutus.system.model.entity.converter;

import com.plutus.system.model.SecurityRole;
import com.plutus.system.model.entity.employee.EmployeeRole;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class EmployeeRoleConverter implements AttributeConverter<EmployeeRole, String> {
    private static final String SECURITY_ROLE_NOT_FOUND = "Security role with identifier %s doesn't exist!";
    private static final String EMPLOYEE_ROLE_NOT_FOUND = "Employee role with security role %s doesn't exist!";

    @Override
    public String convertToDatabaseColumn(EmployeeRole role) {
        return role.getSecurityRole().getRoleString();
    }

    @Override
    public EmployeeRole convertToEntityAttribute(String roleStr) {
        SecurityRole securityRole = SecurityRole.fromString(roleStr)
                .orElseThrow(() -> new IllegalArgumentException(String.format(SECURITY_ROLE_NOT_FOUND, roleStr)));
        return EmployeeRole.fromSecurityRole(securityRole)
                .orElseThrow(() -> new IllegalArgumentException(String.format(EMPLOYEE_ROLE_NOT_FOUND, securityRole)));
    }
}
