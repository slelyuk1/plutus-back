package com.plutus.system.utils;

import com.plutus.system.model.entity.employee.Employee;
import com.plutus.system.model.entity.employee.EmployeeRole;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public final class EmployeeUtils {

    public static final String TEST_LOGIN = "test";
    public static final String TEST_PASSWORD = "12345678";


    public static Employee instantiateTestEmployee(EmployeeRole role) {
        Employee employee = new Employee();
        employee.setLogin(TEST_LOGIN);
        employee.setPassword(TEST_PASSWORD);
        employee.setRole(role);
        return employee;
    }
}
