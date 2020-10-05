package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDAO {
    void create(Meal meal);

    List<Meal> read();

    void update(Meal meal, int id);

    void delete(int id);
}
