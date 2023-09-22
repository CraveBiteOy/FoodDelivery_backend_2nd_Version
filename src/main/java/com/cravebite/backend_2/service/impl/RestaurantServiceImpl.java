package com.cravebite.backend_2.service.impl;

import java.util.List;

import java.util.Optional;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cravebite.backend_2.exception.CraveBiteGlobalExceptionHandler;
import com.cravebite.backend_2.models.entities.Customer;
import com.cravebite.backend_2.models.entities.Location;
import com.cravebite.backend_2.models.entities.Restaurant;
import com.cravebite.backend_2.models.entities.RestaurantOwner;
import com.cravebite.backend_2.models.request.RestaurantRequestDTO;
import com.cravebite.backend_2.repository.RestaurantRepository;
import com.cravebite.backend_2.service.CustomerService;
import com.cravebite.backend_2.service.LocationService;
import com.cravebite.backend_2.service.RestaurantOwnerService;
import com.cravebite.backend_2.service.RestaurantService;
import com.cravebite.backend_2.utils.Geocoder;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    LocationService locationService;

    @Autowired
    private Geocoder geocoder;

    @Autowired
    private RestaurantOwnerService restaurantOwnerService;

    // get restaurant by id
    public Restaurant getRestaurantById(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new CraveBiteGlobalExceptionHandler(HttpStatus.NOT_FOUND, "Restaurant not found"));
    }

    // get restaurant by its name
    public Restaurant getRestaurantByName(String restaurantName) {
        return restaurantRepository.findByName(restaurantName)
                .orElseThrow(() -> new CraveBiteGlobalExceptionHandler(HttpStatus.NOT_FOUND, "Restaurant not found"));
    }

    // get all restaurants
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    // get all restaurants by restaurant owner id
    public List<Restaurant> getAllRestaurantsByRestaurantOwnerId(Long restaurantOwnerId) {
        return restaurantRepository.findByRestaurantOwnerId(restaurantOwnerId);
    }

    // check if owner has any restaurants
    public boolean ownerHasRestaurant() {
        RestaurantOwner restaurantOwner = restaurantOwnerService.createRestaurantOwnerFromAuthenticatedUser();
        Long restaurantOwnerId = restaurantOwner.getId();

        // checking if owner has any restaurants
        long restaurantCount = restaurantRepository.countByRestaurantOwnerId(restaurantOwnerId);
        return restaurantCount > 0;

    }

    // create a new restaurant by authenticated restaurant owner
    public Restaurant createNewRestaurant(RestaurantRequestDTO restaurantRequestDTO) {
        RestaurantOwner restaurantOwner = restaurantOwnerService.createRestaurantOwnerFromAuthenticatedUser();

        if (restaurantOwner == null) {
            throw new CraveBiteGlobalExceptionHandler(HttpStatus.UNAUTHORIZED,
                    "Only restaurant owners can create restaurants");
        }

        // Check if a restaurant with the same name, address, and city already exists
        Optional<Restaurant> existingRestaurant = restaurantRepository.findByNameAndAddressAndCity(
                restaurantRequestDTO.getName(),
                restaurantRequestDTO.getAddress(),
                restaurantRequestDTO.getCity());

        if (existingRestaurant.isPresent()) {
            throw new CraveBiteGlobalExceptionHandler(HttpStatus.CONFLICT,
                    "A restaurant with this name, address, and city already exists");
        }

        // Geocoding
        restaurantRequestDTO = geocoder.geoEncode(restaurantRequestDTO);

        Restaurant newRestaurant = new Restaurant();
        newRestaurant.setName(restaurantRequestDTO.getName());
        newRestaurant.setAddress(restaurantRequestDTO.getAddress());
        newRestaurant.setZipcode(restaurantRequestDTO.getZipcode());
        newRestaurant.setCity(restaurantRequestDTO.getCity());
        newRestaurant.setCookingTime(restaurantRequestDTO.getCookingTime());
        Point location = new GeometryFactory()
                .createPoint(new Coordinate(restaurantRequestDTO.getLongitude(), restaurantRequestDTO.getLatitude()));
        newRestaurant.setRestaurantPoint(location);

        newRestaurant.setRestaurantOwner(restaurantOwner);

        return restaurantRepository.save(newRestaurant);
    }

    // recommend restaurants
    public List<Restaurant> recommendRestaurants() {
        Customer customer = customerService.getCustomerFromAuthenticatedUser();
        Location customerLocation = locationService.getLocationById(customer.getLocationId());
        return restaurantRepository.findNearbyRestaurants(customerLocation.getGeom(), 20000);
    }

    @Override
    public Restaurant updateRestaurant(Long id, String name, Integer cookingTime) {

        RestaurantOwner authenticatedOwner = restaurantOwnerService.getRestaurantOwnerFromAuthenticatedUser();
        if (authenticatedOwner == null) {
            throw new CraveBiteGlobalExceptionHandler(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }
        Restaurant existingRestaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new CraveBiteGlobalExceptionHandler(HttpStatus.NOT_FOUND, "Restaurant not found"));

        if (!existingRestaurant.getRestaurantOwner().getId().equals(authenticatedOwner.getId())) {
            throw new CraveBiteGlobalExceptionHandler(HttpStatus.FORBIDDEN, "Not authorized to update this restaurant");
        }

        if (!existingRestaurant.getName().equals(name) && restaurantRepository.findByName(name) != null) {
            throw new CraveBiteGlobalExceptionHandler(HttpStatus.CONFLICT, "Restaurant name already exists");
        }

        existingRestaurant.setName(name);
        existingRestaurant.setCookingTime(cookingTime);
        return restaurantRepository.save(existingRestaurant);
    }

}
