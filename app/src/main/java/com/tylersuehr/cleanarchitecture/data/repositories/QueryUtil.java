package com.tylersuehr.cleanarchitecture.data.repositories;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.tylersuehr.cleanarchitecture.data.exceptions.EmptyQueryException;
import com.tylersuehr.cleanarchitecture.data.exceptions.QueryException;
import com.tylersuehr.cleanarchitecture.data.mappers.IEntityMapper;
import com.tylersuehr.cleanarchitecture.data.models.Entity;
import java.util.ArrayList;
import java.util.List;
/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 7/3/2017.
 *
 * Basic utility to help with querying the local SQLite database.
 */
public final class QueryUtil {
    /**
     * Queries the SQLite database for a single object.
     * @param db {@link SQLiteDatabase}
     * @param mapper {@link IEntityMapper}
     * @param table Table name
     * @param where Where clause
     * @param callback {@link SingleCallback}
     */
    public static <T extends Entity> void query(
            SQLiteDatabase db,
            IEntityMapper<T> mapper,
            String table,
            String where,
            SingleCallback<T> callback) {
        Cursor c = null;
        try {
            c = db.query(table, null, where, null, null, null, null);
            c.moveToFirst();

            T object = mapper.map(c);
            callback.onSingleLoaded(object);
        } catch (Exception ex) {
            callback.onNotAvailable(new QueryException(table, ex));
        } finally {
            if (c != null) { c.close(); }
        }
    }

    /**
     * Queries the SQLite database for multiple objects.
     * @param db {@link SQLiteDatabase}
     * @param mapper {@link IEntityMapper}
     * @param table Table name
     * @param where Where clause
     * @param order OrderBy clause
     * @param limit Limit clause
     * @param callback {@link ListCallback}
     */
    public static <T extends Entity> void query(
            SQLiteDatabase db,
            IEntityMapper<T> mapper,
            String table,
            String where,
            String order,
            String limit,
            ListCallback<T> callback) {
        Cursor c = null;
        try {
            c = db.query(table, null, where, null, null, null, null);
            c.moveToFirst();

            List<T> objects = new ArrayList<>(c.getCount());
            for (int i = 0; i < c.getCount(); i++) {
                objects.add(mapper.map(c));
                c.moveToNext();
            }

            if (objects.isEmpty()) {
                throw new EmptyQueryException(table);
            }
            callback.onListLoaded(objects);
        } catch (Exception ex) {
            callback.onNotAvailable(new QueryException(table, ex));
        } finally {
            if (c != null) { c.close(); }
        }
    }

    /**
     * Queries the SQLite database for multiple objects. Does not callback with fail if results
     * are empty.
     * @param db {@link SQLiteDatabase}
     * @param mapper {@link IEntityMapper}
     * @param table Table name
     * @param where Where clause
     * @param order OrderBy clause
     * @param limit Limit clause
     * @param callback {@link ListCallback}
     */
    public static <T extends Entity> void queryForEmpty(
            SQLiteDatabase db,
            IEntityMapper<T> mapper,
            String table,
            String where,
            String order,
            String limit,
            ListCallback<T> callback) {
        Cursor c = null;
        try {
            c = db.query(table, null, where, null, null, null, null);
            c.moveToFirst();

            List<T> objects = new ArrayList<>(c.getCount());
            for (int i = 0; i < c.getCount(); i++) {
                objects.add(mapper.map(c));
                c.moveToNext();
            }

            callback.onListLoaded(objects);
        } catch (Exception ex) {
            callback.onNotAvailable(new QueryException(table, ex));
        } finally {
            if (c != null) { c.close(); }
        }
    }
}