package com.cravebite.backend_2.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cravebite.backend_2.models.entities.Order;
import com.cravebite.backend_2.models.entities.OrderItem;
import com.cravebite.backend_2.repository.OrderItemRepository;
import com.cravebite.backend_2.service.OrderItemService;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    public List<OrderItem> getItemsByOrder(Order order) {

        return orderItemRepository.findByOrder(order);

    }

    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {

        return orderItemRepository.findByOrderId(orderId);

    }

    @Override
    public void saveOrderItem(OrderItem newOrderItem) {

        orderItemRepository.save(newOrderItem);
    }

}
