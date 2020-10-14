package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    ConfigurableApplicationContext appCtx;
    private MealRestController mealRestController;

    @Override
    public void init(ServletConfig config) throws ServletException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to users");
        String action = request.getParameter("action");
        if (action.equals("setUser")) {
            int userId = Integer.parseInt(request.getParameter("userId"));
            log.info("Setting user id={}", userId);
            SecurityUtil.setAuthUserId(userId);

            request.setAttribute("action", "getAll");
            request.getRequestDispatcher("/meals").forward(request, response);
        }
        request.getRequestDispatcher("/users.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
    }
}
