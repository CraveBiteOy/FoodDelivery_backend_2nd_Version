package com.cravebite.backend_2.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cravebite.backend_2.models.entities.Customer;
import com.cravebite.backend_2.models.response.CustomerResponseDTO;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "id", target = "customerId")
    @Mapping(source = "location.id", target = "locationId")
    @Mapping(source = "user.firstname", target = "firstname")
    CustomerResponseDTO toCustomerResponseDTO(Customer customer);
}
