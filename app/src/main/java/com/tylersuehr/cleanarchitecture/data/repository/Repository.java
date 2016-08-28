package com.tylersuehr.cleanarchitecture.data.repository;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.tylersuehr.cleanarchitecture.data.DatabaseManager;
import com.tylersuehr.cleanarchitecture.data.mappers.Mapper;
import com.tylersuehr.cleanarchitecture.data.models.Entity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 8/28/2016.
 *
 * This is our implementation of {@link IRepository}. It allows us to
 * use a local SQLite database.
 *
 * NOTE: This opens and closes a connection each time a method is called.
 */
public class Repository<T extends Entity> implements IRepository<T> {
    private static final String TAG = "REPO >";
    private DatabaseManager manager;
    private Mapper<T> mapper;
    private String table;
    private Context c;


    public Repository(Context c, String table, Mapper<T> mapper) {
        this.mapper = mapper;
        this.table = table;
        this.c = c;
    }

    @Override
    public String addAll(Collection<T> entities) {
        SQLiteDatabase db = open();
        for (T e : entities) {
            db.insert(table, null, mapper.toContentValues(e));
        }
        manager.close();
        return "";
    }

    @Override
    public String add(T e) {
        SQLiteDatabase db = open();
        db.insert(table, null, mapper.toContentValues(e));
        manager.close();
        return "";
    }

    @Override
    public String update(T e) {
        SQLiteDatabase db = open();
        db.update(table, mapper.toContentValues(e), e.toString(), null);
        manager.close();
        return "";
    }

    @Override
    public String remove(T e) {
        SQLiteDatabase db = open();
        db.delete(table, e != null ? e.toString() : null, null);
        manager.close();
        return "";
    }

    @Override
    public boolean exits(T e) {
        SQLiteDatabase db = open();
        Cursor c = db.query(table, null, e.toString(), null, null, null, null);
        int count = c.getCount();
        c.close();
        manager.close();
        return count > 0;
    }

    @Override
    public T findById(String id) {
        try {
            SQLiteDatabase db = open();
            Cursor c = db.query(table, null, "[id]='" + id + "'", null, null, null, null);
            c.moveToFirst();

            T object = mapper.map(c);
            c.close();
            manager.close();

            return object;
        } catch (Exception ex) {
            Log.e(TAG, "Couldn't find by id!", ex);
        }
        return null;
    }

    @Override
    public Collection<T> find(String where, String order, String limit) {
        try {
            SQLiteDatabase db = open();
            Cursor c = db.query(table, null, where, null, null, null, order, limit);
            c.moveToFirst();

            List<T> objects = new ArrayList<>();
            for (int i = 0; i < c.getCount(); i++) {
                objects.add(mapper.map(c));
                c.moveToNext();
            }
            c.close();
            manager.close();

            return objects;
        } catch (Exception ex) {
            Log.e(TAG, "Couldn't find!", ex);
        }
        return null;
    }

    @Override
    public Collection<T> find(String sql) {
        try {
            SQLiteDatabase db = open();
            Cursor c = db.rawQuery(sql, null);
            c.moveToFirst();

            List<T> objects = new ArrayList<>();
            for (int i = 0; i < c.getCount(); i++) {
                objects.add(mapper.map(c));
                c.moveToNext();
            }
            c.close();
            manager.close();

            return objects;
        } catch (Exception ex) {
            Log.e(TAG, "Couldn't find with SQL!", ex);
        }
        return null;
    }

    /**
     * Opens a connection to our database and returns a
     * valid instance of our SQLite database.
     * @return {@link SQLiteDatabase}
     */
    private SQLiteDatabase open() {
        manager = new DatabaseManager(c);
        return manager.getDb();
    }
}