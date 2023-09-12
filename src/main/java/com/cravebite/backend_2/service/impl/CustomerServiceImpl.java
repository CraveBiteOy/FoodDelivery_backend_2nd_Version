package com.cravebite.backend_2.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cravebite.backend_2.models.entities.Customer;
import com.cravebite.backend_2.models.entities.User;
import com.cravebite.backend_2.repository.CustomerRepository;
import com.cravebite.backend_2.service.CustomerService;
import com.cravebite.backend_2.service.UserService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerRepository customerRepository;

    // get customer by id
    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    }

    // get customer by user id
    public Customer getCustomerByUserId(Long userId) {
        return customerRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    }

    // create customer from authenticated user
    public Customer createCustomerFromAuthenticatedUser() {
        User authenticatedUser = userService.getAuthenticatedUser();
        Long userId = authenticatedUser.getId();

        Optional<Customer> existingCustomer = customerRepository.findById(userId);
        if (existingCustomer.isPresent()) {
            return existingCustomer.get();
        } else {
            Customer newCustomer = new Customer();
            newCustomer.setUser(authenticatedUser);

            return customerRepository.save(newCustomer);
        }

    }

}
