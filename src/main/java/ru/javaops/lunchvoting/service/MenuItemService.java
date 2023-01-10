package ru.javaops.lunchvoting.service;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.lunchvoting.model.MenuItem;
import ru.javaops.lunchvoting.repository.MenuItemRepository;
import ru.javaops.lunchvoting.repository.RestaurantRepository;

@Service
@AllArgsConstructor
public class MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    @CacheEvict(value = "restaurantsForVoting", allEntries = true)
    public MenuItem save(MenuItem menuItem, int restaurantId) {
        menuItem.setRestaurant(restaurantRepository.getExisted(restaurantId));
        return menuItemRepository.save(menuItem);
    }
}
