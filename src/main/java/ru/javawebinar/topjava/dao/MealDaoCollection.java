package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.Util;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.slf4j.LoggerFactory.getLogger;

public class MealDaoCollection implements MealDao {
    private static final Logger log = getLogger(MealDaoCollection.class);
    private static List<Meal> meals;

    static {
        meals = Arrays.asList(
                new Meal(Util.increment(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(Util.increment(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(Util.increment(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(Util.increment(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(Util.increment(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(Util.increment(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(Util.increment(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );
    }

    private final List<Meal> safeMeals = new CopyOnWriteArrayList<>(MealDaoCollection.meals);

    @Override
    public Meal create(Meal meal) {
        safeMeals.add(meal);
        log.debug("{} is added.", meal);

        return meal;
    }

    @Override
    public List<Meal> getAll() {
        return safeMeals;
    }

    @Override
    public Meal getById(int id) {
        return safeMeals.stream()
                .filter(meal -> meal.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Meal update(Meal meal) {
        Meal current = getById(meal.getId());

        if (current == null) {
            log.debug("{} doesn't exist.", meal);
        }

        safeMeals.remove(current);
        safeMeals.add(meal);
        log.debug("{} is updated.", meal);

        return meal;
    }

    @Override
    public void delete(int id) {
        safeMeals.stream()
                .filter(meal1 -> meal1.getId() == id)
                .findFirst().ifPresent(safeMeals::remove);
        log.debug("Meal id{} is deleted", id);
    }
}
