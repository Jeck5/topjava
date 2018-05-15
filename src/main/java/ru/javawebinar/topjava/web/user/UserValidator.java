package ru.javawebinar.topjava.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Locale;

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserTo.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (target instanceof UserTo) {
            UserTo userTo = (UserTo) target;
            try {
                User user = userService.getByEmail(userTo.getEmail());
                if (user != null && !user.getId().equals(userTo.getId()))
                    errors.rejectValue("email", "user.duplicated", messageSource.getMessage("user.duplicated", null, LocaleContextHolder.getLocale()));
            } catch (NotFoundException e) {
            }
        }

    }
}
