package com.cravebite.backend_2.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantResponseDTO {
    private Long id;
    private String name;
    private String address;
    private String zipcode;
    private Integer cookingTime;
    private String city;
    private Double latitude;
    private Double longitude;
}
