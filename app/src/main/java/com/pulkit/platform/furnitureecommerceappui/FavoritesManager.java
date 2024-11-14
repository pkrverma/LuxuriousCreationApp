package com.pulkit.platform.furnitureecommerceappui;

import java.util.ArrayList;
import java.util.List;

public class FavoritesManager {
    private static FavoritesManager instance;
    // Use a generic type to hold both types of models
    private final List<Object> favoriteModels;

    private FavoritesManager() {
        favoriteModels = new ArrayList<>();
    }

    public static FavoritesManager getInstance() {
        if (instance == null) {
            instance = new FavoritesManager();
        }
        return instance;
    }

    // Retrieve all favorite models
    public List<Object> getFavoriteModels() {
        return favoriteModels;
    }

    // Check if a specific model is a favorite
    public boolean isFavorite(Object model) {
        return favoriteModels.contains(model);
    }

    // Add a favorite model
    public void addFavorite(Object model) {
        if (!isFavorite(model)) {
            favoriteModels.add(model);
        }
    }

    // Remove a favorite model
    public void removeFavorite(Object model) {
        favoriteModels.remove(model);
    }
}
