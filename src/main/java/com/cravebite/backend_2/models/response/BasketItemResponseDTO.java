package com.cravebite.backend_2.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BasketItemResponseDTO {
    private Long id;
    private int quantity;
    private MenuItemResponseDTO menuItem;
}