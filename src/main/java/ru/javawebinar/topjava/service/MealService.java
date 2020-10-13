package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.*;

@Service
public class MealService {

    @Autowired
    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    public Meal update(Meal meal, int userId) {
        return checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public Meal get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public MealTo getTo(int id, int userId, int calories) {
        Meal meal = get(id, userId);
        List<Meal> mealsOfThisDay = getAll(userId).stream()
                .filter(meal1 -> meal1.getDate().equals(meal.getDate()))
                .collect(Collectors.toList());

        List<MealTo> mealsOfThisDayTo = MealsUtil.getTos(mealsOfThisDay, calories);
        return mealsOfThisDayTo.stream()
                .filter(mealTo -> mealTo.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    public List<MealTo> getAllTo(int userId, int calories) {
        return MealsUtil.getTos(getAll(userId), calories);
    }


}