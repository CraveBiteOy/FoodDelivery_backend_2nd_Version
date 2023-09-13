package com.cravebite.backend_2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cravebite.backend_2.models.entities.SpatialEntity;

public interface SpatialEntityRepository extends JpaRepository<SpatialEntity, Long> {
}
