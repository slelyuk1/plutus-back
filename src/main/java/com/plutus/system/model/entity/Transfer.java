package com.plutus.system.model.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity(name = "transfers")
@NoArgsConstructor
public class Transfer {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Long fromId;

    @Column
    private Long toId;

    @Column
    @NotNull
    private String transferStatus;

    @Column
    @NotNull
    BigDecimal amount;

    @Column
    @NotNull
    LocalDateTime createdWhen;

    @Column
    @NotNull
    String description;


}
