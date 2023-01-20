package com.github.elenafairest.lunchvoting.service;

import com.github.elenafairest.lunchvoting.model.Vote;
import com.github.elenafairest.lunchvoting.repository.RestaurantRepository;
import com.github.elenafairest.lunchvoting.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public Vote save(Vote vote, int restaurantId) {
        vote.setRestaurant(restaurantRepository.getReferenceById(restaurantId));
        return voteRepository.save(vote);
    }
}
