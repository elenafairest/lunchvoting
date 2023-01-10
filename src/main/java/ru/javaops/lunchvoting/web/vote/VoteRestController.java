package ru.javaops.lunchvoting.web.vote;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.javaops.lunchvoting.model.Restaurant;
import ru.javaops.lunchvoting.repository.RestaurantRepository;
import ru.javaops.lunchvoting.service.VoteService;
import ru.javaops.lunchvoting.web.AuthUser;

import java.util.List;

@RestController
@RequestMapping(value = VoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class VoteRestController {
    static final String REST_URL = "/api/rest/votes";
    private final VoteService voteService;
    private final RestaurantRepository restaurantRepository;

    @GetMapping
    public List<Restaurant> getRestaurantsForVoting() {
        log.info("get restaurants for voting");
        return voteService.getForVoting();
    }

    @PostMapping(value = "/{id}/vote")
    public void vote(@PathVariable("id") int restaurantId, @AuthenticationPrincipal AuthUser authUser) {
        log.info("User {} votes for Restaurant {}", authUser.id(), restaurantId);
        voteService.save(authUser.id(), restaurantId);
    }

    @GetMapping(value = "/numberOfVotes/{id}")
    public int numberOfVotes(@PathVariable("id") int restaurantId) {
        log.info("Number of votes for restaurant {}", restaurantId);
        return restaurantRepository.getNumberOfVotes(restaurantId);
    }
}
