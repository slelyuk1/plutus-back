package com.plutus.system.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@Entity(name = "credit_tariff")
public class CreditTariff {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;

    @NotEmpty
    @Column
    private String name;

    @NotNull
    @Range(min = 0, max = 100)
    @Column
    private Integer percent;

    @NotNull
    @Min(0)
    @Column(name = "\"limit\"")
    private BigDecimal limit;

    public CreditTariff(BigInteger id) {
        this.id = id;
    }
}
