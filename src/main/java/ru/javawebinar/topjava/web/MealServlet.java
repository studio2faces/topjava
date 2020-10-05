package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.implementations.MealDAOImplCollection;
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

    MealDAOImplCollection handler = new MealDAOImplCollection();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Meal> meals = handler.read();

        log.debug("Forward to meals list.");
        request.setCharacterEncoding("UTF-8");
        List<MealTo> mealsTo = MealsUtil.filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, 2000);
        request.setAttribute("mealsToList", mealsTo);

        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String localDateTime = request.getParameter("date");

        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        System.out.println(localDateTime + " " + description + " " + calories);

        Meal meal = new Meal(LocalDateTime.parse(localDateTime), description, calories);
        try {
            handler.create(meal);
            log.debug("Meal {} was added with id {}.", meal.getDescription(), meal.getId());
        } catch (Exception e) {
            log.error("Meal {} wasn't added.", meal.getDescription());
            e.printStackTrace();
        }
        response.sendRedirect("/topjava/meals");
    }
}
