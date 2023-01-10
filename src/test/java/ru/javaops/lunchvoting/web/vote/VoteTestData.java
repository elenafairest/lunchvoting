package ru.javaops.lunchvoting.web.vote;

import ru.javaops.lunchvoting.model.Vote;
import ru.javaops.lunchvoting.web.MatcherFactory;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javaops.lunchvoting.web.restaurant.RestaurantTestData.*;
import static ru.javaops.lunchvoting.web.user.UserTestData.admin;
import static ru.javaops.lunchvoting.web.user.UserTestData.user;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingAssertions(Vote.class,
            (a, e) -> assertThat(a).usingRecursiveComparison()
                    .ignoringFields("user.registered", "user.password", "restaurant.menu").isEqualTo(e),
            (a, e) -> assertThat(a).usingRecursiveFieldByFieldElementComparatorIgnoringFields("user.registered", "user.password", "restaurant.menu").isEqualTo(e)
    );
    public static final int VOTE1_ID = 1;
    public static final int NOT_FOUND = 1000;

    public static final Vote vote1 = new Vote(VOTE1_ID, olivka, user, LocalDate.of(2022, Month.NOVEMBER, 8));
    public static final Vote vote2 = new Vote(VOTE1_ID + 1, kakaoMama, admin, LocalDate.of(2022, Month.NOVEMBER, 8));
    public static final Vote vote3 = new Vote(VOTE1_ID + 2, cheshireCat, user, LocalDate.of(2022, Month.DECEMBER, 9));
    public static final Vote vote4 = new Vote(VOTE1_ID + 3, olivka, admin, LocalDate.of(2022, Month.DECEMBER, 9));
    public static final Vote vote5 = new Vote(VOTE1_ID + 4, kakaoMama, user, LocalDate.now().minusDays(2));
    public static final Vote vote6 = new Vote(VOTE1_ID + 5, olivka, admin, LocalDate.now());
}
