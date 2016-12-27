package com.tylersuehr.cleanarchitecture.data.mappers;

import android.content.ContentValues;
import android.database.Cursor;

import com.tylersuehr.cleanarchitecture.data.models.Entity;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 12/25/2016.
 */
public interface IEntityMapper<T extends Entity> {
    T map(Cursor c);
    ContentValues toContentValues(T entity);
}