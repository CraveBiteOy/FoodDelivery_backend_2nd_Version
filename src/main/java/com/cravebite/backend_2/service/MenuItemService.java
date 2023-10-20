package com.cravebite.backend_2.service;

import java.util.List;

import com.cravebite.backend_2.models.entities.MenuItem;

public interface MenuItemService {

    public MenuItem getMenuItemById(Long menuItemId);
    
    public boolean existsById(Long menuItemId);

}
