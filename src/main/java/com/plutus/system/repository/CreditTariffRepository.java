package com.plutus.system.repository;

import com.plutus.system.model.entity.CreditTariff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface CreditTariffRepository extends JpaRepository<CreditTariff, BigInteger> {
}
