package ru.javawebinar.topjava.repo;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.util.List;

public interface MealRepository {
    Meal read(long id);
    Meal createOrUpdate(Meal meal);
    void delete(long id);
    List<Meal> findAll();
}
