package com.cravebite.backend_2.models.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MenuItemRequestDTO {

    @NotNull(message = "restaurantId cannot be null")
    private Long restaurantId;

    @NotBlank(message = "name cannot be blank")
    private String name;

    @NotBlank(message = "description cannot be blank")
    private String description;

    @Min(value = 0, message = "Price must be greater than 0")
    private Double price;

    @NotNull(message = "isAvailable cannot be null")
    private Boolean isAvailable;
}