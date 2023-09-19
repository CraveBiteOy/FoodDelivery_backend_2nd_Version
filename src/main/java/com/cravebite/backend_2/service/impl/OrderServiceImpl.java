package com.cravebite.backend_2.service.impl;

import java.util.List;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cravebite.backend_2.models.entities.Basket;
import com.cravebite.backend_2.models.entities.BasketItem;
import com.cravebite.backend_2.models.entities.Location;
import com.cravebite.backend_2.models.entities.Customer;
import com.cravebite.backend_2.models.entities.Order;
import com.cravebite.backend_2.models.entities.OrderItem;
import com.cravebite.backend_2.models.entities.Restaurant;
import com.cravebite.backend_2.models.enums.NavigationMode;
import com.cravebite.backend_2.models.enums.OrderStatus;
import com.cravebite.backend_2.models.request.OrderRequestDTO;
import com.cravebite.backend_2.repository.OrderRepository;
import com.cravebite.backend_2.service.BasketService;
import com.cravebite.backend_2.service.CustomerService;
import com.cravebite.backend_2.service.LocationService;
import com.cravebite.backend_2.service.OrderService;
import com.cravebite.backend_2.utils.Geocoder;

@Service
public class OrderServiceImpl implements OrderService {

    // Constants
    private static final double CAR_SPEED = 40; // Average speed in km/h
    private static final double BICYCLE_SPEED = 15; // Average speed in km/h
    // courier payment calculation
    private static final double BASE_PAY = 3.0; // Base pay for each delivery
    private static final double DISTANCE_BONUS_PER_KM = 1.0; // Bonus per kilometer
    private static final double CAR_BONUS = 1.0; // Extra bonus for car deliveries

    @Autowired
    private BasketService basketService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private Geocoder geocoder;

    @Autowired
    private OrderRepository orderRepository;

    // calculate delivery fee
    private double calculateDeliveryFee(Point restaurantPoint, Point customerPoint, NavigationMode mode) {
        double distanceInKilometers = calculateDistance(restaurantPoint, customerPoint);

        double deliveryFee;

        if (distanceInKilometers <= 0.5) {
            deliveryFee = 1.99;
        } else if (distanceInKilometers <= 1) {
            deliveryFee = 2.99;
        } else if (distanceInKilometers <= 1.5) {
            deliveryFee = 3.99;
        } else if (distanceInKilometers <= 2) {
            deliveryFee = 4.99;
        } else {
            deliveryFee = 5.99;
        }

        return Math.round(deliveryFee * 100.0) / 100.0;
    }

    // Calculate courier's payment for an order
    public double calculateCourierPayment(Order order, NavigationMode mode) {
        double distanceInKilometers = calculateDistance(order.getDeliveryStartPoint(), order.getDeliveryEndPoint());

        double payment = BASE_PAY + (DISTANCE_BONUS_PER_KM * distanceInKilometers);

        // Add extra bonus for car deliveries
        if (mode == NavigationMode.CAR) {
            payment += CAR_BONUS;
        }

        return Math.round(payment * 100.0) / 100.0;
    }

    // calculate pickup time
    private int calculatePickupTime(int cookingTime) {
        int bufferTime = 10;
        return cookingTime + bufferTime;
    }

    // calculate drop-off time
    private int calculateDropoffTime(double distance, NavigationMode mode) {
        double averageSpeed = mode == NavigationMode.BICYCLE ? CAR_SPEED : BICYCLE_SPEED;
        return (int) Math.ceil(distance / averageSpeed * 60);
    }

    // calculate total time
    private int calculateTotalDeliveryTime(int pickupTime, int dropoffTime) {
        return pickupTime + dropoffTime;
    }

    // calculate total price
    private double calculateTotalPrice(double basketTotal, double deliveryFee) {
        double totalPrice = basketTotal + deliveryFee;
        return Math.round(totalPrice * 100.0) / 100.0;
    }

    // calculate distance between two points
    private double calculateDistance(Point point1, Point point2) {
        double distanceInMeters = orderRepository.calculateDistance(point1, point2);
        return distanceInMeters / 1000;
    }

    @Override
    public Order createAnOrder(OrderRequestDTO orderRequest) {
        /*
         * GET BASKET
         */
        Basket basket = basketService.getBasketById(orderRequest.getBasketId());
        Restaurant restaurant = basket.getRestaurant();

        /**
         * checking if the authenticated customer owns the basket
         * 
         */
        Long basketId = orderRequest.getBasketId();
        if (!basketService.isBasketOwnedByAuthenticatedCustomer(basketId)) {
            throw new RuntimeException("You don't own this basket!");
        }

        /* Get customer's location */
        Customer customer = customerService.getCustomerFromAuthenticatedUser();
        Location customerLocation = locationService.getLocationById(customer.getLocationId());
        Point customerPoint = customerLocation.getGeom();
        Point restaurantPoint = restaurant.getRestaurantPoint();

        double deliveryFee = calculateDeliveryFee(restaurantPoint, customerPoint, NavigationMode.CAR);

        int pickupTime = calculatePickupTime(restaurant.getCookingTime());

        int dropoffTime = calculateDropoffTime(calculateDistance(restaurantPoint, customerPoint),
                NavigationMode.CAR);

        int totalDeliveryTime = calculateTotalDeliveryTime(pickupTime, dropoffTime);

        double totalPrice = calculateTotalPrice(basket.getTotalPrice(), deliveryFee);

        /*
         * GEOCODING
         */
        orderRequest = geocoder.geoEncode(orderRequest);

        /*
         * CREATE ORDER
         */
        Order order = new Order();
        order.setStatus(OrderStatus.ORDERED);
        order.setTotalPrice(totalPrice);
        order.setDeliveryFee(deliveryFee);
        order.setDeliveryInstructions(orderRequest.getDeliveryInstructions());
        order.setDeliveryTotalTime(totalDeliveryTime);
        order.setPickupTime(pickupTime);
        order.setDropoffTime(dropoffTime);
        order.setDeliveryStartPoint(restaurantPoint);
        order.setDeliveryEndPoint(customerPoint);
        order.setCustomer(customer);
        order.setRestaurant(restaurant);
        order.setDeliveryAddress(orderRequest.getDeliveryAddress());
        order.setDeliveryZipcode(orderRequest.getDeliveryZipcode());
        order.setDeliveryCity(orderRequest.getDeliveryCity());

        /*
         * Converting BasketItems to OrderItems
         */
        for (BasketItem basketItem : basket.getBasketItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(basketItem.getQuantity());
            orderItem.setMenuItem(basketItem.getMenuItem());
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);
        }

        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found!"));
    }

    @Override
    public List<Order> getbyCustomer(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Order> getbyRestaurant(Long restaurantId) {
        return orderRepository.findByRestaurantId(restaurantId);
    }

}
