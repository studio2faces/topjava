package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepository implements MealRepository {
    private final CrudMealRepository crudMealRepository;
    private final CrudUserRepository crudUserRepository;

    public DataJpaMealRepository(CrudMealRepository crudMealRepository, CrudUserRepository crudUserRepository) {
        this.crudMealRepository = crudMealRepository;
        this.crudUserRepository = crudUserRepository;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        User user = crudUserRepository.getOne(userId);
        meal.setUser(user);
        if (meal.isNew() || crudMealRepository.existsByIdAndUser(meal.getId(), user)) {
            return crudMealRepository.save(meal);
        }
        return null;
    }

    @Override
    public boolean delete(int id, int userId) {
        User user = crudUserRepository.getOne(userId);
        return crudMealRepository.deleteByIdAndUser(id, user) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        User user = crudUserRepository.getOne(userId);
        return crudMealRepository.getByIdAndUser(id, user);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudMealRepository.getAllByUserIdOrderByDateTimeDesc(userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return crudMealRepository.getBetweenHalfOpen(startDateTime, endDateTime, userId);
    }
}
