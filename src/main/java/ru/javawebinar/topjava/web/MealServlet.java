package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoCollection;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final String mealsServletPath = "meals";
    public static final int CALORIES_PER_DAY = 2000;

    private MealDao mealDao;

    @Override
    public void init() throws ServletException {
        mealDao = new MealDaoCollection();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        if (request.getParameterMap().containsKey("id")) {
            int id = getIdFromRequest(request);
            Meal existing = mealDao.getById(id);

            request.setAttribute("existing", existing);

            log.debug("POST - Update. Forward to edit.jsp");
            request.getRequestDispatcher("edit.jsp").forward(request, response);
        }

        List<Meal> meals = mealDao.getAll();
        List<MealTo> mealsTo = MealsUtil.filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);

        request.setAttribute("mealsToList", mealsTo);

        log.debug("Forward to meals list.");
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String method = request.getParameter("method");

        switch (method) {
            case "create": {
                log.debug("POST - Create.");

                String localDateTime = request.getParameter("dateTime");
                String description = request.getParameter("description");
                int calories = Integer.parseInt(request.getParameter("calories"));
                log.debug("Meal for adding: {} - {} - {} kcal.", localDateTime, description, calories);

                Meal meal = new Meal(LocalDateTime.parse(localDateTime), description, calories);

                mealDao.create(meal);
                log.debug("Meal {} was added with id {}.", meal.getDescription(), meal.getId());

                response.sendRedirect(mealsServletPath);
                break;
            }
            case "update": {
                log.debug("POST - Update. Getting updated parameters.");

                int id = getIdFromRequest(request);
                String localDateTime = request.getParameter("dateTime");
                String description = request.getParameter("description");
                int calories = Integer.parseInt(request.getParameter("calories"));

                Meal meal = new Meal(id, LocalDateTime.parse(localDateTime), description, calories);

                mealDao.update(meal);
                log.debug("Meal with ID{} is updated.", id);
                response.sendRedirect(mealsServletPath);
                break;
            }
            case "delete": {
                log.debug("POST - Delete.");
                int idToDel = getIdFromRequest(request);

                mealDao.delete(idToDel);
                log.debug("Meal with ID{} is deleted.", idToDel);

                response.sendRedirect(mealsServletPath);
                break;
            }
        }
    }

    public int getIdFromRequest(HttpServletRequest request) {
        return Integer.parseInt(request.getParameter("id"));
    }
}