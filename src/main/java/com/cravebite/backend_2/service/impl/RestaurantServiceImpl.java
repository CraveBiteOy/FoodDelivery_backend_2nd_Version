package com.cravebite.backend_2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cravebite.backend_2.models.entities.Restaurant;
import com.cravebite.backend_2.repository.RestaurantRepository;
import com.cravebite.backend_2.service.RestaurantService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    RestaurantRepository restaurantRepository;

    // get restaurant by id
    public Restaurant getRestaurantById(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));
    }

}
