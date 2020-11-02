package com.plutus.system.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@Entity(name = "account")
public class Account {

    @Id
    @GeneratedValue
    private BigInteger id;

    @Column
    @NotEmpty
    private String number;

    @Column
    @NotNull
    private String pin;

    @Column
    @NotNull
    // TODO: 10/6/2020 Check if there exists better alternative to storing money
    private BigDecimal money = BigDecimal.ZERO;

    @ToString.Exclude
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id")
    private Client owner;

    public void setOwner(Client owner) {
        this.owner = owner;
        owner.getAccounts().add(this);
    }

    public Account(BigInteger id) {
        this.id = id;
    }

    // TODO: 10/6/2020 Solve StackOverflow exception when calling toString
}
