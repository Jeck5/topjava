package ru.javawebinar.topjava.repo;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class MealRepositoryImpl implements MealRepository {
    private List<Meal> MEALS = new CopyOnWriteArrayList<>(Arrays.asList(
            new Meal(LocalDateTime.of(2015, Month.MAY, 29, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 29, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 29, 20, 0), "Ужин", 400),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
    ));
    private AtomicLong currentId = new AtomicLong(100000L);

    {
        this.MEALS.forEach(meal -> meal.setId(currentId.incrementAndGet()));
    }


    @Override
    public Meal createOrUpdate(Meal meal) {
        if (meal.getId() == 0) {
            meal.setId(currentId.incrementAndGet());
        }

        if (this.MEALS.stream().filter(ml -> (ml.getId() == meal.getId())).count() == 0) {
            this.MEALS.add(meal);
        } else {
            this.delete(meal.getId());
            this.MEALS.add(meal);
        }
        return meal;
    }

    @Override
    public void delete(long id) {
        this.MEALS.removeIf(meal -> (meal.getId() == id));
    }

    @Override
    public List<Meal> findAll() {
        return this.MEALS;
    }

    @Override
    public Meal read(long id) {
        return this.MEALS.stream().filter(meal -> (meal.getId() == id)).findFirst().orElse(null);
    }
}
