package com.plutus.system.service.impl;

import com.plutus.system.model.entity.employee.Employee;
import com.plutus.system.repository.EmployeeRepository;
import com.plutus.system.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultEmployeeService implements EmployeeService {
    private final EmployeeRepository repository;
    private final PasswordEncoder encoder;

    @Override
    public Employee create(Employee employee) {
        employee.setPassword(encoder.encode(employee.getPassword()));
        repository.save(employee);
        return employee;
    }

    @Override
    public boolean exists(Employee employee) {
        return repository.exists(Example.of(employee));
    }
}
