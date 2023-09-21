package com.cravebite.backend_2.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cravebite.backend_2.exception.CraveBiteGlobalExceptionHandler;
import com.cravebite.backend_2.models.entities.BasketItem;
import com.cravebite.backend_2.repository.BasketItemRepository;
import com.cravebite.backend_2.repository.BasketRepository;
import com.cravebite.backend_2.repository.MenuItemRepository;
import com.cravebite.backend_2.service.BasketItemService;

@Service
public class BasketItemServiceImpl implements BasketItemService {

    @Autowired
    private BasketItemRepository basketItemRepository;

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    // get basket item by id
    public BasketItem getBaskeItemById(Long basketItemId) {
        return basketItemRepository.findById(basketItemId)
                .orElseThrow(() -> new CraveBiteGlobalExceptionHandler(HttpStatus.NOT_FOUND, "BasketItem not found"));
    }

    // get basket item by basket id and menu item id
    public BasketItem getBasketItemByBasketIdAndMenuItemId(Long basketId, Long menuItemId) {
        if (!basketRepository.existsById(basketId) || !menuItemRepository.existsById(menuItemId)) {

            throw new CraveBiteGlobalExceptionHandler(HttpStatus.NOT_FOUND, "Basket or MenuItem not found");
        }
        return basketItemRepository.findByBasketIdAndMenuItemId(basketId, menuItemId)
                .orElseThrow(() -> new CraveBiteGlobalExceptionHandler(HttpStatus.NOT_FOUND, "BasketItem not found"));
    }

    // get list of basket items by basket id
    public List<BasketItem> getBasketItemsByBasketId(Long basketId) {
        if (!basketRepository.existsById(basketId)) {
            throw new CraveBiteGlobalExceptionHandler(HttpStatus.NOT_FOUND, "Basket not found");
        }
        return basketItemRepository.findByBasketId(basketId);
    }

    public BasketItem save(BasketItem basketItem) {
        return basketItemRepository.save(basketItem);
    }

}
