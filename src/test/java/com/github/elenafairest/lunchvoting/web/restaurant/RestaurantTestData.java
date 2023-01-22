package com.github.elenafairest.lunchvoting.web.restaurant;

import com.github.elenafairest.lunchvoting.model.Restaurant;
import com.github.elenafairest.lunchvoting.web.MatcherFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "menu");
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_WITH_MENU_MATCHER = MatcherFactory.usingAssertions(Restaurant.class,
            (a, e) -> assertThat(a).usingRecursiveComparison()
                    .ignoringFields("menu.restaurant").isEqualTo(e),
            (a, e) -> assertThat(a).usingRecursiveFieldByFieldElementComparatorIgnoringFields("menu.restaurant").isEqualTo(e)
    );

    public static final int KAKAO_MAMA_ID = 1;
    public static final int OLIVKA_ID = 2;
    public static final int CHESHIRE_CAT_ID = 3;

    public static final int NOT_FOUND = 1000;

    public static final Restaurant kakaoMama = new Restaurant(KAKAO_MAMA_ID, "KakaoMama", "+7 8483 201-221, shop@kakaomama.ru");
    public static final Restaurant olivka = new Restaurant(OLIVKA_ID, "Olivka", "olivks-tlt@mail.ru");
    public static final Restaurant cheshireCat = new Restaurant(CHESHIRE_CAT_ID, "Cheshire Cat", "8 (848) 236-25-03");

    static {
        olivka.setMenu(List.of(MenuItemTestData.menuItem18, MenuItemTestData.menuItem19));
        cheshireCat.setMenu(List.of(MenuItemTestData.menuItem20, MenuItemTestData.menuItem21));
    }

    public static Restaurant getNew() {
        return new Restaurant(null, "New Restaurant", "New Contact Information Value");
    }

    public static Restaurant getUpdated() {
        Restaurant updated = new Restaurant(kakaoMama);
        updated.setName("UpdatedName");
        updated.setContactInfo("Updated Contact Info Value");
        return updated;
    }
}
