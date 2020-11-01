package com.plutus.system.service;

import com.plutus.system.model.entity.employee.Employee;

public interface EmployeeService {
    Employee create(Employee employee);

    boolean exists(Employee employee);
}
