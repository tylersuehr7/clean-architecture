package com.tylersuehr.cleanarchitecture.data.models;
import java.util.UUID;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 12/25/2016.
 *
 * This is the base object for any thing that we wish to persist in our repository system.
 */
public class Entity {
    private Object id; // Use an object so that we can have any kind of id that we want


    public Entity() {}

    public String toQuery() {
        return (id instanceof String || id instanceof UUID)
                ? "[id]='" + id.toString() + "'" : "[id]=" + id;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }
}