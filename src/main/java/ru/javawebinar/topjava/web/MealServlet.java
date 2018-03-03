package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet  extends HttpServlet
{
    private static final Logger log = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to meals");

        List<MealWithExceed> mealWithExceedList = MealsUtil.getFilteredWithExceededInOnePass(MealsUtil.MEALS, LocalTime.of(0, 0),
                LocalTime.of(23, 59),MealsUtil.CALORIES_PER_DAY);

        request.setAttribute("mealsWithExceed", mealWithExceedList);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
