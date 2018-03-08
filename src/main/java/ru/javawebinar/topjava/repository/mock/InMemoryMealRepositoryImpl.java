package ru.javawebinar.topjava.repository.mock;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> {
            meal.setUserId(1);
            save(meal, 1);});  //TODO check
    }

    @Override
    public Meal save(Meal meal, Integer userId) {
        if (meal == null) { return null; }
        else if (!meal.getUserId().equals(userId)) {return null; }
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, Integer userId) {
        Meal meal = repository.get(id);
        if (meal == null) { return false; }
        else if (!meal.getUserId().equals(userId)) {return false; }
        repository.remove(id);
        return true;
    }

    @Override
    public Meal get(int id, Integer userId) {
        Meal meal = repository.get(id);
        if (meal == null) { return null; }
        else if (!meal.getUserId().equals(userId)) {return null; }
        return meal;
    }

    @Override
    public Collection<Meal> getAll(Integer userId) {
        return repository.values().stream().filter(meal -> meal.getUserId().equals(userId)).sorted(Comparator.comparing(Meal::getDateTime)).collect(Collectors.toList());
    }
}

