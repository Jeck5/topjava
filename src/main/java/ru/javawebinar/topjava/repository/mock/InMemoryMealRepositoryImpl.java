package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
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
            meal.setUserId((meal.getDateTime().getYear() % 5) + 1);
            save(meal);
        });
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id) {
        return (repository.remove(id) != null);
    }

    @Override
    public Meal get(int id) {
        return repository.get(id);
    }

    @Override
    public List<Meal> getAll(Integer userId) {
        return repository.values().stream().filter(meal -> meal.getUserId().equals(userId)).sorted(Comparator.comparing(Meal::getDateTime)).collect(Collectors.toList());
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(repository.values());
    }

    @Override
    public List<Meal> getFilteredByDate(LocalDate startDate, LocalDate endDate, Integer userId) {
        return repository.values().stream().filter(meal ->
                meal.getUserId().equals(userId) && DateTimeUtil.isBetween(meal.getDate(), startDate, endDate))
                .sorted(Comparator.comparing(Meal::getDateTime)).collect(Collectors.toList()); //TODO check duplication
    }
}

