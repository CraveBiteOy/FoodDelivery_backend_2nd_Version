package com.cravebite.backend_2.service;

import java.util.List;

import com.cravebite.backend_2.models.entities.Restaurant;
import com.cravebite.backend_2.models.request.RestaurantRequestDTO;

public interface RestaurantService {

    Restaurant getRestaurantById(Long restaurantId);

    Restaurant getRestaurantByName(String restaurantName);

    List<Restaurant> getAllRestaurants();

    List<Restaurant> getAllRestaurantsByRestaurantOwnerId(Long restaurantOwnerId);

    boolean ownerHasRestaurant();

    Restaurant createNewRestaurant(RestaurantRequestDTO restaurantRequestDTO);

    List<Restaurant> recommendRestaurants();

    Restaurant updateRestaurant(Long id, String name, Integer cookingTime);

}
