package com.cravebite.backend_2.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cravebite.backend_2.models.entities.Order;
import com.cravebite.backend_2.models.response.OrderResponseDTO;

@Mapper(componentModel = "spring", uses = { CourierMapper.class, CustomerMapper.class, RestaurantMapper.class })
public interface OrderMapper {

    @Mapping(target = "dropOffLatitude", expression = "java(order.getDropOffPoint().getY())")
    @Mapping(target = "dropOffLongitude", expression = "java(order.getDropOffPoint().getX())")
    @Mapping(target = "pickUpLatitude", expression = "java(order.getPickUpPoint().getY())")
    @Mapping(target = "pickUpLongitude", expression = "java(order.getPickUpPoint().getX())")
    @Mapping(target = "courierCurrentLatitude", expression = "java(order.getCourierCurrentPoint() != null ? order.getCourierCurrentPoint().getY() : null)")
    @Mapping(target = "courierCurrentLongitude", expression = "java(order.getCourierCurrentPoint() != null ? order.getCourierCurrentPoint().getX() : null)")
 
    OrderResponseDTO toOrderResponseDTO(Order order);
}
