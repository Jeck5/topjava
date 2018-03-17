package ru.javawebinar.topjava.service;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;


import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceImplTest {
    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(MEAL_START_ID + 1, USER_ID);
        assertThat(meal).isEqualTo(MEAL2);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        Meal meal = service.get(MEAL_START_ID + 2, ADMIN_ID);
    }

    @Test
    public void delete() {
        service.delete(MEAL_START_ID + 1, USER_ID);
        assertMatch(service.getAll(USER_ID), MEAL1, MEAL3, MEAL4);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        service.delete(MEAL_START_ID + 1, ADMIN_ID);
    }

    @Test
    public void getBetweenDateTimes() {
        assertMatch(service.getBetweenDateTimes(LOCAL_DATE_TIME_FROM, LOCAL_DATE_TIME_TO, USER_ID), MEAL1, MEAL4);
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(USER_ID), MEAL1, MEAL2, MEAL3, MEAL4);
    }

    @Test
    public void update() {
        Meal meal = new Meal(MEAL3.getId(), LocalDateTime.now(), "for test", 1111);
        service.update(meal, USER_ID);
        assertThat(meal).isEqualTo(service.get(MEAL3.getId(), USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() {
        Meal meal = new Meal(MEAL3.getId(), LocalDateTime.now(), "for test", 1111);
        service.update(meal, ADMIN_ID);
    }

    @Test
    public void create() {
        Meal meal = service.create(new Meal(LocalDateTime.now(), "for test", 1111), USER_ID);
        assertMatch(service.getAll(USER_ID), MEAL1, MEAL2, MEAL3, MEAL4, meal);
    }
}