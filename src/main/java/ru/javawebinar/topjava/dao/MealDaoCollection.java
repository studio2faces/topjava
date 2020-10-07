package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.Util;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class MealDaoCollection implements MealDao {
    private static final Logger log = getLogger(MealDaoCollection.class);

    public static final AtomicInteger count = new AtomicInteger();

    private static final Map<Integer, Meal> safeMap = new ConcurrentHashMap<Integer, Meal>();

    {
        create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    @Override
    public Meal create(Meal meal) {
        generateId(meal);
        safeMap.put(meal.getId(), meal);
        log.debug("{} is added.", meal);
        return meal;
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(safeMap.values());
    }

    @Override
    public Meal getById(int id) {
        return safeMap.get(id);
    }

    @Override
    public Meal update(Meal meal) {
        safeMap.put(meal.getId(), meal);
        log.debug("Meal id {} is updated.", meal.getId());
        return meal;
    }

    @Override
    public void delete(int id) {
        safeMap.remove(id);
        log.debug("Meal id {} is deleted", id);
    }

    private static void generateId(Meal meal) {
        meal.setId(count.incrementAndGet());
    }
}
