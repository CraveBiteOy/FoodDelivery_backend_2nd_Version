package com.cravebite.backend_2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cravebite.backend_2.models.mappers.OrderMapper;
import com.cravebite.backend_2.models.request.OrderRequestDTO;
import com.cravebite.backend_2.models.response.OrderResponseDTO;
import com.cravebite.backend_2.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMapper orderMapper;

    @RequestMapping("/create")
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        return ResponseEntity
                .ok(orderMapper
                        .toOrderResponseDTO(orderService
                                .createAnOrder(orderRequestDTO)));
    }

}
