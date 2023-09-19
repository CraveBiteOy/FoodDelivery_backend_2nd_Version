package com.cravebite.backend_2.service;

import com.cravebite.backend_2.models.entities.Basket;
import com.cravebite.backend_2.models.entities.BasketItem;

public interface BasketService {

    public Basket getBasketById(Long basketId);

    public boolean existsById(Long basketId);

    public boolean isBasketOwnedByAuthenticatedCustomer(Long basketId);

    public Basket createBasketFromCustomer(Long customerId, Long restaurantId);

    public Basket createBasketFromAuthenticatedUser(Long restaurantId);

    public BasketItem addMenuItemToBasket(Long basketId, Long menuItemId, int quantity);

    public void removeMenuItemFromBasket(Long basketItemId);

    public BasketItem updateMenuItemInBasket(Long menuItemId, int quantity);

}
