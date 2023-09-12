package com.cravebite.backend_2.service;

import java.util.List;

import com.cravebite.backend_2.models.entities.Restaurant;
import com.cravebite.backend_2.models.request.RestaurantRequestDTO;

public interface RestaurantService {

    public Restaurant getRestaurantById(Long restaurantId);

    public Restaurant getRestaurantByName(String restaurantName);

    public List<Restaurant> getAllRestaurants();

    Restaurant getRestaurantByIdAndAuthenticatedCustomer(Long restaurantId, Long customerId);

    public List<Restaurant> getAllRestaurantsByRestaurantOwnerId(Long restaurantOwnerId);

    boolean ownerHasRestaurant();

    public Restaurant createNewRestaurant(RestaurantRequestDTO restaurantRequestDTO);
}
