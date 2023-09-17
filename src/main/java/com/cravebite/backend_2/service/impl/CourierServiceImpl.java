package com.cravebite.backend_2.service.impl;

import java.util.List;
import java.util.Optional;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cravebite.backend_2.models.entities.Courier;
import com.cravebite.backend_2.models.entities.Location;
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
            return courierRepository.save(newCourier);
        }

    }

    public Courier getCourierFromAuthenticatedUser() {
        User authenticatedUser = userService.getAuthenticatedUser();
        Long userId = authenticatedUser.getId();

        return courierRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Courier not found"));
    }

    @Override
    public Courier getCourierById(Long courierId) {
        return courierRepository.findById(courierId)
                .orElseThrow(() -> new RuntimeException("Courier not found"));
    }

    @Override
    public List<Courier> getOnlineAndAvailableCouriers() {
        return courierRepository.findByStatusAndAvailability(CourierStatus.ONLINE, true);

    }

    @Override
    public Courier updateStatusForAuthenticatedCourier(CourierStatus status) {
        // Courier authCourier = createCourierFromAuthenticatedUser();
        Courier authCourier = getCourierFromAuthenticatedUser();
        authCourier.setStatus(status);
        return courierRepository.save(authCourier);

    }

    @Override
    public Courier updateNavigationModeForAuthenticatedCourier(NavigationMode mode) {
        // Courier authCourier = createCourierFromAuthenticatedUser();
        Courier authCourier = getCourierFromAuthenticatedUser();
        authCourier.setMode(mode);
        return courierRepository.save(authCourier);
    }

    @Override
    public Courier updateCourierAvailability(Boolean availability) {
        // Courier authCourier = createCourierFromAuthenticatedUser();
        Courier authCourier = getCourierFromAuthenticatedUser();
        authCourier.setAvailability(availability);
        return courierRepository.save(authCourier);
    }

    // update location
    public Courier updateCourierLocation(Long courierId, Point newLocation) {
        // Courier authCourier = createCourierFromAuthenticatedUser();
        Courier authCourier = getCourierFromAuthenticatedUser();
        Location updatedLocation = locationService.updateLocation(authCourier.getLocationId(), newLocation);
        authCourier.setLocationId(updatedLocation.getId());
        return courierRepository.save(authCourier);
    }
}
