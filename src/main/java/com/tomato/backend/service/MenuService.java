package com.tomato.backend.service;

import com.tomato.backend.dto.MenuItemRequest;
import com.tomato.backend.entity.MenuItem;

import java.util.List;

public interface MenuService {
    List<MenuItem> getAllMenuItems();
    List<MenuItem> getMenuItemsByCategory(String category);
    MenuItem getMenuItemById(String id);
    MenuItem createMenuItem(MenuItemRequest request);
    MenuItem updateMenuItem(String id, MenuItemRequest request);
    void deleteMenuItem(String id);
}
