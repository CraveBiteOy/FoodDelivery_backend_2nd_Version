package com.cravebite.backend_2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cravebite.backend_2.models.entities.Courier;

public interface CourierRepository extends JpaRepository<Courier, Long> {
}
