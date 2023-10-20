package com.cravebite.backend_2.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourierResponseDTO {
    private Long userId;
    private Long courierId;
    private Long locationId;
    private String status;
    private Boolean availability;
    private String mode;
    private Double latitude;
    private Double longitude;
    private String firstname;  

}