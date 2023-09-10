package com.cravebite.backend_2.service;

import java.util.List;

import com.cravebite.backend_2.models.entities.BasketItem;

public interface BasketItemService {

    public BasketItem getBaskeItemById(Long basketItemId);

    public List<BasketItem> getBasketItemsByBasketId(Long basketId);

    public BasketItem getBasketItemByBasketIdAndMenuItemId(Long basketId, Long menuItemId);

    public BasketItem save(BasketItem basketItem);
}
