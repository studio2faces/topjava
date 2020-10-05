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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
        List sortedById = mealsTo.stream()
                .sorted(Comparator.comparingInt(MealTo::getId))
                .collect(Collectors.toList());
        request.setAttribute("mealsToList", sortedById);

        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("method");


        if (method.equals("create")) {
            log.debug("POST - Create.");

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
        } else if (method.equals("update")) {
            log.debug("POST - Update");


            request.getRequestDispatcher("edit.jsp").forward(request, response);

        } else if (method.equals("update2")) {
            int id = Integer.parseInt(request.getParameter("id"));
            String localDateTime = request.getParameter("date");
            String description = request.getParameter("description");
            int calories = Integer.parseInt(request.getParameter("calories"));
            Meal meal = new Meal(LocalDateTime.parse(localDateTime), description, calories);
            try {
                handler.update(meal, id);
                log.debug("Meal with id {} is updated.", id);
                response.sendRedirect("/topjava/meals");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (method.equals("delete")) {
            log.debug("POST - Delete.");
            int id = Integer.parseInt(request.getParameter("id"));

            try {
                handler.delete(id);
                log.debug("Meal with id {} is deleted.", id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.sendRedirect("/topjava/meals");
        }
    }

}
