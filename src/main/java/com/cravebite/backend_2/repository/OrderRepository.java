package com.cravebite.backend_2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cravebite.backend_2.models.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
