package com.cravebite.backend_2.service;

import com.cravebite.backend_2.models.entities.Courier;
import org.locationtech.jts.geom.Point;

public interface CourierOrderService {
    Courier updateCourierAndOrderLocation(Point newLocation);
}