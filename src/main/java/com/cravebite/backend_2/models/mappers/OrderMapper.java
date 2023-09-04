package com.cravebite.backend_2.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cravebite.backend_2.models.entities.Order;
import com.cravebite.backend_2.models.response.OrderResponseDTO;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "courierStartLatitude", target = "courierStartLatitude")
    @Mapping(source = "courierStartLongitude", target = "courierStartLongitude")
    @Mapping(source = "destinationLatitude", target = "destinationLatitude")
    @Mapping(source = "destinationLongitude", target = "destinationLongitude")
    OrderResponseDTO toOrderResponseDTO(Order order);
}
