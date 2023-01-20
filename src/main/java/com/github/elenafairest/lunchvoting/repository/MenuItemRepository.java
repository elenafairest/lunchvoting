package com.github.elenafairest.lunchvoting.repository;

import com.github.elenafairest.lunchvoting.error.DataConflictException;
import com.github.elenafairest.lunchvoting.model.MenuItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface MenuItemRepository extends BaseRepository<MenuItem> {
    @Query("SELECT m from MenuItem m where m.restaurant.id = :restaurantId ORDER BY m.menuDate DESC, m.name")
    List<MenuItem> getAll(int restaurantId);

    @Query("SELECT m FROM MenuItem m WHERE m.id = :id and m.restaurant.id = :restaurantId")
    Optional<MenuItem> get(int id, int restaurantId);

    @Query("SELECT m FROM MenuItem m " +
            "WHERE m.restaurant.id=:restaurantId " +
            "AND (:menuDate IS NULL OR m.menuDate=:menuDate) " +
            "ORDER BY m.name")
    List<MenuItem> getByDate(Integer restaurantId, LocalDate menuDate);

    default MenuItem checkBelong(int id, int restaurantId) {
        return get(id, restaurantId).orElseThrow(
                () -> new DataConflictException("MenuItem id=" + id + " is not found or doesn't belong to Restaurant id=" + restaurantId));
    }
}
