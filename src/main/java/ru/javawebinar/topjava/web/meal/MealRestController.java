package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

@Controller
public class MealRestController {
    protected final Logger log = getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public List<MealTo> getAll() {
        return service.getAllTo(SecurityUtil.authUserId(), SecurityUtil.authUserCaloriesPerDay());
    }

    //запуталась пока
    //Отдать свою еду, отфильтрованную по startDate, startTime, endDate, endTime
    public List<MealTo> getAllByDateAndTime() {
        List<Meal> meals = service.getAll(SecurityUtil.authUserId());
        return null;
    }

    public MealTo get(int id) {
        return service.getTo(id, SecurityUtil.authUserId(), SecurityUtil.authUserCaloriesPerDay());
    }

    public void delete(int id) {
        service.delete(id, SecurityUtil.authUserId());
    }

    public Meal create(Meal meal) {
        return service.create(meal, SecurityUtil.authUserId());
    }

    public void update(Meal meal, int id) {
        assureIdConsistent(meal, id);
        service.update(meal, SecurityUtil.authUserId());
    }

}