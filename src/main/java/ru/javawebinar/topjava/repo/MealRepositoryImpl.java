package ru.javawebinar.topjava.repo;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class MealRepositoryImpl implements MealRepository {
    private Map<Long, Meal> mealMap = new ConcurrentHashMap<>();

    private AtomicLong currentId = new AtomicLong(100000L);

    public MealRepositoryImpl() {
        MealsUtil.MEALS.forEach(meal -> {
            meal.setId(currentId.incrementAndGet());
            mealMap.put(meal.getId(), meal);
        });

    }

    @Override
    public Meal createOrUpdate(Meal meal) {
        if (meal.getId() == 0) {
            meal.setId(currentId.incrementAndGet());
        }
        mealMap.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public void delete(long id) {
        mealMap.remove(id);
    }

    @Override
    public List<Meal> findAll() {
        return new ArrayList<>(mealMap.values());
    }

    @Override
    public Meal read(long id) {
        return mealMap.get(id);
    }
}
