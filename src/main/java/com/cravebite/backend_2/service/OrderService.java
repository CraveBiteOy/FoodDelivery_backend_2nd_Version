package com.cravebite.backend_2.service;

import java.util.List;

import com.cravebite.backend_2.models.entities.Order;
// import com.cravebite.backend_2.models.enums.OrderStatus;
import com.cravebite.backend_2.models.request.OrderRequestDTO;

public interface OrderService {

    Order createAnOrder(OrderRequestDTO orderRequest);

    Order getOrderById(Long id);

    // List<Order> getbyCourier(Long courierId);

    List<Order> getbyCustomer(Long customerId);

    List<Order> getbyRestaurant(Long restaurantId);

    public Order getbyAuthenticatedRestaurantOwner(Long OrderId);

    // List<Order> getbyCourierAndOrderStatus(Long driverId, OrderStatus status);

    // List<Order> getbyRestaurantAndOrderStatus(Long restaurantId, OrderStatus
    // status);

    Order acceptOrderByOwner(Long orderId);

    Order markOrderAsReayByRestaurantOwner(Long orderId);

    Order rejectOrderByOwner(Long orderId);

    Order acceptOrderByCourier(Long orderId);

    Order rejectOrderByCourier(Long orderId);

    Order pickupOrderByCourier(Long orderId);

    Order dropoffOrderByCourier(Long orderId);

}
