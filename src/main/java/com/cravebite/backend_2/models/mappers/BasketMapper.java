package com.cravebite.backend_2.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cravebite.backend_2.models.entities.Basket;
import com.cravebite.backend_2.models.response.BasketResponseDTO;

@Mapper(componentModel = "spring", uses = { CourierMapper.class, CustomerMapper.class })
public interface BasketMapper {

    @Mapping(target = "restaurant.latitude", expression = "java(restaurant.getRestaurantPoint().getY())")
    @Mapping(target = "restaurant.longitude", expression = "java(restaurant.getRestaurantPoint().getX())")
    BasketResponseDTO toBasketResponseDTO(Basket basket);
}
