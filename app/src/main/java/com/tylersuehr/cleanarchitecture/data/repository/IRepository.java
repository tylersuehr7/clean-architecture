package com.tylersuehr.cleanarchitecture.data.repository;
import com.tylersuehr.cleanarchitecture.data.models.Entity;
import java.util.Collection;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 8/28/2016.
 *
 * This is our generic CRUD functionality. It can be used with anything
 * that intends to manipulate some kind of data storage.
 * EX: Local, Cloud, Disk...etc
 */
public interface IRepository<T extends Entity> {
    String add(Entity e);
    String update(Entity e);
    String remove(Entity e); // Null to remove all
    boolean exits(Entity e);
    T findById(String id);
    Collection<T> find(String where, String order, String limit);
    Collection<T> find(String sql);
}