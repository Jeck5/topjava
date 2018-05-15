package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.List;

@Component
public class MealValidator implements Validator {

    @Autowired
    private MealService mealService;

    @Autowired
    private MessageSource messageSource;

    @Override

    public boolean supports(Class<?> clazz) {
        return Meal.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (target instanceof Meal) {
            Meal meal = (Meal) target;
            if (meal.getDateTime() != null) {
                List<Meal> mealList = mealService.getBetweenDateTimes(meal.getDateTime(), meal.getDateTime(), AuthorizedUser.id());
                if (mealList.stream().anyMatch(m -> !m.getId().equals(meal.getId()))) {
                    errors.rejectValue("dateTime", "meal.duplicated", messageSource.getMessage("meal.duplicated", null, LocaleContextHolder.getLocale()));
                }
            }
        }
    }
}