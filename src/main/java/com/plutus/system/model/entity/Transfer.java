package com.plutus.system.model.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@Entity(name = "transfer")
@NoArgsConstructor
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;

    @OneToOne
    private Account creator;

    @OneToOne
    private Account receiver;

    @NotNull
    @Column
    BigDecimal amount;

    @NotNull
    @Column
    LocalDateTime createdWhen;

    @Column
    String description;

    @PrePersist
    public void prePersist() {
        if (creator == null && receiver == null) {
            //todo throw exception https://stackoverflow.com/questions/37878992/custom-jpa-validation-in-spring-boot
        }
        if (createdWhen == null) {
            createdWhen = LocalDateTime.now();
        }
    }

    @PreUpdate
    public void preUpdate() {
        if (creator == null && receiver == null) {
            //todo throw exception
        }
    }
}
