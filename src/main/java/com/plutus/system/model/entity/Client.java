package com.plutus.system.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
public class Client {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    @NotEmpty
    private String name;

    @Column
    @NotEmpty
    private String surname;

    @Column
    @Email
    // TODO: 10/6/2020 custom email validation
    private String email;

    @Column
    @NotNull
    // TODO: 10/6/2020 Solve problem with timezones
    private LocalDateTime createdWhen = LocalDateTime.now();
}
