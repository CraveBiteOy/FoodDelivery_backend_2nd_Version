package com.cravebite.backend_2.repository;

import java.util.List;
import java.util.Optional;

import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cravebite.backend_2.models.entities.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Optional<Restaurant> findByName(String restaurantName);

    List<Restaurant> findByRestaurantOwnerId(Long restaurantOwnerId);

    long countByRestaurantOwnerId(Long restaurantOwnerId);

    @Query(value = "SELECT * FROM restaurant WHERE ST_Distance(CAST(restaurant_point AS geography), CAST(:point AS geography)) <= :distance", nativeQuery = true)
    List<Restaurant> findNearbyRestaurants(@Param("point") Point point, @Param("distance") double distance);

}
