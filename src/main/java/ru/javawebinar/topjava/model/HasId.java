package ru.javawebinar.topjava.model;

public class HasId {
    public HasId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    private long id;

    public void setId(long id) {
        this.id = id;
    }
}
