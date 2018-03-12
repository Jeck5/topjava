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
        List<Integer> userIdList = new LinkedList<Integer>(Arrays.asList(1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 0, 0));
        MealsUtil.MEALS.forEach(meal -> {
            meal.setUserId(userIdList.remove(0));
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

        Meal oldMeal = repository.get(meal.getId());
        if (oldMeal == null) return null;
        if (!meal.getUserId().equals(repository.get(meal.getId()).getUserId())) return null;
        return repository.put(meal.getId(), meal);
    }

    @Override
    public boolean delete(int id, Integer userId) {
        Meal meal = repository.remove(id);
        return ((meal != null) && (userId.equals(meal.getUserId())));
    }

    @Override
    public Meal get(int id, Integer userId) {
        Meal meal = repository.get(id);
        return ((meal != null) && (userId.equals(meal.getUserId()))) ? meal : null;
    }

    @Override
    public List<Meal> getAll(Integer userId) {
        return repository.values().stream().filter(meal -> meal.getUserId().equals(userId)).sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList());
    }

    @Override
    public List<Meal> getFilteredByDate(LocalDate startDate, LocalDate endDate, Integer userId) {
        return repository.values().stream().filter(meal ->
                meal.getUserId().equals(userId) && DateTimeUtil.isBetween(meal.getDate(), startDate, endDate))
                .sorted(Comparator.comparing(Meal::getDateTime)).collect(Collectors.toList());
    }

}

