package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.Util;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

public class MealDaoCollection implements MealDao {
    private static final Logger log = getLogger(MealDaoCollection.class);

    private static final MealDao mealDao = new MealDaoCollection();
    private static final Map<Integer, Meal> safeMap = new ConcurrentHashMap<>();

    static {
        mealDao.create(new Meal(Util.increment(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        mealDao.create(new Meal(Util.increment(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        mealDao.create(new Meal(Util.increment(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        mealDao.create(new Meal(Util.increment(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        mealDao.create(new Meal(Util.increment(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        mealDao.create(new Meal(Util.increment(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        mealDao.create(new Meal(Util.increment(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    @Override
    public Meal create(Meal meal) {
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
        return safeMap.getOrDefault(id, null);
    }

    @Override
    public Meal update(Meal meal) {
        Meal current = getById(meal.getId());

        if (current == null) {
            log.debug("{} doesn't exist.", meal);
        }

        safeMap.put(meal.getId(), meal);
        log.debug("{} is updated.", meal);

        return meal;
    }

    @Override
    public void delete(int id) {
        safeMap.remove(id);
        log.debug("Meal id{} is deleted", id);
    }
}
