package com.cravebite.backend_2.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cravebite.backend_2.models.entities.Courier;
import com.cravebite.backend_2.models.response.CourierResponseDTO;

@Mapper(componentModel = "spring")
public interface CourierMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "id", target = "courierId")
    @Mapping(source = "locationId", target = "locationId")
    CourierResponseDTO toCourierResponseDTO(Courier courier);
}
