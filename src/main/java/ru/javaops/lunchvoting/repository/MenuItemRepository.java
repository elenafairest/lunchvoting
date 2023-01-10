package ru.javaops.lunchvoting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.lunchvoting.error.DataConflictException;
import ru.javaops.lunchvoting.model.MenuItem;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface MenuItemRepository extends BaseRepository<MenuItem> {
    @Query("SELECT m FROM MenuItem m WHERE m.id = :id and m.restaurant.id = :restaurantId")
    Optional<MenuItem> get(int id, int restaurantId);

    @Query("SELECT m FROM MenuItem m " +
            "WHERE (:restaurantId IS NULL OR m.restaurant.id=:restaurantId) " +
            "AND (:date IS NULL OR m.date=:date)")
    List<MenuItem> getFiltered(Integer restaurantId, LocalDate date);

    default MenuItem checkBelong(int id, int restaurantId) {
        return get(id, restaurantId).orElseThrow(
                () -> new DataConflictException("MenuItem id=" + id + " doesn't belong to Restaurant id=" + restaurantId));
    }
}
