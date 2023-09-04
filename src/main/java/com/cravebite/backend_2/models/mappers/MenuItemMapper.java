package com.cravebite.backend_2.models.mappers;

import org.mapstruct.Mapper;

import com.cravebite.backend_2.models.entities.MenuItem;
import com.cravebite.backend_2.models.response.MenuItemResponseDTO;

@Mapper(componentModel = "spring")
public interface MenuItemMapper {

    MenuItemResponseDTO toMenuItemResponseDTO(MenuItem menuItem);
}
