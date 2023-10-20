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
    private Double totalDistance;
    private Double deliveryFee;
    private Double courierPaymentAmount;
    private String deliveryInstructions;
    private String pickupTime;
    private String dropoffTime;
    private String deliveryTotalTime;
    private Double pickUpLatitude;
    private Double pickUpLongitude;
    private Double dropOffLatitude;
    private Double dropOffLongitude;
    private Double courierCurrentLatitude;
    private Double courierCurrentLongitude;
    private String deliveryAddress;
    private String deliveryZipcode;
    private String deliveryCity;
    private CustomerResponseDTO customer;
    private RestaurantResponseDTO restaurant;
    private CourierResponseDTO courier;
}
