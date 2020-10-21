package ru.javawebinar.topjava.service;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest extends TestCase {

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(MEAL_ID, UserTestData.USER_ID);
        MealTestData.assertMatch(meal, meal1);
    }

    @Test
    public void getNotFound() {
        Assert.assertThrows(NotFoundException.class, () -> service.get(MEAL_ID, UserTestData.NOT_FOUND));
    }

    @Test
    public void delete() {
        service.delete(MEAL_ID, UserTestData.USER_ID);
        Assert.assertThrows(NotFoundException.class, () -> service.get(MEAL_ID, UserTestData.USER_ID));
    }

    @Test
    public void deleteNotFound() {
        Assert.assertThrows(NotFoundException.class, () -> service.delete(MEAL_ID, UserTestData.NOT_FOUND));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> actual = service.getBetweenInclusive(LocalDate.of(2020, Month.JANUARY, 31), LocalDate.of(2020, Month.JANUARY, 31), UserTestData.ADMIN_ID);
        MealTestData.assertMatch(actual, mealsOfAdminBetweenInclusive);
    }

    @Test
    public void getAll() {
        List<Meal> actual = service.getAll(UserTestData.USER_ID);
        MealTestData.assertMatch(actual, mealsOfUser);
    }

    @Test
    public void update() {
        Meal updated = MealTestData.getUpdated();
        service.update(updated, USER_ID);
        Meal actual = service.get(updated.getId(), USER_ID);
        MealTestData.assertMatch(actual, updated);
    }

    @Test
    public void updateNotFound(){
        Meal updated = MealTestData.getUpdated();
        Assert.assertThrows(NotFoundException.class, () -> service.update(updated, UserTestData.NOT_FOUND));
    }

    @Test
    public void create() {
        Meal newMeal = getNew();
        Meal created = service.create(newMeal, UserTestData.USER_ID);
        Integer newId = created.getId();
        newMeal.setId(newId);
        MealTestData.assertMatch(created, newMeal);
        MealTestData.assertMatch(service.get(newId, USER_ID), newMeal);
    }
}