package com.cravebite.backend_2.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cravebite.backend_2.models.entities.Order;
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

        // create order
        @PostMapping("/create")
        public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
                return ResponseEntity
                                .ok(orderMapper
                                                .toOrderResponseDTO(orderService
                                                                .createAnOrder(orderRequestDTO)));
        }

        // get order by id
        @GetMapping("/{orderId}")
        public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long orderId) {
                return ResponseEntity
                                .ok(orderMapper
                                                .toOrderResponseDTO(orderService
                                                                .getOrderById(orderId)));
        }

        // get order by id and auth onwer
        @GetMapping("/onwerAndOrderId/{orderId}")
        public ResponseEntity<OrderResponseDTO> getbyAuthenticatedRestaurantOwner(@PathVariable Long orderId) {
                return ResponseEntity
                                .ok(orderMapper
                                                .toOrderResponseDTO(orderService
                                                                .getbyAuthenticatedRestaurantOwner(orderId)));
        }

        // get order by customer
        @GetMapping("/customer/{customerId}")
        public ResponseEntity<List<OrderResponseDTO>> getbyCustomer(@PathVariable Long customerId) {
                List<Order> orders = orderService.getbyCustomer(customerId);
                List<OrderResponseDTO> response = orders.stream()
                                .map(order -> orderMapper.toOrderResponseDTO(order)).collect(Collectors.toList());
                return ResponseEntity.ok(response);
        }

        // get order by restaurant
        @GetMapping("/restaurant/{restaurantId}")
        public ResponseEntity<List<OrderResponseDTO>> getbyRestaurant(@PathVariable Long restaurantId) {
                List<Order> orders = orderService.getbyRestaurant(restaurantId);
                List<OrderResponseDTO> response = orders.stream()
                                .map(order -> orderMapper.toOrderResponseDTO(order)).collect(Collectors.toList());
                return ResponseEntity.ok(response);

        }

        // accept order by owner
        @GetMapping("/owner/accept/{orderId}")
        public ResponseEntity<OrderResponseDTO> acceptOrderByOwner(@PathVariable Long orderId) {
                return ResponseEntity
                                .ok(orderMapper
                                                .toOrderResponseDTO(orderService
                                                                .acceptOrderByOwner(orderId)));

        }

        // reject order by owner
        @GetMapping("/owner/reject/{orderId}")
        public ResponseEntity<OrderResponseDTO> rejectOrderByOwner(@PathVariable Long orderId) {
                return ResponseEntity
                                .ok(orderMapper
                                                .toOrderResponseDTO(orderService
                                                                .rejectOrderByOwner(orderId)));
        }

        // mark order as reay by owner
        @GetMapping("/owner/ready/{orderId}")
        public ResponseEntity<OrderResponseDTO> markOrderAsReayByRestaurantOwner(@PathVariable Long orderId) {
                return ResponseEntity
                                .ok(orderMapper
                                                .toOrderResponseDTO(orderService
                                                                .markOrderAsReayByRestaurantOwner(orderId)));
        }

        // accept order by courier
        @GetMapping("/courier/accept/{orderId}")
        public ResponseEntity<OrderResponseDTO> acceptOrderByCourier(@PathVariable Long orderId) {
                return ResponseEntity
                                .ok(orderMapper
                                                .toOrderResponseDTO(orderService
                                                                .acceptOrderByCourier(orderId)));
        }

        // reject order by courier
        @GetMapping("/courier/reject/{orderId}")
        public ResponseEntity<OrderResponseDTO> rejectOrderByCourier(@PathVariable Long orderId) {
                return ResponseEntity
                                .ok(orderMapper
                                                .toOrderResponseDTO(orderService
                                                                .rejectOrderByCourier(orderId)));
        }

        // pickup order by courier
        @GetMapping("/courier/pickup/{orderId}")
        public ResponseEntity<OrderResponseDTO> pickupOrderByCourier(@PathVariable Long orderId) {
                return ResponseEntity
                                .ok(orderMapper
                                                .toOrderResponseDTO(orderService
                                                                .pickupOrderByCourier(orderId)));
        }

        // dropoff order by courier
        @GetMapping("/courier/dropoff/{orderId}")
        public ResponseEntity<OrderResponseDTO> dropoffOrderByCourier(@PathVariable Long orderId) {
                return ResponseEntity
                                .ok(orderMapper
                                                .toOrderResponseDTO(orderService
                                                                .dropoffOrderByCourier(orderId)));

        }
}
