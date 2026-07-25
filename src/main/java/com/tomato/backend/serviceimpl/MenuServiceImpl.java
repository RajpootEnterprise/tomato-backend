package com.tomato.backend.serviceimpl;

import com.tomato.backend.dto.MenuItemRequest;
import com.tomato.backend.entity.MenuItem;
import com.tomato.backend.exceptions.ResourceNotFoundException;
import com.tomato.backend.repository.MenuItemRepository;
import com.tomato.backend.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuItemRepository menuItemRepository;

    @Override
    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    @Override
    public List<MenuItem> getMenuItemsByCategory(String category) {
        return menuItemRepository.findByCategoryIgnoreCase(category);
    }

    @Override
    public MenuItem getMenuItemById(String id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + id));
    }

    @Override
    public MenuItem createMenuItem(MenuItemRequest request) {
        MenuItem item = MenuItem.builder()
                .name(request.getName())
                .category(request.getCategory())
                .description(request.getDescription())
                .price(request.getPrice())
                .rating(request.getRating())
                .imageUrl(request.getImageUrl())
                .build();
        return menuItemRepository.save(item);
    }

    @Override
    public MenuItem updateMenuItem(String id, MenuItemRequest request) {
        MenuItem existing = getMenuItemById(id);
        existing.setName(request.getName());
        existing.setCategory(request.getCategory());
        existing.setDescription(request.getDescription());
        existing.setPrice(request.getPrice());
        existing.setRating(request.getRating());
        existing.setImageUrl(request.getImageUrl());
        return menuItemRepository.save(existing);
    }

    @Override
    public void deleteMenuItem(String id) {
        MenuItem existing = getMenuItemById(id);
        menuItemRepository.delete(existing);
    }
}
