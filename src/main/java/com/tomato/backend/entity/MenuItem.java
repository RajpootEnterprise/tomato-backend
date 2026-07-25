package com.tomato.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "menu_items")
public class MenuItem {

    @Id
    private String id;

    private String name;          // e.g. Greek Salad
    private String category;      // Salad, Rolls, Deserts, Sandwich, Cake, Pure Veg, Pasta, Noodles
    private String description;
    private double price;
    private double rating;
    private String imageUrl;
}
