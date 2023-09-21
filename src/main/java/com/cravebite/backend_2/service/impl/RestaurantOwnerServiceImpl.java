package com.cravebite.backend_2.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cravebite.backend_2.exception.CraveBiteGlobalExceptionHandler;
import com.cravebite.backend_2.models.entities.RestaurantOwner;
import com.cravebite.backend_2.models.entities.User;
import com.cravebite.backend_2.repository.RestaurantOwnerRepository;
import com.cravebite.backend_2.service.RestaurantOwnerService;
import com.cravebite.backend_2.service.UserService;

@Service
public class RestaurantOwnerServiceImpl implements RestaurantOwnerService {

    @Autowired
    private RestaurantOwnerRepository restaurantOwnerRepository;

    @Autowired
    private UserService userService;

    // get restaurant owner by id
    @Override
    public RestaurantOwner getRestaurantOwnerById(Long restaurantOwnerId) {
        return restaurantOwnerRepository.findById(restaurantOwnerId)
                .orElseThrow(() -> new CraveBiteGlobalExceptionHandler(HttpStatus.NOT_FOUND,
                        "Restaurant Owner not found"));

    }

    // get all restaurant owners
    @Override
    public List<RestaurantOwner> getAllRestaurantOwners() {
        return restaurantOwnerRepository.findAll();
    }

    // create restaurant onwer from the authenticated user
    public RestaurantOwner createRestaurantOwnerFromAuthenticatedUser() {
        User authenticatedUser = userService.getAuthenticatedUser();
        Long userId = authenticatedUser.getId();

        Optional<RestaurantOwner> existingRestaurantOwner = restaurantOwnerRepository.findByUserId(userId);
        if (existingRestaurantOwner.isPresent()) {
            return existingRestaurantOwner.get();
        } else {
            RestaurantOwner newRestaurantOwner = new RestaurantOwner();
            newRestaurantOwner.setUser(authenticatedUser);

            return restaurantOwnerRepository.save(newRestaurantOwner);
        }

    }

    public RestaurantOwner getRestaurantOwnerFromAuthenticatedUser() {
        User authenticatedUser = userService.getAuthenticatedUser();
        Long userId = authenticatedUser.getId();

        return restaurantOwnerRepository.findByUserId(userId)
                .orElseThrow(
                        () -> new CraveBiteGlobalExceptionHandler(HttpStatus.NOT_FOUND, "Restaurant Owner not found"));
    }

}
