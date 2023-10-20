package com.cravebite.backend_2.models.entities;

import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.Point;

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

@Entity(name = "orders")
@Table()
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

    @Min(value = 0, message = "Total distance must be greater than 0")
    @Column(name = "total_distance")
    private Double totalDistance;


    @Min(value = 0, message = "Delivery fee must be greater than 0")
    @Column(name = "delivery_fee")
    private Double deliveryFee;

    @Min(value = 0, message = "Courier payment amount must be greater than 0")
    @Column(name = "courier_payment_amount")
    private Double courierPaymentAmount;


    @Column(name = "delivery_instructions")
    private String deliveryInstructions;

    @Min(value = 0, message = "pickup time must be greater than 0")
    @Column(name = "pickup_time")
    private Integer pickupTime;

    @Min(value = 0, message = "dropoff time must be greater than 0")
    @Column(name = "dropoff_time")
    private Integer dropoffTime;

    @Min(value = 0, message = "delivery total time must be greater than 0")
    @Column(name = "delivery_total_time")
    private Integer deliveryTotalTime;

    @Column(name = "pickup_point", columnDefinition = "geometry(Point,4326)")
    private Point pickUpPoint;

    @Column(name = "dropoff_point", columnDefinition = "geometry(Point,4326)")
    private Point DropOffPoint;

    @Column(name = "courier_current_point", columnDefinition = "geometry(Point,4326)")
    private Point courierCurrentPoint;


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
