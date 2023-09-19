package com.cravebite.backend_2.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {
    private Long id;
    private String status;
    private Double totalPrice;
    private Double deliveryFee;
    private String deliveryInstructions;
    private String pickupTime;
    private String dropoffTime;
    private String deliveryTotalTime;
    private Double courierStartLatitude;
    private Double courierStartLongitude;
    private Double destinationLatitude;
    private Double destinationLongitude;
    private String deliveryAddress;
    private String deliveryZipcode;
    private String deliveryCity;
    private CustomerResponseDTO customer;
    private RestaurantResponseDTO restaurant;
    private CourierResponseDTO courier;
}
