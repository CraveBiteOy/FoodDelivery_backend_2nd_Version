package com.cravebite.backend_2.models.mappers;

import org.mapstruct.Mapper;

import com.cravebite.backend_2.models.entities.Restaurant;
import com.cravebite.backend_2.models.response.RestaurantResponseDTO;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    RestaurantResponseDTO toRestaurantResponseDTO(Restaurant restaurant);
}
