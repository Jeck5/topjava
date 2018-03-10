package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private MealService service;

    @Autowired
    public void setService(MealService service) {
        this.service = service;
    }

    public List<MealWithExceed> getAll() {
        log.info("getAll");
        return service.getAll(AuthorizedUser.id(),AuthorizedUser.getCaloriesPerDay());
    }

    public List<MealWithExceed> getFiltered(LocalDate startDate, LocalTime startTime,
                                            LocalDate  endDate,LocalTime endTime) {
        log.info("getFiltered");
        return service.getFiltered(startDate, startTime, endDate, endTime, AuthorizedUser.id(),AuthorizedUser.getCaloriesPerDay());
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id,AuthorizedUser.id());
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        meal.setUserId(AuthorizedUser.id());
        return service.createOrUpdate(meal,AuthorizedUser.id(),false);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id,AuthorizedUser.id());
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        meal.setUserId(AuthorizedUser.id());
        service.createOrUpdate(meal, AuthorizedUser.id(),true);
    }

}