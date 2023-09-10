package com.cravebite.backend_2.service;

import com.cravebite.backend_2.models.entities.Customer;

public interface CustomerService {

    public Customer getCustomerById(Long customerId);

    public Customer createCustomerFromAuthenticatedUser();

    public Customer getCustomerByUserId(Long userId);

}
