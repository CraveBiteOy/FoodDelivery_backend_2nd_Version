package com.cravebite.backend_2.service;

import java.util.List;

import org.locationtech.jts.geom.Point;

import com.cravebite.backend_2.models.entities.Courier;
import com.cravebite.backend_2.models.enums.CourierStatus;
import com.cravebite.backend_2.models.enums.NavigationMode;

public interface CourierService {

    Courier createCourierFromAuthenticatedUser(Long locationId);

    Courier getCourierFromAuthenticatedUser();

    Courier getCourierById(Long courierId);

    List<Courier> getOnlineAndAvailableCouriers();

    Courier updateStatusForAuthenticatedCourier(CourierStatus status);

    Courier updateNavigationModeForAuthenticatedCourier(NavigationMode mode);

    Courier updateCourierAvailability(Boolean availability);

    Courier updateCourierLocation(Long courierId, Point newLocation);

}
