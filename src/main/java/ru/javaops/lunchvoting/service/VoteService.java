package ru.javaops.lunchvoting.service;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import ru.javaops.lunchvoting.model.Restaurant;
import ru.javaops.lunchvoting.model.Vote;
import ru.javaops.lunchvoting.repository.RestaurantRepository;
import ru.javaops.lunchvoting.repository.UserRepository;
import ru.javaops.lunchvoting.repository.VoteRepository;
import ru.javaops.lunchvoting.util.validation.ValidationUtil;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @Transactional
    public void save(int userId, int restaurantId) {
        Restaurant restaurant = restaurantRepository.getExisted(restaurantId);
        Vote vote;
        List<Vote> foundByUserAndDate = voteRepository.getFiltered(userId, null, LocalDate.now());
        vote = CollectionUtils.isEmpty(foundByUserAndDate) ? null : foundByUserAndDate.get(0);
        if (vote != null) {
            ValidationUtil.checkTime();
            vote.setRestaurant(restaurant);
        } else {
            vote = new Vote(null, restaurant, userRepository.getExisted(userId), LocalDate.now());
        }
        voteRepository.save(vote);
    }

    @Cacheable("restaurantsForVoting")
    public List<Restaurant> getForVoting() {
        return restaurantRepository.getWithMenu(LocalDate.now());
    }
}
