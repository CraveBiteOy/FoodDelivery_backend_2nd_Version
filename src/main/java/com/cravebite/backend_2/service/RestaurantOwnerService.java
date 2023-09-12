package com.cravebite.backend_2.service;

import java.util.List;

import com.cravebite.backend_2.models.entities.RestaurantOwner;

public interface RestaurantOwnerService {

    public RestaurantOwner getRestaurantOwnerById(Long restaurantOwnerId);

    public List<RestaurantOwner> getAllRestaurantOwners();

    public RestaurantOwner createRestaurantOwnerFromAuthenticatedUser();

}
