package com.cravebite.backend_2.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cravebite.backend_2.models.entities.Basket;
import com.cravebite.backend_2.models.entities.BasketItem;
import com.cravebite.backend_2.models.entities.Customer;
import com.cravebite.backend_2.models.entities.MenuItem;
import com.cravebite.backend_2.models.entities.Restaurant;
import com.cravebite.backend_2.models.entities.User;
import com.cravebite.backend_2.repository.BasketItemRepository;
import com.cravebite.backend_2.repository.BasketRepository;
import com.cravebite.backend_2.repository.MenuItemRepository;
import com.cravebite.backend_2.service.BasketItemService;
import com.cravebite.backend_2.service.BasketService;
import com.cravebite.backend_2.service.CustomerService;
import com.cravebite.backend_2.service.MenuItemService;
import com.cravebite.backend_2.service.RestaurantService;
import com.cravebite.backend_2.service.UserService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BasketServiceImpl implements BasketService {

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private BasketItemRepository basketItemRepository;

    @Autowired
    MenuItemRepository menuItemRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private BasketItemService basketItemService;

    @Autowired
    private MenuItemService menuItemService;

    // get basket by id
    public Basket getBasketById(Long basketId) {
        return basketRepository.findById(basketId)
                .orElseThrow(() -> new EntityNotFoundException("Basket not found"));
    }

    // check if basket exists
    public boolean existsById(Long basketId) {
        return basketRepository.existsById(basketId);
    }

    // create basket from customer id and restaurant id
    public Basket createBasketFromCustomer(Long customerId, Long restaurantId) {
        Customer customer = customerService.getCustomerById(customerId);
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        Optional<Basket> basket = getBasket(customer, restaurant);
        if (basket.isPresent()) {
            return basket.get();

        } else {
            Basket newBasket = new Basket();
            newBasket.setCustomer(customer);
            newBasket.setRestaurant(restaurant);
            basketRepository.save(newBasket);
            return newBasket;

        }
    }

    // create basket from authenticated user
    public Basket createBasketFromAuthenticatedUser(Long restaurantId) {
        User authenticatedUser = userService.getAuthenticatedUser();
        System.out.println("authenticated user id: " + authenticatedUser.getId());
        Customer customer = customerService.getCustomerByUserId(authenticatedUser.getId());
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        Optional<Basket> basket = getBasket(customer, restaurant);
        if (basket.isPresent()) {
            return basket.get();

        } else {
            Basket newBasket = new Basket();
            newBasket.setCustomer(customer);
            newBasket.setRestaurant(restaurant);
            basketRepository.save(newBasket);
            return newBasket;

        }
    }

    // helper method
    private Optional<Basket> getBasket(Customer customer, Restaurant restaurant) {
        return basketRepository.findByCustomerAndRestaurant(customer, restaurant);
    }

    /**
     * add menu item to basket; if basket doesn't exist, create new one and if menu
     * item already exists, update quantity
     **/
    public BasketItem addMenuItemToBasket(Long basketId, Long menuItemId, int quantity) {
        if (!basketRepository.existsById(basketId) || !menuItemRepository.existsById(menuItemId)) {
            throw new EntityNotFoundException("Basket or MenuItem not found");
        }

        Basket basket = getBasketById(basketId);
        MenuItem menuItem = menuItemService.getMenuItemById(menuItemId);

        BasketItem basketItem = null;

        try {
            // Try to find existing basket item
            basketItem = basketItemService.getBasketItemByBasketIdAndMenuItemId(basketId, menuItemId);
            basketItem.setQuantity(basketItem.getQuantity() + quantity);
        } catch (EntityNotFoundException e) {
            // If not found, create new basket item
            basketItem = new BasketItem();
            basketItem.setBasket(basket);
            basketItem.setMenuItem(menuItem);
            basketItem.setQuantity(quantity);
        }

        basketItemService.save(basketItem);
        updateTotalPrice(basket);

        return basketItem;
    }

    // update total price of the basket
    private void updateTotalPrice(Basket basket) {
        List<BasketItem> basketItems = basketItemService.getBasketItemsByBasketId(basket.getId());
        double totalPrice = 0;
        totalPrice = basketItems.stream()
                .mapToDouble(basketItem -> basketItem.getQuantity() * basketItem.getMenuItem().getPrice())
                .sum();
        basket.setTotalPrice(totalPrice);
        basketRepository.save(basket);

    }

    // update quantity of an already existing (added) item in basket
    public BasketItem updateMenuItemInBasket(Long basketItemId, int quantity) {
        BasketItem basketItem = basketItemRepository.findById(basketItemId)
                .orElseThrow(() -> new EntityNotFoundException("Basket item not found"));

        Basket basket = basketItem.getBasket();
        Optional<BasketItem> existingBasketItem = basket.getBasketItems().stream()
                .filter(bi -> bi.getId().equals(basketItemId))
                .findFirst();

        if (existingBasketItem.isPresent()) {
            BasketItem existingItem = existingBasketItem.get();
            if (quantity > 0) {
                // Update quantity
                existingItem.setQuantity(quantity);
                basketItemRepository.save(existingItem);
            } else {
                // Remove item from basket
                basket.getBasketItems().remove(existingItem);
                basketRepository.save(basket);
                basketItemRepository.delete(existingItem);
            }
            // Update total price of the basket
            updateTotalPrice(basket);
            return existingItem;
        } else {
            throw new EntityNotFoundException("Menu item not found in basket");
        }
    }

    // remove menu item from basket
    public void removeMenuItemFromBasket(Long basketItemId) {
        BasketItem basketItem = basketItemRepository.findById(basketItemId)
                .orElseThrow(() -> new EntityNotFoundException("Basket item not found"));
        Basket basket = basketItem.getBasket();
        basket.getBasketItems().remove(basketItem);
        basketRepository.save(basket);
        basketItemRepository.delete(basketItem);
        // update total price of the basket
        updateTotalPrice(basket);
    }

}
