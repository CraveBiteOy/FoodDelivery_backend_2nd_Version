package com.cravebite.backend_2.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
@RequestMapping("/api/orders")
public class OrderController {

        @Autowired
        private OrderService orderService;

        @Autowired
        private OrderMapper orderMapper;

        @Autowired
        SimpMessagingTemplate simpMessagingTemplate;

        // create order
        @PostMapping("/order/create")
        public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
                return ResponseEntity
                                .ok(orderMapper
                                                .toOrderResponseDTO(orderService
                                                                .createAnOrder(orderRequestDTO)));
        }

        // re order with past order id
        @PostMapping("/order/re-order/id/{orderId}")
        public ResponseEntity<OrderResponseDTO> reOrder(@PathVariable Long orderId) {
                return ResponseEntity
                                .ok(orderMapper
                                                .toOrderResponseDTO(orderService
                                                                .reOrder(orderId)));
        }

        // get order by id
        @GetMapping("/order/byId/{orderId}")
        public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long orderId) {
                return ResponseEntity
                                .ok(orderMapper
                                                .toOrderResponseDTO(orderService
                                                                .getOrderById(orderId)));
        }

        // get order by id and auth onwer
        @GetMapping("/order/onwerAndOrderId/{orderId}")
        public ResponseEntity<OrderResponseDTO> getbyAuthenticatedRestaurantOwner(@PathVariable Long orderId) {
                return ResponseEntity
                                .ok(orderMapper
                                                .toOrderResponseDTO(orderService
                                                                .getbyAuthenticatedRestaurantOwner(orderId)));
        }

        // get order by customer
        @GetMapping("/order/byCustomerId/{customerId}")
        public ResponseEntity<List<OrderResponseDTO>> getbyCustomer(@PathVariable Long customerId) {
                List<Order> orders = orderService.getbyCustomer(customerId);
                List<OrderResponseDTO> response = orders.stream()
                                .map(order -> orderMapper.toOrderResponseDTO(order)).collect(Collectors.toList());
                return ResponseEntity.ok(response);
        }

        // get order by restaurant
        @GetMapping("/order/byRestaurantId/{restaurantId}")
        public ResponseEntity<List<OrderResponseDTO>> getbyRestaurant(@PathVariable Long restaurantId) {
                List<Order> orders = orderService.getbyRestaurant(restaurantId);
                List<OrderResponseDTO> response = orders.stream()
                                .map(order -> orderMapper.toOrderResponseDTO(order)).collect(Collectors.toList());
                return ResponseEntity.ok(response);

        }

        // accept order by owner
        @GetMapping("/order/id/{orderId}/ownerAccepts")
        public ResponseEntity<OrderResponseDTO> acceptOrderByOwner(@PathVariable Long orderId) {
                OrderResponseDTO orderResponseDTO = orderMapper
                                .toOrderResponseDTO(orderService.acceptOrderByOwner(orderId));
                /*
                 * Notify courier so they get a notification prompting them to accept the new order or reject it
                 */
                simpMessagingTemplate.convertAndSend("/topic/orders/courier/" + orderResponseDTO.getCourier().getCourierId(), orderResponseDTO);

                return ResponseEntity.ok(orderResponseDTO);

        }

        // reject order by owner
        @GetMapping("/order/id/{orderId}/ownerRejects")
        public ResponseEntity<OrderResponseDTO> rejectOrderByOwner(@PathVariable Long orderId) {
                return ResponseEntity
                                .ok(orderMapper
                                                .toOrderResponseDTO(orderService
                                                                .rejectOrderByOwner(orderId)));
        }

        // mark order as reay by owner
        @GetMapping("/order/id/{orderId}/ownerMarksReady")
        public ResponseEntity<OrderResponseDTO> markOrderAsReayByRestaurantOwner(@PathVariable Long orderId) {
                OrderResponseDTO orderResponseDTO = orderMapper
                                .toOrderResponseDTO(orderService.markOrderAsReayByRestaurantOwner(orderId));

                /*
                * Notify courier so they know the order is ready for pickup
                */
                simpMessagingTemplate.convertAndSend("/topic/orders/courier/" + orderResponseDTO.getCourier().getCourierId(), orderResponseDTO);

                return ResponseEntity.ok(orderResponseDTO);
        }

        // accept order by courier
        @GetMapping("/order/id/{orderId}/courierAccepts")
        public ResponseEntity<OrderResponseDTO> acceptOrderByCourier(@PathVariable Long orderId) {
                OrderResponseDTO orderResponseDTO = orderMapper
                                .toOrderResponseDTO(orderService.acceptOrderByCourier(orderId));
                
                /*
                * Notify courier so they get the updated order
                */
                simpMessagingTemplate.convertAndSend("/topic/orders/courier/" + orderResponseDTO.getCourier().getCourierId(), orderResponseDTO);

                return ResponseEntity.ok(orderResponseDTO);
        }

        // reject order by courier
        @GetMapping("/order/id/{orderId}/courierRejects")
        public ResponseEntity<OrderResponseDTO> rejectOrderByCourier(@PathVariable Long orderId) {
                return ResponseEntity
                                .ok(orderMapper
                                                .toOrderResponseDTO(orderService
                                                                .rejectOrderByCourier(orderId)));
        }

        // pickup order by courier
        @GetMapping("/order/id/{orderId}/courierPicksup")
        public ResponseEntity<OrderResponseDTO> pickupOrderByCourier(@PathVariable Long orderId) {
                OrderResponseDTO orderResponseDTO = orderMapper
                                .toOrderResponseDTO(orderService.pickupOrderByCourier(orderId));

                /*
                * Notify courier so they get the updated order
                */
                simpMessagingTemplate.convertAndSend("/topic/orders/courier/" + orderResponseDTO.getCourier().getCourierId(), orderResponseDTO);

                return ResponseEntity.ok(orderResponseDTO);
        }

        // dropoff order by courier
        @GetMapping("/order/id/{orderId}/courierDropsoff")
        public ResponseEntity<OrderResponseDTO> dropoffOrderByCourier(@PathVariable Long orderId) {
                OrderResponseDTO orderResponseDTO = orderMapper
                                .toOrderResponseDTO(orderService.dropoffOrderByCourier(orderId));

                /*
                * Notify courier so they get the updated order
                */
                simpMessagingTemplate.convertAndSend("/topic/orders/courier/" + orderResponseDTO.getCourier().getCourierId(), orderResponseDTO);

                return ResponseEntity.ok(orderResponseDTO);

        }
}
