package com.github.elenafairest.lunchvoting.web.restaurant;

import com.github.elenafairest.lunchvoting.model.MenuItem;
import com.github.elenafairest.lunchvoting.repository.MenuItemRepository;
import com.github.elenafairest.lunchvoting.util.JsonUtil;
import com.github.elenafairest.lunchvoting.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.github.elenafairest.lunchvoting.web.restaurant.AdminMenuItemController.URL;
import static com.github.elenafairest.lunchvoting.web.restaurant.MenuItemTestData.*;
import static com.github.elenafairest.lunchvoting.web.restaurant.RestaurantTestData.KAKAO_MAMA_ID;
import static com.github.elenafairest.lunchvoting.web.restaurant.RestaurantTestData.OLIVKA_ID;
import static com.github.elenafairest.lunchvoting.web.user.UserTestData.ADMIN_MAIL;
import static com.github.elenafairest.lunchvoting.web.user.UserTestData.USER_MAIL;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminMenuItemControllerTest extends AbstractControllerTest {
    private static final String URL_SLASH = URL + "/";
    public static final String URL_KAKAO_MAMA = URL_SLASH + KAKAO_MAMA_ID + "/menu-items";
    public static final String URL_KAKAO_MAMA_MENU_ITEM1 = URL_KAKAO_MAMA + "/" + MENU_ITEM1_ID;
    public static final String URL_NOT_FOUND = URL_SLASH + RestaurantTestData.NOT_FOUND + "/menu-items";
    @Autowired
    private MenuItemRepository menuItemRepository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(URL_KAKAO_MAMA_MENU_ITEM1))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_ITEM_MATCHER.contentJson(menuItem1));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(URL_KAKAO_MAMA + "/" + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(URL_KAKAO_MAMA_MENU_ITEM1))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(URL_KAKAO_MAMA_MENU_ITEM1))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(URL_KAKAO_MAMA_MENU_ITEM1))
                .andExpect(status().isNoContent());
        assertFalse(menuItemRepository.findById(MENU_ITEM1_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(URL_KAKAO_MAMA + "/" + NOT_FOUND))
                .andExpect(status().isConflict());
    }

    @Test
    void deleteUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.delete(URL_KAKAO_MAMA_MENU_ITEM1))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void deleteForbidden() throws Exception {
        perform(MockMvcRequestBuilders.delete(URL_KAKAO_MAMA_MENU_ITEM1))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteInvalidRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.delete(URL_SLASH + OLIVKA_ID + "/menu-items/" + MENU_ITEM1_ID))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        MenuItem updated = getUpdated();
        perform(MockMvcRequestBuilders.put(URL_KAKAO_MAMA_MENU_ITEM1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        MENU_ITEM_MATCHER.assertMatch(menuItemRepository.getExisted(MENU_ITEM1_ID), updated);
    }

    @Test
    void updateUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.put(URL_KAKAO_MAMA_MENU_ITEM1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(getUpdated())))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateForbidden() throws Exception {
        perform(MockMvcRequestBuilders.put(URL_KAKAO_MAMA_MENU_ITEM1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(getUpdated())))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocation() throws Exception {
        MenuItem newRestaurant = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(URL_KAKAO_MAMA)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andExpect(status().isCreated());
        MenuItem created = MENU_ITEM_MATCHER.readFromJson(action);
        int newId = created.id();
        newRestaurant.setId(newId);
        MENU_ITEM_MATCHER.assertMatch(created, newRestaurant);
        MENU_ITEM_MATCHER.assertMatch(menuItemRepository.getExisted(newId), newRestaurant);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createNotFoundRestaurant() throws Exception {
        MenuItem newRestaurant = getNew();
        perform(MockMvcRequestBuilders.post(URL_NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andExpect(status().isConflict());
    }

    @Test
    void createWithLocationUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.post(URL_KAKAO_MAMA)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(getNew())))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createWithLocationForbidden() throws Exception {
        perform(MockMvcRequestBuilders.post(URL_KAKAO_MAMA)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(getNew())))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(URL_KAKAO_MAMA))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_ITEM_MATCHER.contentJson(menuItemsKakaoMama));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(URL_KAKAO_MAMA + "/by-date")
                .param("menuDate", String.valueOf(LocalDate.now().minusDays(2))))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_ITEM_MATCHER.contentJson(List.of(menuItem15, menuItem16, menuItem17)));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid() throws Exception {
        MenuItem invalid = new MenuItem(null, null, 0, null);
        perform(MockMvcRequestBuilders.post(URL_KAKAO_MAMA)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createDuplicate() throws Exception {
        MenuItem invalid = new MenuItem(null, menuItem1.getName(), 100, menuItem1.getMenuDate());
        perform(MockMvcRequestBuilders.post(URL_KAKAO_MAMA)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateInvalid() throws Exception {
        MenuItem invalid = new MenuItem(menuItem1);
        invalid.setName("");
        perform(MockMvcRequestBuilders.put(URL_KAKAO_MAMA_MENU_ITEM1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateHtmlUnsafe() throws Exception {
        MenuItem invalid = new MenuItem(menuItem1);
        invalid.setName("<script>alert(123)</script>");
        perform(MockMvcRequestBuilders.put(URL_KAKAO_MAMA_MENU_ITEM1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    @WithUserDetails(value = ADMIN_MAIL)
    void updateDuplicate() throws Exception {
        MenuItem invalid = new MenuItem(menuItem1);
        invalid.setName(menuItem2.getName());
        invalid.setMenuDate(menuItem2.getMenuDate());
        perform(MockMvcRequestBuilders.put(URL_KAKAO_MAMA_MENU_ITEM1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateInvalidRestaurant() throws Exception {
        MenuItem invalid = new MenuItem(menuItem1);
        perform(MockMvcRequestBuilders.put(URL_SLASH + OLIVKA_ID + "/menu-items/" + MENU_ITEM1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isConflict());
    }
}
