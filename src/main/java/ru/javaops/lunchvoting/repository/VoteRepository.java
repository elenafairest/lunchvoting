package ru.javaops.lunchvoting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.lunchvoting.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {
    @Query("SELECT v FROM Vote v JOIN FETCH v.user JOIN FETCH v.restaurant " +
            "WHERE (:userId IS NULL OR v.user.id=:userId) " +
            "AND (:restaurantId IS NULL OR v.restaurant.id=:restaurantId) " +
            "AND (:date IS NULL OR v.date=:date)")
    List<Vote> getFiltered(Integer userId, Integer restaurantId, LocalDate date);

    @Query("SELECT v FROM Vote v JOIN FETCH v.user JOIN FETCH v.restaurant WHERE v.id=:id")
    Optional<Vote> getWithData(int id);

    @Query("SELECT v FROM Vote v JOIN FETCH v.user JOIN FETCH v.restaurant")
    List<Vote> getAllWithData();
}
