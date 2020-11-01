package com.plutus.system.utils;

import com.plutus.system.model.entity.employee.Employee;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public final class EmployeeUtils {
    public static Employee initEmployeeForSearchByLogin(String login) {
        Employee employee = new Employee();
        employee.setLogin(login);
        return employee;
    }
}
