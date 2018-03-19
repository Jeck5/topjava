package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_START_ID = START_SEQ + 2;
    //MEAL1..4 is considered for USER; 5..6 for ADMIN
    public static final Meal MEAL1 = new Meal(MEAL_START_ID, LocalDateTime.parse("2018-03-17T10:00:00"), "завтрак", 500);
    public static final Meal MEAL2 = new Meal(MEAL_START_ID + 1, LocalDateTime.parse("2018-03-17T14:00:00"), "обед", 1000);
    public static final Meal MEAL3 = new Meal(MEAL_START_ID + 2, LocalDateTime.parse("2018-03-17T20:00:00"), "ужин", 501);
    public static final Meal MEAL4 = new Meal(MEAL_START_ID + 3, LocalDateTime.parse("2018-03-16T10:00:00"), "завтрак", 500);
    public static final Meal MEAL5 = new Meal(MEAL_START_ID + 4, LocalDateTime.parse("2018-03-14T09:40:00"), "завтрак", 1501);
    public static final Meal MEAL6 = new Meal(MEAL_START_ID + 5, LocalDateTime.parse("2018-03-14T14:30:00"), "обед", 500);
    public static final LocalDateTime LOCAL_DATE_TIME_FROM = LocalDateTime.parse("2018-03-16T09:00:00");
    public static final LocalDateTime LOCAL_DATE_TIME_TO =  LocalDateTime.parse("2018-03-17T11:00:00");

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(Stream.of(expected).sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList()));
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }
}