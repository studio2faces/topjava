package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {
    public static final int MEAL_ID = 100002;
    public static final Meal meal1 = new Meal(MEAL_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Breakfast", 500);
    public static final Meal meal2 = new Meal(100003, LocalDateTime.of(2020, Month.JANUARY, 30, 14, 0), "Lunch", 1000);
    public static final Meal meal3 = new Meal(100004, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Dinner", 500);

    public static final Meal meal4 = new Meal(100006, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Night meal", 50);
    public static final Meal meal5 = new Meal(100007, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Breakfast", 500);
    public static final Meal meal6 = new Meal(100008, LocalDateTime.of(2020, Month.JANUARY, 31, 12, 0), "Brunch", 200);
    public static final Meal meal7 = new Meal(100009, LocalDateTime.of(2020, Month.JANUARY, 31, 14, 0), "Lunch", 1000);
    public static final Meal meal8 = new Meal(100010, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Dinner", 500);

    public static List<Meal> mealsOfUser = Arrays.asList(
            meal3,
            meal2,
            meal1);

    public static List<Meal> mealsOfAdminBetweenInclusive = Arrays.asList(
            meal8,
            meal7,
            meal6,
            meal5,
            meal4
    );

    public static Meal getNew() {
        return new Meal(LocalDateTime.now(), "Test description", 1000);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(meal1);
        updated.setDateTime(LocalDateTime.of(2020, Month.JANUARY, 15, 20, 0));
        updated.setDescription("new desc");
        updated.setCalories(2000);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingDefaultComparator().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("user_id").isEqualTo(expected);
    }
}