package com.cravebite.backend_2.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RestaurantRequestDTO {

    @NotBlank(message = "name cannot be blank")
    private String name;

    @NotBlank(message = "address cannot be blank")
    private String address;

    @NotBlank(message = "zipcode cannot be blank")
    private String zipcode;

    @NotBlank(message = "city cannot be blank")
    private String city;

    @NotNull(message = "latitude cannot be null")
    private Double latitude;

    @NotNull(message = "longitude cannot be null")
    private Double longitude;

}