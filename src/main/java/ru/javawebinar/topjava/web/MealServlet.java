package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    // private MealRepository repository;

    private MealRestController mealRestController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            mealRestController = appCtx.getBean(MealRestController.class);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")),
                SecurityUtil.authUserId());

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);

        if (meal.isNew()) {
            mealRestController.create(meal);
        } else {
            mealRestController.update(meal, Integer.parseInt(id));
        }
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "setUser":
                int userId = Integer.parseInt(request.getParameter("userId"));
                SecurityUtil.setAuthUserId(userId);
                request.setAttribute("meals", mealRestController.getAll());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                mealRestController.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
                final Meal newMeal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
                request.setAttribute("meal", newMeal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "update":
                int id2 = getId(request);
                final Meal meal = mealRestController.get(id2);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "filter":
                log.info("filter");
                String fromDate = request.getParameter("fromDate");
                String toDate = request.getParameter("toDate");
                String fromTime = request.getParameter("fromTime");
                String toTime = request.getParameter("toTime");

                LocalDate fromLocalDate = fromDate.isEmpty() ? LocalDate.MIN : LocalDate.parse(fromDate);
                LocalDate toLocalDate = toDate.isEmpty() ? LocalDate.MAX : LocalDate.parse(toDate);
                LocalTime fromLocalTime = fromTime.isEmpty() ? LocalTime.MIN : LocalTime.parse(fromTime);
                LocalTime toLocalTime = toTime.isEmpty() ? LocalTime.MAX : LocalTime.parse(toTime);

                request.setAttribute("meals", mealRestController.getAllByDateAndTime(fromLocalDate, toLocalDate, fromLocalTime, toLocalTime));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                request.setAttribute("meals", mealRestController.getAll());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}