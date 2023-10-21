package com.cravebite.backend_2.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cravebite.backend_2.models.mappers.OrderItemMapper;
import com.cravebite.backend_2.models.response.OrderItemResponseDTO;
import com.cravebite.backend_2.service.OrderItemService;

@RestController
@RequestMapping("/api/orderItems")
public class MenuItemController {

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderItemMapper orderItemMapper;

    // get menu items by order id
    @GetMapping("/byOrderId/{orderId}")
    public ResponseEntity<List<OrderItemResponseDTO>> getMenuItemsByOrderId(@PathVariable Long orderId) {
        return ResponseEntity
                .ok(orderItemService
                        .getOrderItemsByOrderId(orderId)
                        .stream()
                        .map(orderItemMapper::toOrderItemResponseDTO)
                        .collect(Collectors.toList()));
    }

    
}
