package com.plutus.system.repository;

import com.plutus.system.model.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface ClientRepository extends JpaRepository<Client, BigInteger> {
}
