package com.cravebite.backend_2.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Boolean isAvailable;
}
