package com.cravebite.backend_2.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderRequestDTO {

    @NotNull(message = "userId cannot be null")
    private Long basketId;

    @NotBlank(message = "deliveryAddress cannot be blank")
    private String deliveryAddress;

    @NotBlank(message = "deliveryZipcode cannot be blank")
    private String deliveryZipcode;

    @NotBlank(message = "deliveryCity cannot be blank")
    private String deliveryCity;

    @NotBlank(message = "deliveryCountry cannot be blank")
    private String deliveryInstructions;

    @NotNull(message = "destinationLatitude cannot be null")
    private Double destinationLatitude;

    @NotNull(message = "destinationLongitude cannot be null")
    private Double destinationLongitude;

}
