package com.cravebite.backend_2.service.impl;

import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cravebite.backend_2.exception.CraveBiteGlobalExceptionHandler;
import com.cravebite.backend_2.models.entities.Location;
import com.cravebite.backend_2.repository.LocationRepository;
import com.cravebite.backend_2.service.LocationService;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;

    // get location by id
    public Location getLocationById(Long locationId) {
        return locationRepository.findById(locationId)
                .orElseThrow(() -> new CraveBiteGlobalExceptionHandler(HttpStatus.NOT_FOUND, "Location not found"));
    }

    public Location updateLocation(Long locationId, Point newLocation) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new CraveBiteGlobalExceptionHandler(HttpStatus.NOT_FOUND, "Location not found"));
        location.setGeom(newLocation);
        return locationRepository.save(location);
    }
}
