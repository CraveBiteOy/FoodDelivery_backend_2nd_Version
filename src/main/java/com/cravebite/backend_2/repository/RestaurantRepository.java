package com.cravebite.backend_2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cravebite.backend_2.models.entities.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Optional<Restaurant> findByName(String restaurantName);

    List<Restaurant> findByRestaurantOwnerId(Long restaurantOwnerId);

    long countByRestaurantOwnerId(Long restaurantOwnerId);

}
