package com.plutus.system.model.entity.employee;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Data
@Entity
public class Employee {
    @Id
    @GeneratedValue
    private BigInteger id;

    @NotEmpty
    @Column(unique = true)
    private String login;

    @NotEmpty
    @Column
    private String password;

    @NotNull
    @Column
    private EmployeeRole role;
}
