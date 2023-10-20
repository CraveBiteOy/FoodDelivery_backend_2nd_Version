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
import com.cravebite.backend_2.service.UserService;

@Service
public class CourierServiceImpl implements CourierService {

    @Autowired
    private CourierRepository courierRepository;

    @Autowired
    private UserService userService;

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
            newCourier.setAvailability(true);
            newCourier.setStatus(CourierStatus.OFFLINE);
            newCourier.setFirstLogin(true);
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

    public Courier getNearestCourier(Order order) {
        System.out.println("Inside getNearestCourier method");

        // Get the restaurant's location
        Point restaurantLocation = order.getRestaurant().getRestaurantPoint();

        List<Courier> nearbyCouriers = courierRepository.findAvailableNearbyCouriers(restaurantLocation, 20000.0);

        // If no available couriers, throw an exception
        if (nearbyCouriers.isEmpty()) {
            throw new CraveBiteGlobalExceptionHandler(HttpStatus.NOT_FOUND, "No available couriers nearby");
        }

        Courier nearestCourier = null;
        Double shortestDistance = Double.MAX_VALUE;

        for (Courier courier : nearbyCouriers) {
            System.out.println("Checking courier: " + courier);

            // Skip couriers who are currently assigned an order
            if (!courier.isAvailability()) {
                continue;
            }

            // Get the courier's location
            Location courierLocation = courier.getLocation();

            Point courierPoint = courierLocation.getGeom();

            // Calculate the distance between courier and restaurant using PostGIS
            Double distance = courierRepository.calculateDistance(restaurantLocation, courierPoint);

            // If this courier is closer, update nearestCourier and shortestDistance
            if (distance < shortestDistance) {
                nearestCourier = courier;
                shortestDistance = distance;
            }
        }

        System.out.println("Nearest courier: " + nearestCourier);

        return nearestCourier;
    }

    public boolean isCourierNearLocation(Courier courier, Point location) {
        // Get the courier's location
        Location courierLocation = courier.getLocation();

        Point courierPoint = courierLocation.getGeom();

        // Calculate the distance to the location using PostGIS ST_Distance
        Double distance = courierRepository.calculateDistance(courierPoint, location);

        // Check if the distance is less than the threshold (THRESHOLD = 100 meters)
        return distance <= 100;
    }

    /*
     * check if courier is logged in for the first time
     * Why? Because we need to prompt the user to select a navigation mode
     * 
     */
    public boolean isCourierLoggedInForTheFirstTime() {
        Courier authCourier = getCourierFromAuthenticatedUser();
        boolean isFirstLogin = authCourier.isFirstLogin();
        if (isFirstLogin) {
            authCourier.setFirstLogin(false);
            courierRepository.save(authCourier);
        }
        return isFirstLogin;
    }

}