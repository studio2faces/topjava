package ru.javawebinar.topjava.dao.implementations;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.slf4j.LoggerFactory.getLogger;

public class MealDAOImplCollection implements MealDAO {
    private static final Logger log = getLogger(MealDAOImplCollection.class);
    private static List<Meal> meals;

    static {
        meals = Arrays.asList(
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );
    }

    private final List<Meal> safeMeals = new CopyOnWriteArrayList<>(MealDAOImplCollection.meals);

    @Override
    public void create(Meal meal) {
        safeMeals.add(meal);
    }

    @Override
    public List<Meal> read() {
        return safeMeals;
    }

    @Override
    public void update(Meal meal, int id) {
        Meal current = safeMeals.stream()
                .filter(meal1 -> meal1.getId() == id)
                .findFirst()
                .orElse(null);

        if (current != null) {
            safeMeals.remove(current);
            safeMeals.add(meal);
        } else {
            throw new NullPointerException("Meal doesn't exist.");
        }
    }

    @Override
    public void delete(int id) {
        safeMeals.stream()
                .filter(meal1 -> meal1.getId() == id)
                .findFirst().ifPresent(safeMeals::remove);

    }
}
