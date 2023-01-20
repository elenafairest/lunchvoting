package com.github.elenafairest.lunchvoting.repository;

import com.github.elenafairest.lunchvoting.model.Restaurant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {
    @Query("SELECT r FROM Restaurant r JOIN FETCH r.menu m WHERE r.id = :restaurantId AND m.menuDate=CURRENT_DATE")
    Optional<Restaurant> getWithMenu(int restaurantId);

    @Query("SELECT r FROM Restaurant r JOIN FETCH r.menu m WHERE m.menuDate=CURRENT_DATE")
    List<Restaurant> getAllWithMenu();
}
