package com.cravebite.backend_2.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cravebite.backend_2.models.entities.Courier;
import com.cravebite.backend_2.models.response.CourierResponseDTO;
@Mapper(componentModel = "spring")
public interface CourierMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "id", target = "courierId")
    @Mapping(source = "location.id", target = "locationId")
    @Mapping(source = "location.geom.x", target = "longitude")
    @Mapping(source = "location.geom.y", target = "latitude")
    @Mapping(source = "user.firstname", target = "firstname")
    CourierResponseDTO toCourierResponseDTO(Courier courier);
}
