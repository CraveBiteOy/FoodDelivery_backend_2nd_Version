package com.cravebite.backend_2.models.request;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BasketRequestDTO {

    @NotNull(message = "customerId cannot be null")
    private Long customerId;

    @NotNull(message = "restaurantId cannot be null")
    private Long restaurantId;

    @NotEmpty(message = "Menu Item IDs cannot be empty")
    private List<Long> menuItemIds;

}
