package com.cravebite.backend_2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cravebite.backend_2.models.entities.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
