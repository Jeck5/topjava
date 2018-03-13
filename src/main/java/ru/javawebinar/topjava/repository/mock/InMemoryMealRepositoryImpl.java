package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> {
            save(meal, meal.getUserId());
        });
    }

    @Override
    public Meal save(Meal meal, Integer userId) {
        meal.setUserId(userId);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        } else {
            Meal oldMeal = repository.get(meal.getId());
            if (oldMeal == null) return null;
            if (!userId.equals(oldMeal.getUserId())) return null;
        }
        repository.put(meal.getId(), meal);
        return repository.get(meal.getId());
    }

    @Override
    public boolean delete(int id, Integer userId) {
        return repository.entrySet().removeIf(pair -> pair.getKey().equals(id) && pair.getValue().getUserId().equals(userId));
    }

    @Override
    public Meal get(int id, Integer userId) {
        Meal meal = repository.get(id);
        return ((meal != null) && (userId.equals(meal.getUserId()))) ? meal : null;
    }

    @Override
    public List<Meal> getAll(Integer userId) {
        return getFilteredByDate(LocalDate.MIN, LocalDate.MAX, userId);
    }

    @Override
    public List<Meal> getFilteredByDate(LocalDate startDate, LocalDate endDate, Integer userId) {
        return repository.values().stream().filter(meal ->
                meal.getUserId().equals(userId) && DateTimeUtil.isBetween(meal.getDate(), startDate, endDate))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList());
    }

}

