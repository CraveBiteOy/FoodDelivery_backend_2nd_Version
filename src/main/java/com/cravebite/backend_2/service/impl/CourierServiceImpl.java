package com.cravebite.backend_2.service.impl;

import java.util.List;
import java.util.Optional;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cravebite.backend_2.exception.CraveBiteGlobalExceptionHandler;
import com.cravebite.backend_2.models.entities.Courier;
import com.cravebite.backend_2.models.entities.Location;
import com.cravebite.backend_2.models.entities.Order;
import com.cravebite.backend_2.models.entities.User;
import com.cravebite.backend_2.models.enums.CourierStatus;
import com.cravebite.backend_2.models.enums.NavigationMode;
import com.cravebite.backend_2.repository.CourierRepository;
import com.cravebite.backend_2.service.CourierService;
import com.cravebite.backend_2.service.LocationService;
import com.cravebite.backend_2.service.UserService;

@Service
public class CourierServiceImpl implements CourierService {

    @Autowired
    private CourierRepository courierRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private LocationService locationService;

    @Override
    public Courier createCourierFromAuthenticatedUser(Long locationId) {
        User authenticatedUser = userService.getAuthenticatedUser();
        Long userId = authenticatedUser.getId();

        Optional<Courier> existingCourier = courierRepository.findByUserId(userId);
        if (existingCourier.isPresent()) {
            return existingCourier.get();
        } else {
            Courier newCourier = new Courier();
            newCourier.setUser(authenticatedUser);
            newCourier.setLocationId(locationId);
            newCourier.setAvailability(true);
            return courierRepository.save(newCourier);
        }

    }

    public Courier getCourierFromAuthenticatedUser() {
        User authenticatedUser = userService.getAuthenticatedUser();
        Long userId = authenticatedUser.getId();

        return courierRepository.findByUserId(userId)
                .orElseThrow(() -> new CraveBiteGlobalExceptionHandler(HttpStatus.NOT_FOUND, "Courier not found"));
    }

    @Override
    public Courier getCourierById(Long courierId) {
        return courierRepository.findById(courierId)
                .orElseThrow(() -> new CraveBiteGlobalExceptionHandler(HttpStatus.NOT_FOUND, "Courier not found"));
    }

    @Override
    public List<Courier> getOnlineAndAvailableCouriers() {
        return courierRepository.findByStatusAndAvailability(CourierStatus.ONLINE, true);

    }

    @Override
    public Courier updateStatusForAuthenticatedCourier(CourierStatus status) {
        Courier authCourier = getCourierFromAuthenticatedUser();
        authCourier.setStatus(status);
        return courierRepository.save(authCourier);

    }

    @Override
    public Courier updateNavigationModeForAuthenticatedCourier(NavigationMode mode) {
        Courier authCourier = getCourierFromAuthenticatedUser();
        authCourier.setMode(mode);
        return courierRepository.save(authCourier);
    }

    @Override
    public Courier updateCourierAvailability(Boolean availability) {
        Courier authCourier = getCourierFromAuthenticatedUser();
        authCourier.setAvailability(availability);
        return courierRepository.save(authCourier);
    }

    // update location
    public Courier updateCourierLocation(Long courierId, Point newLocation) {
        Courier authCourier = getCourierFromAuthenticatedUser();
        Location updatedLocation = locationService.updateLocation(authCourier.getLocationId(), newLocation);
        authCourier.setLocationId(updatedLocation.getId());
        return courierRepository.save(authCourier);
    }

    public Courier getNearestCourier(Order order) {
        // Get the restaurant's location
        Point restaurantLocation = order.getRestaurant().getRestaurantPoint();

        List<Courier> nearbyCouriers = courierRepository.findNearbyCouriers(restaurantLocation, 20000.0);

        Courier nearestCourier = null;
        Double shortestDistance = Double.MAX_VALUE;

        for (Courier courier : nearbyCouriers) {
            // Skip couriers who are currently assigned an order
            if (!courier.isAvailability()) {
                continue;
            }

            // Get the courier's location
            Location courierLocation = locationService.getLocationById(courier.getLocationId());
            Point courierPoint = courierLocation.getGeom();

            // Calculate the distance between courier and restaurant using PostGIS
            Double distance = courierRepository.calculateDistance(restaurantLocation, courierPoint);

            // If this courier is closer, update nearestCourier and shortestDistance
            if (distance < shortestDistance) {
                nearestCourier = courier;
                shortestDistance = distance;
            }
        }

        return nearestCourier;
    }

    public boolean isCourierNearLocation(Courier courier, Point location) {
        // Get the courier's location
        Location courierLocation = locationService.getLocationById(courier.getLocationId());
        Point courierPoint = courierLocation.getGeom();

        // Calculate the distance to the location using PostGIS ST_Distance
        Double distance = courierRepository.calculateDistance(courierPoint, location);

        // Check if the distance is less than the threshold
        return distance <= 100;
    }

}
