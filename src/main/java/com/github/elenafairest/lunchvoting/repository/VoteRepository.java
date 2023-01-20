package com.github.elenafairest.lunchvoting.repository;

import com.github.elenafairest.lunchvoting.error.DataConflictException;
import com.github.elenafairest.lunchvoting.model.Vote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("SELECT v FROM Vote v WHERE v.id=:id AND v.user.id = :userId")
    Optional<Vote> get(int id, int userId);

    @Query("SELECT v FROM Vote v WHERE v.user.id = :userId ORDER BY v.voteDate DESC")
    List<Vote> getAll(int userId);

    @Query("SELECT v FROM Vote v WHERE v.user.id = :userId AND v.voteDate = :voteDate")
    Optional<Vote> getByDate(Integer userId, LocalDate voteDate);

    default Vote checkBelong(int id, int userId) {
        return get(id, userId).orElseThrow(
                () -> new DataConflictException("Vote id=" + id + " is not exist or doesn't belong to User id=" + userId));
    }
}
