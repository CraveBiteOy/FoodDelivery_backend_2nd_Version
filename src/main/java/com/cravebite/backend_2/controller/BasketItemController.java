package com.cravebite.backend_2.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cravebite.backend_2.models.entities.BasketItem;
import com.cravebite.backend_2.models.mappers.BasketItemMapper;
import com.cravebite.backend_2.models.response.BasketItemResponseDTO;
import com.cravebite.backend_2.service.BasketItemService;

@RestController
@RequestMapping("/basketItem")
public class BasketItemController {

    @Autowired
    private BasketItemService basketItemService;

    @Autowired
    private BasketItemMapper basketItemMapper;

    // get a single basketItem by id
    @GetMapping("/{basketItemId}")
    public ResponseEntity<BasketItemResponseDTO> getBasketItemById(@PathVariable Long basketItemId) {
        BasketItem basketItem = basketItemService.getBaskeItemById(basketItemId);
        BasketItemResponseDTO response = basketItemMapper.toBasketItemResponseDTO(basketItem);
        return ResponseEntity.ok(response);
    }

    // get all basketItems by a given basket id
    @GetMapping("/basket/{basketId}")
    public ResponseEntity<List<BasketItemResponseDTO>> getAllBasketItemsByBasketId(@PathVariable Long basketId) {
        List<BasketItem> basketItems = basketItemService.getBasketItemsByBasketId(basketId);
        List<BasketItemResponseDTO> response = basketItems.stream()
                .map(basketItem -> basketItemMapper.toBasketItemResponseDTO(basketItem))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    // get a single basketItem by basket id and menu item id
    @GetMapping("/basket/{basketId}/menuItem/{menuItemId}")
    public ResponseEntity<BasketItemResponseDTO> getBasketItemByBasketIdAndMenuItemId(@PathVariable Long basketId,
            @PathVariable Long menuItemId) {
        BasketItem basketItem = basketItemService.getBasketItemByBasketIdAndMenuItemId(basketId, menuItemId);
        BasketItemResponseDTO response = basketItemMapper.toBasketItemResponseDTO(basketItem);
        return ResponseEntity.ok(response);
    }

}