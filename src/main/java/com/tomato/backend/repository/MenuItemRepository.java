package com.tomato.backend.repository;

import com.tomato.backend.entity.MenuItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MenuItemRepository extends MongoRepository<MenuItem, String> {
    List<MenuItem> findByCategoryIgnoreCase(String category);
}
