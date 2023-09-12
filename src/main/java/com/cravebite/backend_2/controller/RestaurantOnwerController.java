package com.cravebite.backend_2.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cravebite.backend_2.models.entities.RestaurantOwner;
import com.cravebite.backend_2.models.mappers.RestaurantOwnerMapper;
import com.cravebite.backend_2.models.response.RestaurantOwnerResponseDTO;
import com.cravebite.backend_2.service.RestaurantOnwerService;

@RestController
@RequestMapping("/restaurant-owner")
public class RestaurantOnwerController {

    @Autowired
    private RestaurantOnwerService restaurantOnwerService;

    @Autowired
    private RestaurantOwnerMapper restaurantOwnerMapper;

    // get restaurant owner by id
    @GetMapping("id/{restaurantOwnerId}")
    public ResponseEntity<RestaurantOwnerResponseDTO> getRestaurantOwnerById(@PathVariable Long restaurantOwnerId) {
        RestaurantOwner restaurantOwner = restaurantOnwerService.getRestaurantOwnerById(restaurantOwnerId);
        RestaurantOwnerResponseDTO restaurantOwnerDto = restaurantOwnerMapper
                .toRestaurantOwnerResponseDTO(restaurantOwner);

        return new ResponseEntity<>(restaurantOwnerDto, HttpStatus.OK);
    }

    // get all restaurant owners
    @GetMapping("all")
    public ResponseEntity<List<RestaurantOwnerResponseDTO>> getAllRestaurantOwners() {
        List<RestaurantOwner> restaurantOwners = restaurantOnwerService.getAllRestaurantOwners();
        List<RestaurantOwnerResponseDTO> restaurantOwnerDtos = restaurantOwners.stream()
                .map(restaurantOwner -> restaurantOwnerMapper.toRestaurantOwnerResponseDTO(restaurantOwner))
                .collect(Collectors.toList());

        return new ResponseEntity<>(restaurantOwnerDtos, HttpStatus.OK);
    }

    // get customer by authenticated user
    @GetMapping("/authenticated")
    public ResponseEntity<RestaurantOwnerResponseDTO> getRestaurantOwnerByAuthenticatedUser() {
        RestaurantOwner restaurantOwner = restaurantOnwerService.createRestaurantOwnerFromAuthenticatedUser();
        RestaurantOwnerResponseDTO restaurantOwnerDto = restaurantOwnerMapper
                .toRestaurantOwnerResponseDTO(restaurantOwner);

        return new ResponseEntity<>(restaurantOwnerDto, HttpStatus.OK);

    }

}
