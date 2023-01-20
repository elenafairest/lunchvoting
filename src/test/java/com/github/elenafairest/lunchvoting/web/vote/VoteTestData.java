package com.github.elenafairest.lunchvoting.web.vote;

import com.github.elenafairest.lunchvoting.model.Vote;
import com.github.elenafairest.lunchvoting.to.VoteTo;
import com.github.elenafairest.lunchvoting.web.MatcherFactory;
import com.github.elenafairest.lunchvoting.web.restaurant.RestaurantTestData;

import java.time.LocalDate;
import java.time.Month;

import static com.github.elenafairest.lunchvoting.web.user.UserTestData.admin;
import static com.github.elenafairest.lunchvoting.web.user.UserTestData.user;

public class VoteTestData {
    public static final MatcherFactory.Matcher<VoteTo> VOTE_TO_MATCHER = MatcherFactory.usingEqualsComparator(VoteTo.class);
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingEqualsComparator(Vote.class);
    public static final int VOTE1_ID = 1;
    public static final int NOT_FOUND = 1000;

    public static final Vote vote1 = new Vote(VOTE1_ID, RestaurantTestData.olivka, user, LocalDate.of(2022, Month.NOVEMBER, 8));
    public static final Vote vote2 = new Vote(VOTE1_ID + 1, RestaurantTestData.kakaoMama, admin, LocalDate.of(2022, Month.NOVEMBER, 8));
    public static final Vote vote3 = new Vote(VOTE1_ID + 2, RestaurantTestData.cheshireCat, user, LocalDate.of(2022, Month.DECEMBER, 9));
    public static final Vote vote4 = new Vote(VOTE1_ID + 3, RestaurantTestData.olivka, admin, LocalDate.of(2022, Month.DECEMBER, 9));
    public static final Vote vote5 = new Vote(VOTE1_ID + 4, RestaurantTestData.kakaoMama, user, LocalDate.now().minusDays(2));
    public static final Vote vote6 = new Vote(VOTE1_ID + 5, RestaurantTestData.olivka, admin, LocalDate.now());
}
