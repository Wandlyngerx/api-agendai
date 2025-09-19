package com.app.agendai.repositories;

import com.app.agendai.entities.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EnterpriseRepository extends JpaRepository<Enterprise, UUID> {

    Optional<Enterprise> findByName(String name);
    Optional<Enterprise> findByUser(UserDetails user);
}
