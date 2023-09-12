package com.cravebite.backend_2.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cravebite.backend_2.models.entities.Restaurant;
import com.cravebite.backend_2.models.mappers.RestaurantMapper;
import com.cravebite.backend_2.models.request.RestaurantRequestDTO;
import com.cravebite.backend_2.models.response.RestaurantResponseDTO;
import com.cravebite.backend_2.service.RestaurantService;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    RestaurantMapper restaurantMapper;

    @GetMapping("/byId/{restaurantId}")
    public ResponseEntity<RestaurantResponseDTO> getRestaurantById(@PathVariable Long restaurantId) {
        return ResponseEntity
                .ok(restaurantMapper.toRestaurantResponseDTO(restaurantService
                        .getRestaurantById(restaurantId)));
    }

    @GetMapping("byName/{restaurantName}")
    private ResponseEntity<RestaurantResponseDTO> getRestaurantByName(@PathVariable String restaurantName) {
        return ResponseEntity
                .ok(restaurantMapper.toRestaurantResponseDTO(restaurantService
                        .getRestaurantByName(restaurantName)));

    }

    @GetMapping("/all")
    public ResponseEntity<List<RestaurantResponseDTO>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        List<RestaurantResponseDTO> response = restaurants.stream()
                .map(restaurant -> restaurantMapper.toRestaurantResponseDTO(restaurant))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<List<RestaurantResponseDTO>> getAllRestaurantsByRestaurantOwner(
            @PathVariable Long restaurantOwnerId) {
        List<Restaurant> restaurants = restaurantService.getAllRestaurantsByRestaurantOwnerId(restaurantOwnerId);
        List<RestaurantResponseDTO> response = restaurants.stream()
                .map(restaurant -> restaurantMapper.toRestaurantResponseDTO(restaurant))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/authenticatedOwner/hasAnyRestaurant")
    public ResponseEntity<Boolean> ownerHasRestaurant() {
        return ResponseEntity.ok(restaurantService
                .ownerHasRestaurant());
    }

    @PostMapping("/create")
    public ResponseEntity<RestaurantResponseDTO> createNewRestaurant(
            @RequestBody RestaurantRequestDTO restaurantRequestDTO) {
        return ResponseEntity
                .ok(restaurantMapper
                        .toRestaurantResponseDTO(restaurantService
                                .createNewRestaurant(restaurantRequestDTO)));
    }
}
