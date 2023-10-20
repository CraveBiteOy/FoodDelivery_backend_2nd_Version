package com.cravebite.backend_2.service.impl;

import com.cravebite.backend_2.exception.CraveBiteGlobalExceptionHandler;
import com.cravebite.backend_2.models.entities.Courier;
import com.cravebite.backend_2.models.entities.Order;
import com.cravebite.backend_2.service.CourierOrderService;
import com.cravebite.backend_2.service.CourierService;
import com.cravebite.backend_2.service.LocationService;
import com.cravebite.backend_2.service.OrderService;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CourierOrderServiceImpl implements CourierOrderService {

    @Autowired
    private CourierService courierService;

    @Autowired
    private OrderService orderService;
    
    @Autowired
    private  LocationService locationService;

    @Override
    public Courier updateCourierAndOrderLocation(Point newLocation) {
        // Fetching the authenticated courier
        Courier authCourier = courierService.getCourierFromAuthenticatedUser();
        Long currentLocationId = authCourier.getLocation().getId();
            if (currentLocationId == null) {
                throw new CraveBiteGlobalExceptionHandler(HttpStatus.BAD_REQUEST,
                         "Courier does not have an associated location ID.");
                }

            locationService.updateLocation(currentLocationId, newLocation);

        // Fetching courier's associated active order
        Order currentOrder = orderService.getbyCourierId(authCourier.getId());
        if (currentOrder != null) {
            currentOrder.setCourierCurrentPoint(newLocation);
            orderService.save(currentOrder);
        }

        return authCourier;
    }
}
