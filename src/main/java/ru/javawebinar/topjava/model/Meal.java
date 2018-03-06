package ru.javawebinar.topjava.model;

import ru.javawebinar.topjava.util.IdGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Meal extends HasId{
    private LocalDateTime dateTime;

    private String description;

    private int calories;

    public Meal(){
        super(0);
//        this(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),"",0);
        this.setDateTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        this.setDescription("");
        this.setCalories(0);
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        super(IdGenerator.getInstance().generate());
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }
}
