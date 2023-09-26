package com.cravebite.backend_2.service;

import java.util.List;

import com.cravebite.backend_2.models.entities.Order;
import com.cravebite.backend_2.models.entities.OrderItem;

public interface OrderItemService {

    List<OrderItem> getItemsByOrder(Order order);

    void saveOrderItem(OrderItem newOrderItem);

}
