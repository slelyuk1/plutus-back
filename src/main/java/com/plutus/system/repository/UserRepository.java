package com.plutus.system.repository;

import com.plutus.system.model.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Client, Long> {
}
