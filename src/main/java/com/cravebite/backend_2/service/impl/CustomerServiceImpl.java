package com.cravebite.backend_2.service.impl;

import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cravebite.backend_2.exception.CraveBiteGlobalExceptionHandler;
import com.cravebite.backend_2.models.entities.Customer;
import com.cravebite.backend_2.models.entities.User;
import com.cravebite.backend_2.repository.CustomerRepository;
import com.cravebite.backend_2.service.CustomerService;
import com.cravebite.backend_2.service.LocationService;
import com.cravebite.backend_2.service.UserService;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LocationService locationService;

    // get customer by id
    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new CraveBiteGlobalExceptionHandler(HttpStatus.NOT_FOUND, "Customer not found"));
    }

    // get customer by user id
    public Customer getCustomerByUserId(Long userId) {
        return customerRepository.findByUserId(userId)
                .orElseThrow(() -> new CraveBiteGlobalExceptionHandler(HttpStatus.NOT_FOUND, "Customer not found"));
    }

    public Customer getCustomerFromAuthenticatedUser() {
        User authenticatedUser = userService.getAuthenticatedUser();
        Long userId = authenticatedUser.getId();

        return customerRepository.findByUserId(userId)
                .orElseThrow(() -> new CraveBiteGlobalExceptionHandler(HttpStatus.NOT_FOUND, "Customer not found"));
    }

    public Customer updateCustomerLocation(Point newLocation) {
        Customer authCustomer = getCustomerFromAuthenticatedUser();
        Long currentLocationId = authCustomer.getLocation().getId();
        if (currentLocationId == null) {
            throw new CraveBiteGlobalExceptionHandler(HttpStatus.BAD_REQUEST,
                    "Customer does not have an associated location ID.");
        }

        locationService.updateLocation(currentLocationId, newLocation);

        return authCustomer;
    }

}
