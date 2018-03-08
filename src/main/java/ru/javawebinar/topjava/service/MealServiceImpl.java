package ru.javawebinar.topjava.service;

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

public class MealServiceImpl implements MealService {

    private /*final*/ MealRepository repository;

    @Override
    public Meal create(Meal meal, Integer userId) {
        return repository.save(meal,userId);
    }

    @Override
    public void delete(int id, Integer userId) throws NotFoundException {
        if (!repository.delete(id, userId)) throw new NotFoundException(String.format("Meal %d wasn't found for user %d",id,userId));
    }

    @Override
    public Meal get(int id, Integer userId) throws NotFoundException {
        Meal meal = repository.get(id,userId);
        if (meal == null) throw new NotFoundException(String.format("Meal %d wasn't found for user %d",id,userId));
        return meal;
    }

    @Override
    public void update(Meal meal, Integer userId) {
        repository.save(meal,userId);
    }

    @Override
    public List<MealWithExceed> getAll(Integer userId,int caloriesPerDay) {
        return MealsUtil.getWithExceeded(repository.getAll(userId),caloriesPerDay);
    }

    @Override
    public List<MealWithExceed> getFiltered(LocalDate startDate, LocalTime startTime,
                                            LocalDate endDate, LocalTime endTime, Integer userId, int caloriesPerDay) {
        return getAll(userId,caloriesPerDay).stream().filter(
                meal -> DateTimeUtil.isBetween(meal.getDateTime().toLocalDate(),startDate,endDate) &&
                        DateTimeUtil.isBetween(meal.getDateTime().toLocalTime(),startTime,endTime)).collect(Collectors.toList());
        //TODO try with generics

    }
}