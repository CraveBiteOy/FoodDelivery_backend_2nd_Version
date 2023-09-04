package com.cravebite.backend_2.models.mappers;

import org.mapstruct.Mapper;

import com.cravebite.backend_2.models.entities.Basket;
import com.cravebite.backend_2.models.response.BasketResponseDTO;

@Mapper(componentModel = "spring")
public interface BasketMapper {

    BasketResponseDTO toBasketResponseDTO(Basket basket);
}
