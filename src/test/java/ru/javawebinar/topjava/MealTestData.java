package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_START_ID = START_SEQ + 2;
    public static final Meal MEAL1 = new Meal(MEAL_START_ID, LocalDateTime.parse("2018-03-17T10:00:00"), "завтрак", 500);
    public static final Meal MEAL2 = new Meal(MEAL_START_ID + 1, LocalDateTime.parse("2018-03-17T14:00:00"), "обед", 1000);
    public static final Meal MEAL3 = new Meal(MEAL_START_ID + 2, LocalDateTime.parse("2018-03-17T20:00:00"), "ужин", 501);
    public static final Meal MEAL4 = new Meal(MEAL_START_ID + 3, LocalDateTime.parse("2018-03-16T10:00:00"), "завтрак", 500);
    public static final Meal MEAL5 = new Meal(MEAL_START_ID + 4, LocalDateTime.parse("2018-03-14T09:40:00"), "завтрак", 1501);
    public static final Meal MEAL6 = new Meal(MEAL_START_ID + 5, LocalDateTime.parse("2018-03-14T14:30:00"), "обед", 500);
    int usersForMeals[] = {UserTestData.USER_ID, UserTestData.USER_ID,UserTestData.USER_ID,UserTestData.USER_ID,
            UserTestData.ADMIN_ID,UserTestData.ADMIN_ID,};

    public static void main(String[] args) {
        System.out.println(MEAL2);
    }
}