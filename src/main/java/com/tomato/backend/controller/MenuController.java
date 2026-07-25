package com.tomato.backend.controller;

import com.tomato.backend.dto.ApiResponse;
import com.tomato.backend.dto.MenuItemRequest;
import com.tomato.backend.entity.MenuItem;
import com.tomato.backend.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    // GET /api/menu               -> all items
    // GET /api/menu?category=Salad -> filtered by category (matches your menu circles: Salad, Rolls, Deserts...)
    @GetMapping
    public ResponseEntity<ApiResponse<List<MenuItem>>> getMenu(
            @RequestParam(required = false) String category) {

        List<MenuItem> items = (category == null || category.isBlank())
                ? menuService.getAllMenuItems()
                : menuService.getMenuItemsByCategory(category);

        return ResponseEntity.ok(ApiResponse.success("Menu fetched", items));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MenuItem>> getMenuItem(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.success("Menu item fetched", menuService.getMenuItemById(id)));
    }

    // Admin-only in practice; protected because it's not under /api/menu GET permitAll rule
    @PostMapping
    public ResponseEntity<ApiResponse<MenuItem>> createMenuItem(@Valid @RequestBody MenuItemRequest request) {
        MenuItem created = menuService.createMenuItem(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Menu item created", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MenuItem>> updateMenuItem(
            @PathVariable String id, @Valid @RequestBody MenuItemRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Menu item updated", menuService.updateMenuItem(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteMenuItem(@PathVariable String id) {
        menuService.deleteMenuItem(id);
        return ResponseEntity.ok(ApiResponse.success("Menu item deleted", null));
    }
}
