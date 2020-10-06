package com.plutus.system.model.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Entity(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @NotNull
    private String pin;

    @Column
    @NotNull
    // TODO: 10/6/2020 Check if there exists better alternative to storing money
    private BigDecimal money = BigDecimal.ZERO;
}
