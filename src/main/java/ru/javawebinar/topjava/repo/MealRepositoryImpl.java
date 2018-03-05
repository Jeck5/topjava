package ru.javawebinar.topjava.repo;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalTime;
import java.util.List;

public class MealRepositoryImpl implements MealRepository {
    @Override
    public void createOrUpdate(Meal meal) {
        if (MealsUtil.MEALS.stream().filter(ml -> (ml.getId() == meal.getId())).count() == 0) {
            MealsUtil.MEALS.add(meal);
        } else {
            this.delete(meal.getId());
            MealsUtil.MEALS.add(meal);
        }
    }

    @Override
    public void delete(long id) {
        MealsUtil.MEALS.removeIf(meal -> (meal.getId() == id));
    }

    @Override
    public List<MealWithExceed> findAll() {
        return MealsUtil.getFilteredWithExceededInOnePass(MealsUtil.MEALS, LocalTime.of(0, 0),
                    LocalTime.of(23, 59), MealsUtil.CALORIES_PER_DAY);
    }

    @Override
    public Meal read(long id) {
        return MealsUtil.MEALS.stream().filter(meal -> (meal.getId() == id)).findFirst().orElse(null);
    }
}
