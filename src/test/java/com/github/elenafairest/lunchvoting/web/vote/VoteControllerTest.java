package com.github.elenafairest.lunchvoting.web.vote;

import com.github.elenafairest.lunchvoting.model.Vote;
import com.github.elenafairest.lunchvoting.repository.VoteRepository;
import com.github.elenafairest.lunchvoting.util.VoteUtil;
import com.github.elenafairest.lunchvoting.web.AbstractControllerTest;
import com.github.elenafairest.lunchvoting.web.restaurant.RestaurantTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.*;
import java.util.List;

import static com.github.elenafairest.lunchvoting.util.DateTimeUtil.setClock;
import static com.github.elenafairest.lunchvoting.web.restaurant.RestaurantTestData.*;
import static com.github.elenafairest.lunchvoting.web.user.UserTestData.*;
import static com.github.elenafairest.lunchvoting.web.vote.VoteController.URL;
import static com.github.elenafairest.lunchvoting.web.vote.VoteTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteControllerTest extends AbstractControllerTest {
    private static final String URL_SLASH = URL + "/";
    private static final LocalTime BEFORE_UPDATE_VOTE_TIME_BORDER = LocalTime.of(10, 0);
    private static final LocalTime AFTER_UPDATE_VOTE_TIME_BORDER = LocalTime.of(12, 0);
    @Autowired
    private VoteRepository voteRepository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(URL_SLASH + VoteTestData.VOTE1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VoteTestData.VOTE_TO_MATCHER.contentJson(VoteUtil.createTo(VoteTestData.vote1)));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(URL_SLASH + VoteTestData.NOT_FOUND))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void getUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(URL_SLASH + VoteTestData.VOTE1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(URL_SLASH + vote2.getId()))
                .andExpect(status().isConflict());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createWithLocation() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.post(URL)
                .param("restaurantId", String.valueOf(CHESHIRE_CAT_ID)))
                .andDo(print())
                .andExpect(status().isCreated());
        Vote created = VOTE_MATCHER.readFromJson(action);
        Vote newVote = new Vote(created.getId(), cheshireCat, user, LocalDate.now());
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(voteRepository.getExisted(created.getId()), newVote);

    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createNotFoundRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.post(URL)
                .param("restaurantId", String.valueOf(RestaurantTestData.NOT_FOUND)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void createUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.post(URL)
                .param("restaurantId", String.valueOf(CHESHIRE_CAT_ID)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateVote() throws Exception {
        Instant instant = LocalDateTime.of(LocalDate.now(), BEFORE_UPDATE_VOTE_TIME_BORDER).toInstant(OffsetDateTime.now().getOffset());
        Clock clock = Clock.fixed(instant, ZoneId.systemDefault());
        setClock(clock);
        Vote updated = new Vote(vote6);
        updated.setRestaurant(kakaoMama);
        perform(MockMvcRequestBuilders.put(URL)
                .param("restaurantId", String.valueOf(KAKAO_MAMA_ID)))
                .andDo(print())
                .andExpect(status().isNoContent());
        VOTE_MATCHER.assertMatch(voteRepository.getExisted(VOTE1_ID + 5), updated);
        setClock(Clock.systemDefaultZone());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateAfterAllowedTime() throws Exception {
        Instant instant = LocalDateTime.of(LocalDate.now(), AFTER_UPDATE_VOTE_TIME_BORDER).toInstant(OffsetDateTime.now().getOffset());
        Clock clock = Clock.fixed(instant, ZoneId.systemDefault());
        setClock(clock);
        perform(MockMvcRequestBuilders.put(URL)
                .param("restaurantId", String.valueOf(KAKAO_MAMA_ID)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
        setClock(Clock.systemDefaultZone());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateNotFound() throws Exception {
        Instant instant = LocalDateTime.of(LocalDate.now(), BEFORE_UPDATE_VOTE_TIME_BORDER).toInstant(OffsetDateTime.now().getOffset());
        Clock clock = Clock.fixed(instant, ZoneId.systemDefault());
        setClock(clock);
        perform(MockMvcRequestBuilders.put(URL)
                .param("restaurantId", String.valueOf(KAKAO_MAMA_ID)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void updateUnauthorized() throws Exception {
        Instant instant = LocalDateTime.of(LocalDate.now(), BEFORE_UPDATE_VOTE_TIME_BORDER).toInstant(OffsetDateTime.now().getOffset());
        Clock clock = Clock.fixed(instant, ZoneId.systemDefault());
        setClock(clock);
        perform(MockMvcRequestBuilders.put(URL)
                .param("restaurantId", String.valueOf(KAKAO_MAMA_ID)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VoteTestData.VOTE_TO_MATCHER.contentJson(VoteUtil.getTos(List.of(VoteTestData.vote5, VoteTestData.vote3, VoteTestData.vote1))));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(URL + "/by-date")
                .param("date", String.valueOf(LocalDate.now().minusDays(2))))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VoteTestData.VOTE_TO_MATCHER.contentJson(VoteUtil.createTo(VoteTestData.vote5)));
    }
}