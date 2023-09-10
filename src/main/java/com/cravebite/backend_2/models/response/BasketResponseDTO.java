package com.cravebite.backend_2.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasketResponseDTO {
    private Long id;
    private CustomerResponseDTO customer;
    private RestaurantResponseDTO restaurant;
    private Double totalPrice;
}
