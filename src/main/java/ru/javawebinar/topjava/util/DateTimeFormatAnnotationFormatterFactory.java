package ru.javawebinar.topjava.util;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DateTimeFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<CustomDateTimeFormat> {

    @Override
    public Set<Class<?>> getFieldTypes() {
        return new HashSet<>(Arrays.asList(LocalDate.class, LocalTime.class));
    }

    @Override
    public Printer<?> getPrinter(CustomDateTimeFormat annotation, Class<?> fieldType) {
        return getFormatter(fieldType);
    }

    @Override
    public Parser<?> getParser(CustomDateTimeFormat annotation, Class<?> fieldType) {
        return getFormatter(fieldType);
    }

    private Formatter getFormatter(Class<?> fieldType) {
        if (fieldType == LocalDate.class) {
            return new DateFormatter();
        } else if (fieldType == LocalTime.class) {
            return new TimeFormatter();
        } else throw new UnsupportedOperationException();
    }

    private class DateFormatter implements Formatter<LocalDate> {

        @Override
        public LocalDate parse(String text, Locale locale) throws ParseException {
            if (text.length() == 0) {
                return null;
            } else {
                return LocalDate.parse(text);
            }
        }

        @Override
        public String print(LocalDate object, Locale locale) {
            return object.format(DateTimeFormatter.ISO_LOCAL_DATE);
        }
    }

    private class TimeFormatter implements Formatter<LocalTime> {

        @Override
        public LocalTime parse(String text, Locale locale) throws ParseException {
            text.getClass().getAnnotations();
            if (text.length() == 0) {
                return null;
            } else {
                return LocalTime.parse(text);
            }
        }

        @Override
        public String print(LocalTime object, Locale locale) {
            return object.format(DateTimeFormatter.ISO_LOCAL_TIME);
        }
    }

}
