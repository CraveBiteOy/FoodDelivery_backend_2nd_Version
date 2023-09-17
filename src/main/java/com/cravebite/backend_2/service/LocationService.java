package com.cravebite.backend_2.service;

import org.locationtech.jts.geom.Point;

import com.cravebite.backend_2.models.entities.Location;

public interface LocationService {

    Location getLocationById(Long locationId);

    Location updateLocation(Long locationId, Point newLocation);

}
