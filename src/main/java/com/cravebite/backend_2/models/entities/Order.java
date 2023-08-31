package com.cravebite.backend_2.models.entities;

import java.util.ArrayList;
import java.util.List;

import com.cravebite.backend_2.models.enums.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @Min(value = 0, message = "Total price must be greater than 0")
    @Column(name = "total_price")
    private Double totalPrice;

    @Min(value = 0, message = "Delivery fee must be greater than 0")
    @Column(name = "delivery_fee")
    private Double deliveryFee;

    @Min(value = 0, message = "Sub total must be greater than 0")
    @Column(name = "sub_total")
    private Double subTotal;

    @Column(name = "delivery_instructions")
    private String deliveryInstructions;

    @Min(value = 0, message = "pickup time must be greater than 0")
    @Column(name = "pickup_time")
    private String pickupTime;

    @Min(value = 0, message = "dropoff time must be greater than 0")
    @Column(name = "dropoff_time")
    private String dropoffTime;

    @Min(value = 0, message = "delivery total time must be greater than 0")
    @Column(name = "delivery_total_time")
    private String deliveryTotalTime;

    @Column(name = "courier_start_latitude")
    private Double courierStartLatitude;

    @Column(name = "courier_start_longitude")
    private Double courierStartLongitude;

    @Column(name = "destination_latitude")
    private Double destinationLatitude;

    @Column(name = "destination_longitude")
    private Double destinationLongitude;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "delivery_zipcode")
    private String deliveryZipcode;

    @Column(name = "delivery_city")
    private String deliveryCity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "courier_id", referencedColumnName = "id")
    private Courier courier;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

}
