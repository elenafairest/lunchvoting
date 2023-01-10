package ru.javaops.lunchvoting.web.menuItem;

import ru.javaops.lunchvoting.model.MenuItem;
import ru.javaops.lunchvoting.web.MatcherFactory;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class MenuItemTestData {
    public static final MatcherFactory.Matcher<MenuItem> MENU_ITEM_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(MenuItem.class, "restaurant");

    public static final int MENU_ITEM1_ID = 1;
    public static final int NOT_FOUND = 1000;

    public static final MenuItem menuItem1 = new MenuItem(MENU_ITEM1_ID, "pumpkin cream-soup", 150, LocalDate.of(2022, Month.NOVEMBER, 8));
    public static final MenuItem menuItem2 = new MenuItem(MENU_ITEM1_ID + 1, "fried salmon", 350, LocalDate.of(2022, Month.NOVEMBER, 8));
    public static final MenuItem menuItem3 = new MenuItem(MENU_ITEM1_ID + 2, "grilled cheese", 170, LocalDate.of(2022, Month.NOVEMBER, 8));
    public static final MenuItem menuItem4 = new MenuItem(MENU_ITEM1_ID + 3, "pasta carbonara", 250, LocalDate.of(2022, Month.NOVEMBER, 8));
    public static final MenuItem menuItem5 = new MenuItem(MENU_ITEM1_ID + 4, "lasagne", 280, LocalDate.of(2022, Month.NOVEMBER, 8));
    public static final MenuItem menuItem6 = new MenuItem(MENU_ITEM1_ID + 5, "grilled steak", 400, LocalDate.of(2022, Month.NOVEMBER, 8));
    public static final MenuItem menuItem7 = new MenuItem(MENU_ITEM1_ID + 6, "caesar salad", 230, LocalDate.of(2022, Month.NOVEMBER, 8));
    public static final MenuItem menuItem8 = new MenuItem(MENU_ITEM1_ID + 7, "mashroom cream-soup", 150, LocalDate.of(2022, Month.DECEMBER, 9));
    public static final MenuItem menuItem9 = new MenuItem(MENU_ITEM1_ID + 8, "fried chicken with potatoes", 250, LocalDate.of(2022, Month.DECEMBER, 9));
    public static final MenuItem menuItem10 = new MenuItem(MENU_ITEM1_ID + 9, "tuna salad", 200, LocalDate.of(2022, Month.DECEMBER, 9));
    public static final MenuItem menuItem11 = new MenuItem(MENU_ITEM1_ID + 10, "pizza margherita", 200, LocalDate.of(2022, Month.DECEMBER, 9));
    public static final MenuItem menuItem12 = new MenuItem(MENU_ITEM1_ID + 11, "caprese salad", 150, LocalDate.of(2022, Month.DECEMBER, 9));
    public static final MenuItem menuItem13 = new MenuItem(MENU_ITEM1_ID + 12, "fish and chips", 350, LocalDate.of(2022, Month.DECEMBER, 9));
    public static final MenuItem menuItem14 = new MenuItem(MENU_ITEM1_ID + 13, "greek salad", 200, LocalDate.of(2022, Month.DECEMBER, 9));
    public static final MenuItem menuItem15 = new MenuItem(MENU_ITEM1_ID + 14, "brokoli cream-soup", 150, LocalDate.now());
    public static final MenuItem menuItem16 = new MenuItem(MENU_ITEM1_ID + 15, "meatball in tomato sauce", 300, LocalDate.now());
    public static final MenuItem menuItem17 = new MenuItem(MENU_ITEM1_ID + 16, "potato salad", 150, LocalDate.now());
    public static final MenuItem menuItem18 = new MenuItem(MENU_ITEM1_ID + 17, "risotto", 270, LocalDate.now());
    public static final MenuItem menuItem19 = new MenuItem(MENU_ITEM1_ID + 18, "stuffed eggplant", 250, LocalDate.now());
    public static final MenuItem menuItem20 = new MenuItem(MENU_ITEM1_ID + 19, "hamburger", 350, LocalDate.now());
    public static final MenuItem menuItem21 = new MenuItem(MENU_ITEM1_ID + 20, "paella", 300, LocalDate.now());

    public static final List<MenuItem> menuItems = List.of(menuItem1, menuItem2, menuItem3, menuItem4, menuItem5,
            menuItem6, menuItem7, menuItem8, menuItem9, menuItem10,
            menuItem11, menuItem12, menuItem13, menuItem14, menuItem15,
            menuItem16, menuItem17, menuItem18, menuItem19, menuItem20, menuItem21);

    public static MenuItem getNew() {
        return new MenuItem(null, "New Menu Item", 100, LocalDate.now());
    }

    public static MenuItem getUpdated() {
        MenuItem updated = new MenuItem(menuItem1);
        updated.setName("UpdatedName");
        updated.setPrice(100);
        updated.setDate(LocalDate.now());
        return updated;
    }

}
