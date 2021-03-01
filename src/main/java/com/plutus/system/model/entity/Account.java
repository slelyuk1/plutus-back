package com.plutus.system.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.persistence.*;
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
    @CreditCardNumber
    private String number;

    @Column
    @NotNull
    private String pin;

    @Column
    @NotNull
    private BigDecimal money;

    @NotNull
    @ManyToOne(optional = false)
    private CreditTariff creditTariff;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id")
    private Client owner;

    public Account(BigInteger id) {
        this.id = id;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
        owner.getAccounts().add(this);
    }

    @PrePersist
    public void prePersist() {
        if (money == null) {
            money = BigDecimal.ZERO;
        }
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", pin='" + pin + '\'' +
                ", money=" + money +
                ", ownerId=" + owner.getId() +
                '}';
    }
}
