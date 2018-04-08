package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.MealTestData.*;

@ActiveProfiles("datajpa")
public class DatajpaMealServiceTest extends MealServiceTest {
    @Test
    public void getWithUser() {
        assertMatch(service.getWithUser(ADMIN_MEAL_ID, ADMIN_ID).getUser(), ADMIN);
    }
}
