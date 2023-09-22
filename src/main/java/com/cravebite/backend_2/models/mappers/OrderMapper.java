package com.cravebite.backend_2.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cravebite.backend_2.models.entities.Order;
import com.cravebite.backend_2.models.response.OrderResponseDTO;

@Mapper(componentModel = "spring", uses = { CourierMapper.class, CustomerMapper.class, RestaurantMapper.class })
public interface OrderMapper {

    @Mapping(target = "destinationLatitude", expression = "java(order.getDeliveryEndPoint().getY())")
    @Mapping(target = "destinationLongitude", expression = "java(order.getDeliveryEndPoint().getX())")
    @Mapping(target = "courierStartLatitude", expression = "java(order.getDeliveryStartPoint().getY())")
    @Mapping(target = "courierStartLongitude", expression = "java(order.getDeliveryStartPoint().getX())")
    OrderResponseDTO toOrderResponseDTO(Order order);
}
