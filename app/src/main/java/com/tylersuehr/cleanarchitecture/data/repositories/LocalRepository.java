package com.tylersuehr.cleanarchitecture.data.repositories;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.tylersuehr.cleanarchitecture.data.mappers.IEntityMapper;
import com.tylersuehr.cleanarchitecture.data.models.Entity;

import java.util.LinkedList;
import java.util.List;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 12/25/2016.
 */
final class LocalRepository<T extends Entity> implements IRepository<T> {
    private final IEntityMapper<T> mapper;
    private final DatabaseClient client;
    private final String table;


    LocalRepository(Context c, String table, IEntityMapper<T> mapper) {
        this.client = DatabaseClient.getInstance(c);
        this.table = table;
        this.mapper = mapper;
    }

    @Override
    public void add(T entity) {
        client.getDb().insert(table, null, mapper.toContentValues(entity));
    }

    @Override
    public void update(T entity) {
        client.getDb().update(table, mapper.toContentValues(entity), entity.toQuery(), null);
    }

    @Override
    public void remove(T entity) {
        client.getDb().delete(table, (entity != null) ? entity.toQuery() : null, null);
    }

    @Override
    public void findById(String id, OnEntityCallback<T> callback) {
        try {
            Cursor c = client.getDb().query(table, null, "[id]='" + id + "'", null, null, null, null);
            c.moveToFirst();

            T entity = mapper.map(c);

            c.close();
            callback.onEntityLoaded(entity);
        } catch (Exception ex) {
            callback.onNotAvailable();
        }
    }

    @Override
    public void findAllSQL(String sql, OnEntitiesCallback<T> callback) {
        try {
            Cursor c = client.getDb().rawQuery(sql, null);
            c.moveToFirst();

            List<T> entities = new LinkedList<>();
            for (int i = 0; i < c.getCount(); i++) {
                entities.add(mapper.map(c));
                c.moveToNext();
            }

            c.close();
            callback.onEntitiesLoaded(entities);
        } catch (Exception ex) {
            callback.onNotAvailable();
        }
    }

    @Override
    public void findAll(String where, String order, String limit, OnEntitiesCallback<T> callback) {
        try {
            Cursor c = client.getDb().query(table, null, where, null, null, null, order, limit);
            c.moveToFirst();

            List<T> entities = new LinkedList<>();
            for (int i = 0; i < c.getCount(); i++) {
                entities.add(mapper.map(c));
                c.moveToNext();
            }

            c.close();
            callback.onEntitiesLoaded(entities);
        } catch (Exception ex) {
            Log.e("LOCAL >", "Couldn't load entitied!", ex);
            callback.onNotAvailable();
        }
    }

    @Override
    public void findAll(OnEntitiesCallback<T> callback) {
        try {
            Cursor c = client.getDb().query(table, null, null, null, null, null, null);
            c.moveToFirst();

            List<T> entities = new LinkedList<>();
            for (int i = 0; i < c.getCount(); i++) {
                entities.add(mapper.map(c));
                c.moveToNext();
            }

            c.close();
            callback.onEntitiesLoaded(entities);
        } catch (Exception ex) {
            Log.e("LOCAL >", "Couldn't load entities!", ex);
            callback.onNotAvailable();
        }
    }
}