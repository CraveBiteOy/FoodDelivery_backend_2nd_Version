package com.cravebite.backend_2.models.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BasketItemRequestDTO {

    @NotNull(message = "basketId cannot be null")
    private Long basketId;

    @NotNull(message = "menuItemId cannot be null")
    private Long menuItemId;

    @Min(value = 1, message = "Quantity must be greater than 0")
    private int quantity;

}
