package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );

        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000).forEach(System.out::println);
        System.out.println("______________________________");
        getFilteredWithExceededWithCycles(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000).forEach(System.out::println);

    }

    public static List<UserMealWithExceed> getFilteredWithExceededWithCycles(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> map = new HashMap<>();
        for (UserMeal el : mealList) {
            map.merge(el.getDateTime().toLocalDate(), el.getCalories(), (a, b) -> a + b);
        }

        List<UserMealWithExceed> list = new ArrayList<>();
        for (UserMeal el : mealList) {
            if (TimeUtil.isBetween(el.getDateTime().toLocalTime(), startTime, endTime)) {
                list.add(new UserMealWithExceed(el.getDateTime(), el.getDescription(), el.getCalories(),
                        map.get(el.getDateTime().toLocalDate()) > caloriesPerDay));
            }
        }
        return list;
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> map = new HashMap<>();
        mealList.forEach(el -> map.merge(el.getDateTime().toLocalDate(), el.getCalories(), (a, b) -> a + b));

        return mealList.stream().filter(el -> TimeUtil.isBetween(el.getDateTime().toLocalTime(), startTime, endTime))
                .map(el -> new UserMealWithExceed(el.getDateTime(), el.getDescription(), el.getCalories(),
                        map.get(el.getDateTime().toLocalDate()) > caloriesPerDay)).collect(Collectors.toList());
    }
}
