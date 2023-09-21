package com.cravebite.backend_2.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cravebite.backend_2.models.entities.RestaurantOwner;

public interface RestaurantOwnerRepository extends JpaRepository<RestaurantOwner, Long> {

    Optional<RestaurantOwner> findByUserId(Long userId);
}