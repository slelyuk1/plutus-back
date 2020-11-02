package com.plutus.system.utils;

import com.plutus.system.model.entity.Account;
import com.plutus.system.model.entity.employee.Employee;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.NONE)
public final class RepositorySearchUtils {
    public static Employee employeeForSearchByLogin(String login) {
        Employee employee = new Employee();
        employee.setLogin(login);
        return employee;
    }

    public static Account accountForSearchByNumber(String number) {
        Account account = new Account();
        account.setNumber(number);
        return account;
    }
}
