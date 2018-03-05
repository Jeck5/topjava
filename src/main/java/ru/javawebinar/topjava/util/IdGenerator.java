package ru.javawebinar.topjava.util;

import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {
    private static IdGenerator ourInstance = new IdGenerator();

    private AtomicLong current = new AtomicLong(100000L);

    public long generate(){
        return current.incrementAndGet();
    }

    public static IdGenerator getInstance() {
        return ourInstance;
    }

    private IdGenerator() {
    }
}
