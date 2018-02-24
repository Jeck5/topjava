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

        Map<LocalDate, Integer> localDateIntegerMap = new HashMap<>();
        for (UserMeal el : mealList) {
            localDateIntegerMap.merge(el.getDateTime().toLocalDate(), el.getCalories(), Integer::sum);
        }

        List<UserMealWithExceed> userMealWithExceedList = new ArrayList<>();
        for (UserMeal el : mealList) {
            if (TimeUtil.isBetween(el.getDateTime().toLocalTime(), startTime, endTime)) {
                userMealWithExceedList.add(new UserMealWithExceed(el.getDateTime(), el.getDescription(), el.getCalories(),
                        localDateIntegerMap.get(el.getDateTime().toLocalDate()) > caloriesPerDay));
            }
        }
        return userMealWithExceedList;
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> localDateIntegerMap = mealList.stream().collect(Collectors.groupingBy(el -> el.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));
        return mealList.stream().filter(el -> TimeUtil.isBetween(el.getDateTime().toLocalTime(), startTime, endTime))
                .map(el -> new UserMealWithExceed(el.getDateTime(), el.getDescription(), el.getCalories(),
                        localDateIntegerMap.get(el.getDateTime().toLocalDate()) > caloriesPerDay)).collect(Collectors.toList());
    }
}
