package com.cravebite.backend_2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    // get customer by authenticated user
    @GetMapping("/authenticated")
    public ResponseEntity<CustomerResponseDTO> getCustomerByAuthenticatedUser() {
        return new ResponseEntity<>(
                customerMapper.toCustomerResponseDTO(customerService.createCustomerFromAuthenticatedUser()),
                HttpStatus.OK);

    }

}
