package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;

public class MealRestControllerTest extends AbstractControllerTest {

    private static final String REST_MEALS_URL = MealRestController.REST_MEALS_URL + "/";

    @Autowired
    private MealService mealService;


    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_MEALS_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_TO_TEST_MATCHER.contentJson(MealsUtil.getTos(meals, UserTestData.user.getCaloriesPerDay())));
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_MEALS_URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_MATCHER.contentJson(meal1));
    }

    @Test
    void createWithLocation() throws Exception {
        Meal newMeal = MealTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_MEALS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMeal)))
                .andExpect(status().isCreated());

        Meal created = TestUtil.readFromJson(action, Meal.class);
        int newId = created.id();
        newMeal.setId(newId);
        MEAL_MATCHER.assertMatch(created, newMeal);
        MEAL_MATCHER.assertMatch(mealService.get(newId, UserTestData.USER_ID), newMeal);
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_MEALS_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        Assertions.assertThrows(NotFoundException.class, () -> mealService.get(MEAL1_ID, UserTestData.USER_ID));
    }

    @Test
    void update() throws Exception {
        Meal upd = MealTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_MEALS_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(upd)))
                .andExpect(status().isNoContent());

        MEAL_MATCHER.assertMatch(mealService.get(MEAL1_ID, UserTestData.USER_ID), upd);
    }

    @Test
    void getBetween() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_MEALS_URL + "filter?startDateTime=2020-01-30T07:00&endDateTime=2020-01-31T11:00"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(MEAL_TO_TEST_MATCHER.contentJson(MealsUtil.createTo(meal5, true), MealsUtil.createTo(meal1, false)));
    }
}
