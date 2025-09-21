package com.app.agendai.repositories;

import com.app.agendai.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    List<Reservation> findByDateAndServiceEntityEnterpriseId(LocalDate date, UUID enterpriseId);
    Optional<List<Reservation>> findByClientId(UUID id);
    Optional<List<Reservation>> findByServiceEntityId(UUID id);
}
