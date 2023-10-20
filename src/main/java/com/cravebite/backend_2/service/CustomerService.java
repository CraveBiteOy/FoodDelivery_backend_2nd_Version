package com.cravebite.backend_2.service;

import org.locationtech.jts.geom.Point;

import com.cravebite.backend_2.models.entities.Customer;

public interface CustomerService {

    Customer getCustomerById(Long customerId);

    Customer getCustomerFromAuthenticatedUser();

    Customer getCustomerByUserId(Long userId);

    Customer updateCustomerLocation(Point newLocation);

}
