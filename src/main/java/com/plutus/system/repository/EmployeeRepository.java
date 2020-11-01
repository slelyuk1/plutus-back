package com.plutus.system.repository;

import com.plutus.system.model.entity.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface EmployeeRepository extends JpaRepository<Employee, BigInteger> {
}
