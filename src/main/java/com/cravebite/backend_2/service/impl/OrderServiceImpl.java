package com.cravebite.backend_2.service.impl;

import java.util.List;
import java.util.ArrayList;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cravebite.backend_2.exception.CraveBiteGlobalExceptionHandler;
import com.cravebite.backend_2.models.entities.Basket;
import com.cravebite.backend_2.models.entities.BasketItem;
import com.cravebite.backend_2.models.entities.Courier;
import com.cravebite.backend_2.models.entities.Location;
import com.cravebite.backend_2.models.entities.Customer;
import com.cravebite.backend_2.models.entities.Order;
import com.cravebite.backend_2.models.entities.OrderItem;
import com.cravebite.backend_2.models.entities.Restaurant;
import com.cravebite.backend_2.models.entities.RestaurantOwner;
import com.cravebite.backend_2.models.enums.NavigationMode;
import com.cravebite.backend_2.models.enums.OrderStatus;
import com.cravebite.backend_2.models.request.OrderRequestDTO;
import com.cravebite.backend_2.repository.CourierRepository;
import com.cravebite.backend_2.repository.OrderRepository;
import com.cravebite.backend_2.service.BasketService;
import com.cravebite.backend_2.service.CourierService;
import com.cravebite.backend_2.service.CustomerService;
import com.cravebite.backend_2.service.LocationService;
import com.cravebite.backend_2.service.OrderItemService;
import com.cravebite.backend_2.service.OrderService;
import com.cravebite.backend_2.service.RestaurantOwnerService;
import com.cravebite.backend_2.utils.Geocoder;
import java.util.Arrays;

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
    private RestaurantOwnerService restaurantOwnerService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private Geocoder geocoder;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CourierRepository courierRepository;

    @Autowired
    private CourierService courierService;

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
        if (basket.getBasketItems().isEmpty()) {
            throw new CraveBiteGlobalExceptionHandler(HttpStatus.BAD_REQUEST,
                    "The basket is empty. Please add items before placing an order.");
        }
        Restaurant restaurant = basket.getRestaurant();

        /**
         * checking if the authenticated customer owns the basket
         * 
         */
        Long basketId = orderRequest.getBasketId();
        if (!basketService.isBasketOwnedByAuthenticatedCustomer(basketId)) {
            throw new CraveBiteGlobalExceptionHandler(HttpStatus.UNAUTHORIZED, "You don't own this basket!");
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

        System.out.println("hajri " + calculateDistance(restaurantPoint, customerPoint));

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

        // clear basket
        basketService.clearBasket(basket);

        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new CraveBiteGlobalExceptionHandler(HttpStatus.NOT_FOUND, "Order not found!"));
    }

    @Override
    public List<Order> getbyCustomer(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Order> getbyRestaurant(Long restaurantId) {
        return orderRepository.findByRestaurantId(restaurantId);
    }

    // get order by authenticated restaurant owner associated with the order
    public Order getbyAuthenticatedRestaurantOwner(Long orderId) {
        Long restaurantOwnerId = restaurantOwnerService.getRestaurantOwnerFromAuthenticatedUser().getId();
        return orderRepository.findByIdAndRestaurant_RestaurantOwner_Id(orderId, restaurantOwnerId)
                .orElseThrow(() -> new CraveBiteGlobalExceptionHandler(HttpStatus.NOT_FOUND,
                        "Order not found for orderId: " + orderId + ", restaurantOwnerId: " + restaurantOwnerId));

    }

    // overloaded methods
    public Order validateAndRetrieveOrder(Long orderId, RestaurantOwner onwer) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CraveBiteGlobalExceptionHandler(HttpStatus.NOT_FOUND, "Order not found"));

        if (!order.getRestaurant().getRestaurantOwner().getId().equals(onwer.getId())) {
            throw new CraveBiteGlobalExceptionHandler(HttpStatus.UNAUTHORIZED, "Not authorized to accept this order");

        }
        return order;
    }

    public Order validateAndRetrieveOrder(Long orderId, Courier courier) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CraveBiteGlobalExceptionHandler(HttpStatus.NOT_FOUND, "Order not found"));

        if (!order.getCourier().getId().equals(courier.getId())) {
            throw new CraveBiteGlobalExceptionHandler(HttpStatus.UNAUTHORIZED, "Not authorized");

        }
        return order;
    }

    // checking pre-conditions
    public void validateOrderStatus(Order order, OrderStatus... expectedStatuses) {
        if (Arrays.stream(expectedStatuses).noneMatch(status -> status == order.getStatus())) {
            throw new CraveBiteGlobalExceptionHandler(HttpStatus.BAD_REQUEST,
                    "Invalid order status. Expected: " + Arrays.toString(expectedStatuses));
        }
    }

    public Order acceptOrderByOwner(Long orderId) {
        RestaurantOwner owner = restaurantOwnerService.getRestaurantOwnerFromAuthenticatedUser();
        Order order = validateAndRetrieveOrder(orderId, owner);

        validateOrderStatus(order, OrderStatus.ORDERED, OrderStatus.NO_COURIERS_AVAILABLE);

        // Mark the order as preparing
        order.setStatus(OrderStatus.PREPARING);

        // Assign a courier to the order
        assignOrderToCourier(order.getId());

        return orderRepository.save(order);
    }

    public Order markOrderAsReayByRestaurantOwner(Long orderId) {
        RestaurantOwner owner = restaurantOwnerService.getRestaurantOwnerFromAuthenticatedUser();
        Order order = validateAndRetrieveOrder(orderId, owner);
        validateOrderStatus(order, OrderStatus.PREPARING);
        order.setStatus(OrderStatus.READY);
        return orderRepository.save(order);
    }

    public Order rejectOrderByOwner(Long orderId) {
        RestaurantOwner owner = restaurantOwnerService.getRestaurantOwnerFromAuthenticatedUser();
        Order order = validateAndRetrieveOrder(orderId, owner);
        validateOrderStatus(order, OrderStatus.ORDERED);

        // Mark the order as rejected by the restaurant
        order.setStatus(OrderStatus.REJECTED_BY_RESTAURANT);

        return orderRepository.save(order);
    }

    public Order assignOrderToCourier(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CraveBiteGlobalExceptionHandler(HttpStatus.NOT_FOUND, "Order not found"));

        // Assign the nearest courier to the order
        Courier courier = courierService.getNearestCourier(order);

        // If no available couriers, set status to NO_COURIERS_AVAILABLE
        if (courier == null) {
            order.setStatus(OrderStatus.NO_COURIERS_AVAILABLE);
            return orderRepository.save(order);
        }

        // Otherwise, assign the courier and keep status as READY
        order.setCourier(courier);

        // Set courier availability to false
        courier.setAvailability(false);
        courierRepository.save(courier);

        return orderRepository.save(order);
    }

    public Order acceptOrderByCourier(Long orderId) {
        Courier courier = courierService.getCourierFromAuthenticatedUser();
        Order order = validateAndRetrieveOrder(orderId, courier);
        validateOrderStatus(order, OrderStatus.READY);

        // Mark the order as accepted by the courier
        order.setStatus(OrderStatus.ACCEPTED_BY_COURIER);

        return orderRepository.save(order);
    }

    public Order rejectOrderByCourier(Long orderId) {
        Courier courier = courierService.getCourierFromAuthenticatedUser();
        Order order = validateAndRetrieveOrder(orderId, courier);
        validateOrderStatus(order, OrderStatus.READY);

        // Set courier availability back to true
        courier.setAvailability(true);
        courierRepository.save(courier);

        // Assign the order to a new courier
        assignOrderToCourier(order.getId());

        return orderRepository.save(order);
    }

    public Order pickupOrderByCourier(Long orderId) {
        Courier courier = courierService.getCourierFromAuthenticatedUser();
        Order order = validateAndRetrieveOrder(orderId, courier);
        validateOrderStatus(order, OrderStatus.ACCEPTED_BY_COURIER);

        // Check if the courier is near the restaurant
        if (!courierService.isCourierNearLocation(courier, order.getRestaurant().getRestaurantPoint())) {
            throw new CraveBiteGlobalExceptionHandler(HttpStatus.PRECONDITION_FAILED,
                    "Courier is not near the restaurant");
        }

        // Mark the order as picked up
        order.setStatus(OrderStatus.PICKED_UP);

        return orderRepository.save(order);
    }

    public Order dropoffOrderByCourier(Long orderId) {
        Courier courier = courierService.getCourierFromAuthenticatedUser();
        Order order = validateAndRetrieveOrder(orderId, courier);
        validateOrderStatus(order, OrderStatus.PICKED_UP);

        // Check if the courier is near the customer
        if (!courierService.isCourierNearLocation(courier, order.getDeliveryEndPoint())) {
            throw new CraveBiteGlobalExceptionHandler(HttpStatus.PRECONDITION_FAILED,
                    "Courier is not near the customer");
        }

        // Mark the order as delivered
        order.setStatus(OrderStatus.DELIVERED);

        return orderRepository.save(order);
    }

    @Override
    public Order reOrder(Long pastOrderId) {
        // return null;

        Order pastOrder = orderRepository.findById(pastOrderId)
                .orElseThrow(() -> new CraveBiteGlobalExceptionHandler(HttpStatus.NOT_FOUND, "Order not found"));

        // check if customer is authenticated
        Customer customer = customerService.getCustomerFromAuthenticatedUser();
        if (!customer.getId().equals(pastOrder.getCustomer().getId())) {
            throw new CraveBiteGlobalExceptionHandler(HttpStatus.UNAUTHORIZED, "Not authorized to re-order this order");
        }

        // create new order
        Order newOrder = new Order();
        newOrder.setStatus(OrderStatus.ORDERED);
        newOrder.setTotalPrice(pastOrder.getTotalPrice());
        newOrder.setDeliveryFee(pastOrder.getDeliveryFee());
        newOrder.setDeliveryInstructions(pastOrder.getDeliveryInstructions());
        newOrder.setDeliveryTotalTime(pastOrder.getDeliveryTotalTime());
        newOrder.setPickupTime(pastOrder.getPickupTime());
        newOrder.setDropoffTime(pastOrder.getDropoffTime());
        newOrder.setDeliveryStartPoint(pastOrder.getDeliveryStartPoint());
        newOrder.setDeliveryEndPoint(pastOrder.getDeliveryEndPoint());
        newOrder.setCustomer(pastOrder.getCustomer());
        newOrder.setRestaurant(pastOrder.getRestaurant());
        newOrder.setDeliveryAddress(pastOrder.getDeliveryAddress());
        newOrder.setDeliveryZipcode(pastOrder.getDeliveryZipcode());
        newOrder.setDeliveryCity(pastOrder.getDeliveryCity());

        // save new order
        newOrder = orderRepository.save(newOrder);

        List<OrderItem> PastOrderItems = orderItemService.getItemsByOrder(pastOrder);

        // create new orderItems
        for (OrderItem pastOrderItem : PastOrderItems) {
            OrderItem newOrderItem = new OrderItem();
            newOrderItem.setQuantity(pastOrderItem.getQuantity());
            newOrderItem.setMenuItem(pastOrderItem.getMenuItem());
            newOrderItem.setOrder(newOrder);

            orderItemService.saveOrderItem(newOrderItem);
        }

        return newOrder;
    }

}
