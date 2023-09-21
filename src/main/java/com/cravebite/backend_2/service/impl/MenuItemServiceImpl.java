package com.cravebite.backend_2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cravebite.backend_2.exception.CraveBiteGlobalExceptionHandler;
import com.cravebite.backend_2.models.entities.MenuItem;
import com.cravebite.backend_2.repository.MenuItemRepository;
import com.cravebite.backend_2.service.MenuItemService;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    // get menu item by id
    public MenuItem getMenuItemById(Long menuItemId) {
        return menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new CraveBiteGlobalExceptionHandler(HttpStatus.NOT_FOUND, "MenuItem not found"));
    }

    // check if menu item exists
    public boolean existsById(Long menuItemId) {
        return menuItemRepository.existsById(menuItemId);
    }

}
