package com.cravebite.backend_2.repository;

import java.util.List;
import java.util.Optional;

import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cravebite.backend_2.models.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT ST_Distance(CAST(:point1 AS geography), CAST(:point2 AS geography))", nativeQuery = true)
    Double calculateDistance(@Param("point1") Point point1, @Param("point2") Point point2);

    List<Order> findByCustomerId(Long customerId);

    List<Order> findByRestaurantId(Long restaurantId);

    Optional<Order> findByIdAndRestaurant_RestaurantOwner_Id(Long orderId, Long restaurantOwnerId);

}
