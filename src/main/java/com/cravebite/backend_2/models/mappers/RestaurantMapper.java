package com.cravebite.backend_2.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cravebite.backend_2.models.entities.Restaurant;
import com.cravebite.backend_2.models.response.RestaurantResponseDTO;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    @Mapping(target = "latitude", expression = "java(restaurant.getRestaurantPoint().getY())")
    @Mapping(target = "longitude", expression = "java(restaurant.getRestaurantPoint().getX())")
    RestaurantResponseDTO toRestaurantResponseDTO(Restaurant restaurant);
}
