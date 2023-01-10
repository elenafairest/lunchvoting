package ru.javaops.lunchvoting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.lunchvoting.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant>{
    @Query("SELECT r FROM Restaurant r JOIN FETCH r.menu m WHERE m.date=:date")
    List<Restaurant> getWithMenu(LocalDate date);

    @Query("SELECT COUNT(v) FROM Vote v WHERE v.restaurant.id = :restaurantId AND v.date=CURRENT_DATE")
    int getNumberOfVotes(int restaurantId);
}
