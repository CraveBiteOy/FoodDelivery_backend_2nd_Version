package com.cravebite.backend_2.models.mappers;

import org.mapstruct.Mapper;

import com.cravebite.backend_2.models.entities.BasketItem;
import com.cravebite.backend_2.models.response.BasketItemResponseDTO;

@Mapper(componentModel = "spring")
public interface BasketItemMapper {

    BasketItemResponseDTO toBasketItemResponseDTO(BasketItem basketItem);

}
