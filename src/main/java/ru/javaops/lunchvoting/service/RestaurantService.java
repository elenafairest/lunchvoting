package ru.javaops.lunchvoting.service;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.javaops.lunchvoting.model.Restaurant;
import ru.javaops.lunchvoting.repository.RestaurantRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    @CacheEvict(value = "restaurants", allEntries = true)
    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Cacheable("restaurants")
    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public void delete(int id) {
        restaurantRepository.deleteExisted(id);
    }

    public Optional<Restaurant> get(int id) {
        return restaurantRepository.findById(id);
    }
}
