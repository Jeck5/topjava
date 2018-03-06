package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.repo.MealRepository;
import ru.javawebinar.topjava.repo.MealRepositoryImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    MealRepository repository = new MealRepositoryImpl();
    private static String LIST_MEAL = "/meals.jsp";
    private static String INSERT_OR_EDIT_MEAL = "/mealPage.jsp";
    private List<MealWithExceed> findAllWithExceed(List<Meal> mealList){
        return MealsUtil.getFilteredWithExceededInOnePass(mealList, LocalTime.MIN,
                    LocalTime.MAX, MealsUtil.CALORIES_PER_DAY);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to meals");
        String forwardTo = "";
        String action = request.getParameter("action");
        if (action==null) action = "";
        long mealId;
        switch (action){
            case "delete":
                mealId = Long.parseLong(request.getParameter("id"));
                repository.delete(mealId);
                response.sendRedirect("meals");
                break;
            case "edit":
                forwardTo = INSERT_OR_EDIT_MEAL;
                mealId = Long.parseLong(request.getParameter("id"));
                Meal meal = repository.read(mealId);
                if (meal==null) {meal = new Meal();}
                request.setAttribute("meal", meal);
                request.getRequestDispatcher(forwardTo).forward(request, response);
                break;
            default:
                forwardTo = LIST_MEAL;
                request.setAttribute("mealsWithExceed", findAllWithExceed(repository.findAll()));
                request.getRequestDispatcher(forwardTo).forward(request, response);
                break;
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forwardTo = LIST_MEAL;
        request.setCharacterEncoding("UTF-8");
        log.debug("from doPost");

        log.debug(request.getParameter("mealid"));
        log.debug(request.getParameter("calories"));
        log.debug(request.getParameter("description"));//TO DO encoding letters fail
        log.debug(request.getParameter("datetime"));

        Meal meal = new Meal();
        meal.setId(Long.parseLong(request.getParameter("mealid")));
        meal.setCalories(Integer.parseInt(request.getParameter("calories")));
        meal.setDescription(request.getParameter("description"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("datetime"), formatter);
        meal.setDateTime(dateTime);
        repository.createOrUpdate(meal);
        request.setAttribute("mealsWithExceed", findAllWithExceed(repository.findAll()));
        request.getRequestDispatcher(forwardTo).forward(request, response);
    }
}
