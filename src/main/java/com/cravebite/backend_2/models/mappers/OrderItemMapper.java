package com.cravebite.backend_2.models.mappers;

import org.mapstruct.Mapper;

import com.cravebite.backend_2.models.entities.OrderItem;
import com.cravebite.backend_2.models.response.OrderItemResponseDTO;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    OrderItemResponseDTO toOrderItemResponseDTO(OrderItem orderItem);
}
