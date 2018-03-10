package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealServiceImpl implements MealService {


    private final MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal createOrUpdate(Meal meal, Integer userId, boolean update) throws NotFoundException {
        checkNotFound(meal, "meal is null");
        if (!userId.equals(meal.getUserId())) {
            throw new NotFoundException(String.format("Meal %d wasn't found for user %d", meal.getId(), userId));
        }
        if (update) {
            return checkNotFoundWithId(repository.save(meal), meal.getId());
        } else {
            return repository.save(meal);
        }
    }

    @Override
    public void delete(int id, Integer userId) throws NotFoundException {
        get(id,userId);
        repository.delete(id);
    }

    @Override
    public Meal get(int id, Integer userId) throws NotFoundException {
        Meal meal = checkNotFoundWithId(repository.get(id), id);
        if (!userId.equals(meal.getUserId())) {
            throw new NotFoundException(String.format("Meal %d wasn't found for user %d", id, userId));
        }
        return meal;
    }

    @Override
    public List<MealWithExceed> getAll(Integer userId, int caloriesPerDay) {
        return MealsUtil.getWithExceeded(repository.getAll(userId), caloriesPerDay);
    }

    @Override
    public List<MealWithExceed> getFiltered(LocalDate startDate, LocalTime startTime,
                                            LocalDate endDate, LocalTime endTime, Integer userId, int caloriesPerDay) {
        return MealsUtil.getWithExceeded(repository.getFilteredByDate(startDate, endDate, userId), caloriesPerDay)
                .stream().filter(meal -> DateTimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)).collect(Collectors.toList());

    }
}