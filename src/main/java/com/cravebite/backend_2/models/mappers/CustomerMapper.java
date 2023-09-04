package com.cravebite.backend_2.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cravebite.backend_2.models.entities.Customer;
import com.cravebite.backend_2.models.response.CustomerResponseDTO;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(source = "user.id", target = "id")
    @Mapping(source = "locationId", target = "locationId")
    CustomerResponseDTO toCustomerResponseDTO(Customer customer);
}
