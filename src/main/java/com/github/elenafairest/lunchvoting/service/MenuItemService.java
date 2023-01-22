package com.github.elenafairest.lunchvoting.service;

import com.github.elenafairest.lunchvoting.model.MenuItem;
import com.github.elenafairest.lunchvoting.repository.MenuItemRepository;
import com.github.elenafairest.lunchvoting.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    @CacheEvict(value = "restaurantsWithMenu", allEntries = true)
    public MenuItem save(MenuItem menuItem, int restaurantId) {
        menuItem.setRestaurant(restaurantRepository.getReferenceById(restaurantId));
        return menuItemRepository.save(menuItem);
    }
}
