package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Meal;

import java.util.Comparator;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.MealTestData.*;

@ActiveProfiles("datajpa")
public class DatajpaUserServiceTest extends UserServiceTest {
    @Test
    public void getWithMeals() {
        assertMatch(service.get(USER_ID).getMeals().stream().sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList()),
                MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }
}
