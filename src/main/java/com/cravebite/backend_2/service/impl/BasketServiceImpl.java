package com.cravebite.backend_2.service.impl;

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

    // check if an authenticated customer owns the basket
    public boolean isBasketOwnedByAuthenticatedCustomer(Long basketId) {
        return getBasketById(basketId).getCustomer().getId()
                .equals(customerService.getCustomerFromAuthenticatedUser().getId());
    }

    // create basket from customer id and restaurant id
    public Basket createBasketFromCustomer(Long customerId, Long restaurantId) {
        Customer customer = customerService.getCustomerById(customerId);
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        Optional<Basket> basket = getBasket(customer, restaurant);

        return basket.orElseGet(() -> {
            Basket newBasket = new Basket();
            newBasket.setCustomer(customer);
            newBasket.setRestaurant(restaurant);
            newBasket.setTotalPrice(0.0);
            basketRepository.save(newBasket);
            return newBasket;

            // }
        });
    }

    // create basket from authenticated user
    public Basket createBasketFromAuthenticatedUser(Long restaurantId) {
        User authenticatedUser = userService.getAuthenticatedUser();
        Customer customer = customerService.getCustomerByUserId(authenticatedUser.getId());
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        Optional<Basket> basket = getBasket(customer, restaurant);

        return basket.orElseGet(() -> {
            Basket newBasket = new Basket();
            newBasket.setCustomer(customer);
            newBasket.setRestaurant(restaurant);
            newBasket.setTotalPrice(0.0);
            basketRepository.save(newBasket);
            return newBasket;

        });
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
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        Basket basket = getBasketById(basketId);
        MenuItem menuItem = menuItemService.getMenuItemById(menuItemId);

        BasketItem basketItem = null;

        try {
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
        double itemTotalPrice = basketItem.getQuantity() * basketItem.getMenuItem().getPrice();
        basket.setTotalPrice(basket.getTotalPrice() + itemTotalPrice);
        basketRepository.save(basket);

        return basketItem;
    }

    // update quantity of an already existing (added) item in basket
    public BasketItem updateMenuItemInBasket(Long basketItemId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        BasketItem basketItem = basketItemRepository.findById(basketItemId)
                .orElseThrow(() -> new EntityNotFoundException("Basket item not found"));

        Basket basket = basketItem.getBasket();
        Optional<BasketItem> existingBasketItem = basket.getBasketItems().stream()
                .filter(bi -> bi.getId().equals(basketItemId))
                .findFirst();

        if (existingBasketItem.isPresent()) {
            BasketItem existingItem = existingBasketItem.get();

            // Calculate the old total price for this item
            double oldItemTotalPrice = existingItem.getQuantity() * existingItem.getMenuItem().getPrice();

            if (quantity > 0) {
                existingItem.setQuantity(quantity);

                // Calculate the new total price
                double newItemTotalPrice = existingItem.getQuantity() * existingItem.getMenuItem().getPrice();

                // Update the total price of the basket
                basket.setTotalPrice(basket.getTotalPrice() - oldItemTotalPrice + newItemTotalPrice);

                basketItemRepository.save(existingItem);
            } else {
                basket.getBasketItems().remove(existingItem);
                basket.setTotalPrice(basket.getTotalPrice() - oldItemTotalPrice);
                basketItemRepository.delete(existingItem);
            }

            basketRepository.save(basket);
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
        double itemTotalPrice = basketItem.getQuantity() * basketItem.getMenuItem().getPrice();
        basket.setTotalPrice(basket.getTotalPrice() - itemTotalPrice);
        basketRepository.save(basket);
    }

}
