package com.cravebite.backend_2.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cravebite.backend_2.models.entities.Courier;
import com.cravebite.backend_2.models.enums.CourierStatus;
import com.cravebite.backend_2.models.enums.NavigationMode;
import com.cravebite.backend_2.models.mappers.CourierMapper;
import com.cravebite.backend_2.models.response.CourierResponseDTO;
import com.cravebite.backend_2.service.CourierService;

@RestController
@RequestMapping("/courier")
public class CourierController {

    @Autowired
    private CourierService courierService;

    @Autowired
    private CourierMapper courierMapper;

    // create courier from authenticated user or get authenticated courier
    @GetMapping("/authenticate")
    public ResponseEntity<CourierResponseDTO> getCourierFromAuthenticatedUser() {
        return ResponseEntity
                .ok(courierMapper.toCourierResponseDTO(courierService
                        .createCourierFromAuthenticatedUser()));
    }

    // get courier by id
    @GetMapping("/id/{id}")
    public ResponseEntity<CourierResponseDTO> getCourierById(@PathVariable Long id) {
        return ResponseEntity
                .ok(courierMapper
                        .toCourierResponseDTO(courierService.getCourierById(id)));
    }

    // get online and available couriers
    @GetMapping("/online-available")
    public ResponseEntity<List<CourierResponseDTO>> getOnlineAndAvailableCouriers() {
        List<Courier> couriers = courierService.getOnlineAndAvailableCouriers();
        return ResponseEntity
                .ok(couriers
                        .stream()
                        .map(courierMapper::toCourierResponseDTO)
                        .collect(Collectors.toList()));
    }

    // upate courier status
    @PutMapping("/status/{status}")
    public ResponseEntity<CourierResponseDTO> updateCourierStatus(@PathVariable CourierStatus status) {
        return ResponseEntity
                .ok(courierMapper
                        .toCourierResponseDTO(courierService.updateStatusForAuthenticatedCourier(status)));
    }

    // update courier navigation mode
    @PutMapping("/navigation-mode/{mode}")
    public ResponseEntity<CourierResponseDTO> updateCourierNavigationMode(@PathVariable NavigationMode mode) {
        return ResponseEntity
                .ok(courierMapper
                        .toCourierResponseDTO(courierService.updateNavigationModeForAuthenticatedCourier(mode)));
    }

    // update courier availability
    @PutMapping("/availability/{availability}")
    public ResponseEntity<CourierResponseDTO> updateCourierAvailability(@PathVariable Boolean availability) {
        return ResponseEntity
                .ok(courierMapper
                        .toCourierResponseDTO(courierService.updateCourierAvailability(availability)));
    }

}
