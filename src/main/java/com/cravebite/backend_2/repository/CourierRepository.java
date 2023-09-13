package com.cravebite.backend_2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cravebite.backend_2.models.entities.Courier;
import com.cravebite.backend_2.models.enums.CourierStatus;

public interface CourierRepository extends JpaRepository<Courier, Long> {

    Optional<Courier> findByUserId(Long userId);

    List<Courier> findByStatusAndAvailability(CourierStatus status, boolean availability);
}
