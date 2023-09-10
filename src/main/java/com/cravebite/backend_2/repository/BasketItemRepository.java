package com.cravebite.backend_2.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cravebite.backend_2.models.entities.BasketItem;

public interface BasketItemRepository extends JpaRepository<BasketItem, Long> {

    Optional<BasketItem> findByBasketIdAndMenuItemId(Long basketId, Long menuItemId);

    List<BasketItem> findByBasketId(Long basketId);
}
