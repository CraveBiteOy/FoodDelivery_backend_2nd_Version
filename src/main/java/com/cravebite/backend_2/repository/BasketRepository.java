package com.cravebite.backend_2.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cravebite.backend_2.models.entities.Basket;
import com.cravebite.backend_2.models.entities.Customer;
import com.cravebite.backend_2.models.entities.Restaurant;

public interface BasketRepository extends JpaRepository<Basket, Long> {

    Optional<Basket> findByCustomerAndRestaurant(Customer customer, Restaurant restaurant);
}
