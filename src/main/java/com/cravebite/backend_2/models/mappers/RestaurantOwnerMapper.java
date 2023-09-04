package com.cravebite.backend_2.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cravebite.backend_2.models.entities.RestaurantOwner;
import com.cravebite.backend_2.models.response.RestaurantOwnerResponseDTO;

@Mapper(componentModel = "spring")
public interface RestaurantOwnerMapper {

    @Mapping(source = "user.id", target = "id")
    @Mapping(source = "locationId", target = "locationId")
    RestaurantOwnerResponseDTO toRestaurantOwnerResponseDTO(RestaurantOwner restaurantOwner);
}
