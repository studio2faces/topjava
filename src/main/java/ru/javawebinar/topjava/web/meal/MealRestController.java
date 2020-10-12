package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.ValidationUtil.*;

@Controller
public class MealRestController {
    protected final Logger log = getLogger(MealRestController.class);

    @Autowired
    @Qualifier("mealService")
    private MealService service;


    //Отдать свою еду (для отображения в таблице, формат List<MealTo>), запрос БЕЗ параметров
    public List<MealTo> getAll() {
        List<Meal> meals = service.getAll(SecurityUtil.authUserId());
        return MealsUtil.getTos(meals, SecurityUtil.authUserCaloriesPerDay());
    }

    //запуталась пока
    //Отдать свою еду, отфильтрованную по startDate, startTime, endDate, endTime
    public List<MealTo> getAllByDateAndTime() {
        List<Meal> meals = service.getAll(SecurityUtil.authUserId());
        return null;
    }

    //  Отдать/удалить свою еду по id, параметр запроса - id еды. Если еда с этим id чужая или отсутствует - NotFoundException
    public MealTo get(int id) {
        Meal meal = service.get(id, SecurityUtil.authUserId());
        return null;
    }

    public void delete(int id) {
        service.delete(id, SecurityUtil.authUserId());
    }

    //Сохранить/обновить еду, параметр запроса - Meal. Если обновляемая еда с этим id чужая или отсутствует - NotFoundException
    public Meal create(Meal meal) {
        return service.create(meal, SecurityUtil.authUserId());
    }

    //В концепции REST при update дополнительно принято передавать id (см. AdminRestController.update)
    public void update(Meal meal, int id) {
        assureIdConsistent(meal, id);
        service.update(meal, SecurityUtil.authUserId());
    }


}