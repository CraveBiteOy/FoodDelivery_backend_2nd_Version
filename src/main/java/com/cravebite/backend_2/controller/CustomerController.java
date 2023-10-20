package com.cravebite.backend_2.controller;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cravebite.backend_2.models.entities.Customer;
import com.cravebite.backend_2.models.mappers.CustomerMapper;
import com.cravebite.backend_2.models.response.CustomerResponseDTO;
import com.cravebite.backend_2.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerMapper customerMapper;

    // get customer by id
    @GetMapping("/id/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return new ResponseEntity<>(customerService.getCustomerById(id), HttpStatus.OK);
    }

    // update customer location
    @PutMapping("/lat/{latitude}/long/{longitude}")
    public ResponseEntity<CustomerResponseDTO> updateCourierLocation(
            @PathVariable Double latitude,
            @PathVariable Double longitude) {
        Point newLocation = new GeometryFactory().createPoint(new Coordinate(longitude, latitude));
        Customer customer = customerService.updateCustomerLocation(newLocation);

        return ResponseEntity
                .ok(customerMapper
                        .toCustomerResponseDTO(customer));
    }

}
