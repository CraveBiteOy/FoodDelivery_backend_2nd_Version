package com.cravebite.backend_2.controller;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cravebite.backend_2.models.entities.Courier;
import com.cravebite.backend_2.models.mappers.CourierMapper;
import com.cravebite.backend_2.models.response.CourierResponseDTO;
import com.cravebite.backend_2.service.CourierOrderService;

@RestController
@RequestMapping("/api/couriers")
public class CourierOrderController {


    @Autowired
        private CourierOrderService courierOrderService;

        @Autowired
        private CourierMapper courierMapper;

        // update courier location
        @PutMapping("/courier/authenticated/lat/{latitude}/long/{longitude}")
        public ResponseEntity<CourierResponseDTO> updateCourierLocation(
                        @PathVariable double latitude, @PathVariable double longitude) {
                Point newLocation = new GeometryFactory().createPoint(new Coordinate(longitude, latitude));
                Courier courier = courierOrderService.updateCourierAndOrderLocation(newLocation);

                return ResponseEntity
                                .ok(courierMapper
                                                .toCourierResponseDTO(courier));
        }
    
}
