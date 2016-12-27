package com.tylersuehr.cleanarchitecture.data.repositories;

import com.tylersuehr.cleanarchitecture.data.models.Entity;

import java.util.List;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 12/25/2016.
 */
public interface IRepository<T extends Entity> {
    interface OnEntityCallback<T> {
        void onEntityLoaded(T entity);
        void onNotAvailable();
    }

    interface OnEntitiesCallback<T> {
        void onEntitiesLoaded(List<T> entities);
        void onNotAvailable();
    }

    void add(T entity);
    void update(T entity);
    void remove(T entity);

    void findById(String id, OnEntityCallback<T> callback);
    void findAllSQL(String sql, OnEntitiesCallback<T> callback);
    void findAll(String where, String order, String limit, OnEntitiesCallback<T> callback);
    void findAll(OnEntitiesCallback<T> callback);
}