package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

@Controller
public class MealRestController {
    protected final Logger log = getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public List<MealTo> getAll() {
        log.info("getAll for user id={}", SecurityUtil.authUserId());
        return service.getAllTo(SecurityUtil.authUserId(), SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealTo> getAllByDateAndTime(String startDate, String endDate, String startTime, String endTime) {
        log.info("filter by date and time");

        LocalDate startLocalDate = startDate.isEmpty() ? LocalDate.MIN : LocalDate.parse(startDate);
        LocalDate endLocalDate = endDate.isEmpty() ? LocalDate.MAX : LocalDate.parse(endDate);
        LocalTime startLocalTime = startTime.isEmpty() ? LocalTime.MIN : LocalTime.parse(startTime);
        LocalTime endLocalTime = endTime.isEmpty() ? LocalTime.MAX : LocalTime.parse(endTime);

        List<Meal> meals = service.getFilteredByDate(SecurityUtil.authUserId(), startLocalDate, endLocalDate);
        return MealsUtil.getFilteredTos(meals, SecurityUtil.authUserCaloriesPerDay(), startLocalTime, endLocalTime);
    }

    public Meal get(int id) {
        log.info("get by id={}", id);
        return service.get(id, SecurityUtil.authUserId());
    }

    public void delete(int id) {
        log.info("delete id={}", id);
        service.delete(id, SecurityUtil.authUserId());
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        ValidationUtil.checkNew(meal);
        return service.create(meal, SecurityUtil.authUserId());
    }

    public void update(Meal meal, int id) {
        log.info("update by id={}, {}", id, meal);
        assureIdConsistent(meal, id);
        service.update(meal, SecurityUtil.authUserId());
    }
}