package com.tylersuehr.cleanarchitecture.data.mappers;
import android.content.ContentValues;
import android.database.Cursor;
import com.tylersuehr.cleanarchitecture.data.models.Entity;
/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 7/3/2017.
 *
 * We encapsulate the mapping logic for our models for the SQLite database.
 */
public interface IEntityMapper<T extends Entity> {
    /**
     * Maps {@link Cursor} data to an {@link Entity} object.
     * @param c {@link Cursor}
     * @return {@link Entity} object
     */
    T map(Cursor c);

    /**
     * Maps an {@link Entity} object to {@link ContentValues} to store in SQLite database.
     * @param entity {@link Entity} object
     * @return {@link ContentValues}
     */
    ContentValues map(T entity);
}