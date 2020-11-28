package com.plutus.system.repository;

import com.plutus.system.model.entity.Account;
import com.plutus.system.model.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Collection;

@Repository
public interface AccountRepository extends JpaRepository<Account, BigInteger> {
}
