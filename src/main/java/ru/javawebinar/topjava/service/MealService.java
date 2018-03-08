package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface MealService {
    Meal create(Meal meal, Integer userId);

    void delete(int id, Integer userId) throws NotFoundException;

    Meal get(int id, Integer userId) throws NotFoundException;

    void update(Meal meal, Integer userId);

    List<MealWithExceed> getAll(Integer userId,int caloriesPerDay);

    List<MealWithExceed> getFiltered(LocalDate startDate, LocalTime startTime,
                                     LocalDate  endDate, LocalTime endTime, Integer userId, int caloriesPerDay);
}