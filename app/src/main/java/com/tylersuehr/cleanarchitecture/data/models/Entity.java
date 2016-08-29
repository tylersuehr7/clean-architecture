package com.tylersuehr.cleanarchitecture.data.models;
import java.util.UUID;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 8/28/2016.
 *
 * This is the base class for all of our models that we want
 * to store in some kind of database.
 *
 * NOTE: All of our models will have a UUID for its primary key in
 *       its table.
 */
public class Entity {
    protected UUID id;


    public Entity() {
        this.id = UUID.randomUUID();
    }

    @Override
    public String toString() {
        // Return SQL query for convenience
        return "[id]='" + id.toString() + "'";
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setId(String id) {
        this.id = UUID.fromString(id);
    }
}