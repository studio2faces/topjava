package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoCollection;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.Util;

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
    private static final String MEALSERVLET_URL = "/topjava/meals";

    MealDao mealDao = new MealDaoCollection();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        List<Meal> meals = mealDao.getAll();
        List<MealTo> mealsTo = MealsUtil.filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, 2000);

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

                String localDateTime = request.getParameter("date");
                String description = request.getParameter("description");
                int calories = Integer.parseInt(request.getParameter("calories"));
                log.debug("Meal for adding: {} - {} - {} kcal.", localDateTime, description, calories);

                Meal meal = new Meal(Util.increment(), LocalDateTime.parse(localDateTime), description, calories);

                try {
                    mealDao.create(meal);
                    log.debug("Meal {} was added with id {}.", meal.getDescription(), meal.getId());
                } catch (Exception e) {
                    log.error("Meal {} wasn't added.", meal.getDescription(), e);
                }
                response.sendRedirect(MEALSERVLET_URL);
                break;
            }
            case "update":
                log.debug("POST - Update");
                request.getRequestDispatcher("edit.jsp").forward(request, response);
                break;
            case "update-to-edit": {
                int id = Integer.parseInt(request.getParameter("id"));

                String localDateTime = request.getParameter("date");
                String description = request.getParameter("description");
                int calories = Integer.parseInt(request.getParameter("calories"));

                Meal meal = new Meal(id, LocalDateTime.parse(localDateTime), description, calories);

                try {
                    mealDao.update(meal);
                    log.debug("Meal with ID{} is updated.", id);
                    response.sendRedirect(MEALSERVLET_URL);
                } catch (Exception e) {
                    log.error("Meal with ID{} is not updated.", id, e);
                }
                break;
            }
            case "delete": {
                log.debug("POST - Delete.");
                int id = Integer.parseInt(request.getParameter("id"));

                try {
                    mealDao.delete(id);
                    log.debug("Meal with ID{} is deleted.", id);
                } catch (Exception e) {
                    log.error("Meal ID{} wasn't deleted.", id, e);
                }
                response.sendRedirect(MEALSERVLET_URL);
                break;
            }
        }
    }

}
