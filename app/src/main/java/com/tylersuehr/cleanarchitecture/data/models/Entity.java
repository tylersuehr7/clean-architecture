package com.tylersuehr.cleanarchitecture.data.models;

/**
 * Copyright 2017 Tyler Suehr
 *
 * The base model for anything we want to store in the SQLite database.
 *
 * {@link #equals(Object)}: Overridden to check if the ids of the Entities match.
 * {@link #toString()}: Overridden to display the Entity's id.
 *
 * @author Tyler Suehr
 * @version 1.0
 */
public abstract class Entity {
    private Object id;


    @Override
    public boolean equals(Object obj) {
        return (this == obj) || (obj instanceof Entity && ((Entity)obj).id.equals(id));
    }

    @Override
    public String toString() {
        return "Entity {'" + id + "'}";
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }
}