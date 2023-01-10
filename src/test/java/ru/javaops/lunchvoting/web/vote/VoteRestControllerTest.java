package ru.javaops.lunchvoting.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.CollectionUtils;
import ru.javaops.lunchvoting.repository.VoteRepository;
import ru.javaops.lunchvoting.web.AbstractControllerTest;

import java.time.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.lunchvoting.util.DateTimeUtil.setClock;
import static ru.javaops.lunchvoting.web.restaurant.RestaurantTestData.*;
import static ru.javaops.lunchvoting.web.user.UserTestData.*;
import static ru.javaops.lunchvoting.web.vote.VoteRestController.REST_URL;

class VoteRestControllerTest extends AbstractControllerTest {
    @Autowired
    private VoteRepository voteRepository;
    @Test
    @WithUserDetails(value = USER_MAIL)
    void getRestaurantsForVoting() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_WITH_MENU_MATCHER.contentJson(kakaoMama, olivka, cheshireCat));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void vote() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL + "/" + CHESHIRE_CAT_ID + "/vote"))
                .andDo(print())
                .andExpect(status().isOk());
        assertFalse(CollectionUtils.isEmpty(voteRepository.getFiltered(USER_ID, null, LocalDate.now())));
    }

    @Test
    void voteUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL + "/" + CHESHIRE_CAT_ID + "/vote"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateVote() throws Exception {
        Instant instant = LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0, 0)).toInstant(OffsetDateTime.now().getOffset());
        Clock clock = Clock.fixed(instant, ZoneId.systemDefault());
        setClock(clock);
        perform(MockMvcRequestBuilders.post(REST_URL + "/" + KAKAO_MAMA_ID + "/vote"))
                .andDo(print())
                .andExpect(status().isOk());
        setClock(Clock.systemDefaultZone());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateVoteAfterAllowedTime() throws Exception {
        Instant instant = LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0, 0)).toInstant(OffsetDateTime.now().getOffset());
        Clock clock = Clock.fixed(instant, ZoneId.systemDefault());
        setClock(clock);
        perform(MockMvcRequestBuilders.post(REST_URL + "/" + KAKAO_MAMA_ID + "/vote"))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
        setClock(Clock.systemDefaultZone());
    }


    @Test
    @WithUserDetails(value = USER_MAIL)
    void getNumberOfVotes() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/numberOfVotes/" + OLIVKA_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }
}