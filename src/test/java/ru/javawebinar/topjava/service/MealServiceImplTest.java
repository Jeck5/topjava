package ru.javawebinar.topjava.service;

import org.junit.Assert;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;

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
        Meal meal = service.get(MealTestData.MEAL_START_ID+1, UserTestData.USER_ID);
        assertThat(meal).isEqualTo(MEAL2);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        Meal meal = service.get(MealTestData.MEAL_START_ID+2, UserTestData.ADMIN_ID);
    }

    @Test
    public void delete() {
        service.delete(MealTestData.MEAL_START_ID+1, UserTestData.USER_ID);
        assertThat(service.getAll(UserTestData.USER_ID)).isEqualTo(Stream.of(MEAL1,MEAL3,MEAL4)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList()));
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        service.delete(MealTestData.MEAL_START_ID+1, UserTestData.ADMIN_ID);
    }

    @Test
    public void getBetweenDateTimes() { //todo contants & common logic
        List<Meal> mealList = service.getBetweenDateTimes( LocalDateTime.parse("2018-03-16T09:00:00"),
                LocalDateTime.parse("2018-03-17T11:00:00"),UserTestData.USER_ID);
        assertThat(mealList).isEqualTo(Stream.of(MEAL1,MEAL2,MEAL3,MEAL4)
                .filter(meal -> DateTimeUtil.isBetween(meal.getDateTime(),LocalDateTime.parse("2018-03-16T09:00:00"),
                        LocalDateTime.parse("2018-03-17T11:00:00")))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList()));
    }

    @Test
    public void getAll() {
        List<Meal> mealList = service.getAll(UserTestData.USER_ID); //TODO delete duplication
        assertThat(mealList).isEqualTo(Stream.of(MEAL1,MEAL2,MEAL3,MEAL4)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList()));
    }

    @Test
    public void update() {
        Meal meal = new Meal(MEAL3.getId(),LocalDateTime.now(),"for test",1111);
        service.update(meal,UserTestData.USER_ID);
        assertThat(meal).isEqualTo(service.get(MEAL3.getId(),UserTestData.USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() {
        Meal meal = new Meal(MEAL3.getId(),LocalDateTime.now(),"for test",1111);
        service.update(meal,UserTestData.ADMIN_ID);
    }

    @Test
    public void create() {
        Meal meal = service.create(new Meal(LocalDateTime.now(),"for test",1111),UserTestData.USER_ID);
        List<Meal> mealList = service.getAll(UserTestData.USER_ID); //TODO delete duplication
        assertThat(mealList).isEqualTo(Stream.of(MEAL1,MEAL2,MEAL3,MEAL4,meal)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList()));
    }
}