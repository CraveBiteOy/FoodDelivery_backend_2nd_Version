package com.cravebite.backend_2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cravebite.backend_2.models.entities.Basket;
import com.cravebite.backend_2.models.entities.BasketItem;
import com.cravebite.backend_2.models.mappers.BasketItemMapper;
import com.cravebite.backend_2.models.mappers.BasketMapper;
import com.cravebite.backend_2.models.request.BasketItemRequestDTO;
import com.cravebite.backend_2.models.response.BasketItemResponseDTO;
import com.cravebite.backend_2.models.response.BasketResponseDTO;
import com.cravebite.backend_2.service.BasketService;

@RestController
@RequestMapping("/basket")
public class BasketController {

    @Autowired
    private BasketService basketService;

    @Autowired
    private BasketItemMapper basketItemMapper;

    @Autowired
    private BasketMapper basketMapper;

    // create basket from customer id and restaurant id
    @GetMapping("/create/{customerId}/{restaurantId}")
    public ResponseEntity<BasketResponseDTO> createBasketFromCustomer(@PathVariable Long customerId,
            @PathVariable Long restaurantId) {
        Basket basket = basketService.createBasketFromCustomer(customerId, restaurantId);
        BasketResponseDTO basketResponseDTO = basketMapper.toBasketResponseDTO(basket);
        return ResponseEntity.ok(basketResponseDTO);
    }

    // create basket from authenicated User
    @GetMapping("/create/{restaurantId}")
    public ResponseEntity<BasketResponseDTO> createBasketFromAuthenticatedUser(@PathVariable Long restaurantId) {
        Basket basket = basketService.createBasketFromAuthenticatedUser(restaurantId);
        BasketResponseDTO basketResponseDTO = basketMapper.toBasketResponseDTO(basket);
        return ResponseEntity.ok(basketResponseDTO);
    }

    // add item to basket
    @PostMapping("/item")
    public ResponseEntity<BasketItemResponseDTO> addMenuItemToBasket(
            @RequestBody BasketItemRequestDTO basketItemRequestDTO) {
        BasketItem basketItem = basketService.addMenuItemToBasket(basketItemRequestDTO.getBasketId(),
                basketItemRequestDTO.getMenuItemId(), basketItemRequestDTO.getQuantity());
        BasketItemResponseDTO basketItemResponseDTO = basketItemMapper.toBasketItemResponseDTO(basketItem);
        return ResponseEntity.ok(basketItemResponseDTO);
    }

    // update quantity of an item in basket
    @PutMapping("/items/{basketItem}/quantity/{quantity}")
    public ResponseEntity<BasketItemResponseDTO> updateMenuItemInBasket(@PathVariable Long basketItemId,
            @PathVariable int quantity) {
        BasketItem basketItem = basketService.updateMenuItemInBasket(basketItemId, quantity);
        BasketItemResponseDTO basketItemResponseDTO = basketItemMapper.toBasketItemResponseDTO(basketItem);
        return ResponseEntity.ok(basketItemResponseDTO);
    }

    // remove item from basket
    @DeleteMapping("/items/{basketItemId}")
    public ResponseEntity<Void> removeMenuItemFromBasket(@PathVariable Long basketItemId) {
        basketService.removeMenuItemFromBasket(basketItemId);
        return ResponseEntity.noContent().build();
    }

    // get basket by basket id
    @GetMapping("/{basketId}")
    public ResponseEntity<BasketResponseDTO> getBasket(@PathVariable Long basketId) {
        Basket basket = basketService.getBasketById(basketId);
        BasketResponseDTO basketResponseDTO = basketMapper.toBasketResponseDTO(basket);
        return ResponseEntity.ok(basketResponseDTO);
    }

}
