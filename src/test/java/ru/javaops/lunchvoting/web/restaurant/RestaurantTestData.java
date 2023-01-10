package ru.javaops.lunchvoting.web.restaurant;

import ru.javaops.lunchvoting.model.Restaurant;
import ru.javaops.lunchvoting.web.MatcherFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javaops.lunchvoting.web.menuItem.MenuItemTestData.*;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "menu");
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_WITH_MENU_MATCHER = MatcherFactory.usingAssertions(Restaurant.class,
            (a, e) -> assertThat(a).usingRecursiveComparison()
                    .ignoringFields("menu.restaurant").isEqualTo(e),
            (a, e) -> assertThat(a).usingRecursiveFieldByFieldElementComparatorIgnoringFields("menu.restaurant"));

    public static final int KAKAO_MAMA_ID = 1;
    public static final int OLIVKA_ID = 2;
    public static final int CHESHIRE_CAT_ID = 3;

    public static final int NOT_FOUND = 1000;

    public static final Restaurant kakaoMama = new Restaurant(KAKAO_MAMA_ID, "KakaoMama", "+7 8483 201-221, shop@kakaomama.ru");
    public static final Restaurant olivka = new Restaurant(OLIVKA_ID, "Olivka", "olivks-tlt@mail.ru");
    public static final Restaurant cheshireCat = new Restaurant(CHESHIRE_CAT_ID, "Cheshire Cat", "8 (848) 236-25-03");

    static {
        kakaoMama.setMenu(List.of(menuItem1, menuItem2, menuItem3, menuItem8, menuItem9, menuItem10, menuItem15, menuItem16, menuItem17));
        olivka.setMenu(List.of(menuItem4, menuItem5, menuItem11, menuItem12, menuItem18, menuItem19));
        cheshireCat.setMenu(List.of(menuItem6, menuItem7, menuItem13, menuItem14, menuItem20, menuItem21));
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
