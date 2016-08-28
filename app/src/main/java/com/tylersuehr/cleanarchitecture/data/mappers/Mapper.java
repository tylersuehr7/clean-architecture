package com.tylersuehr.cleanarchitecture.data.mappers;
import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.CallSuper;
import com.tylersuehr.cleanarchitecture.data.models.Entity;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 8/28/2016.
 */
public abstract class Mapper<T extends Entity> {
    public abstract T map(Cursor c);

    @CallSuper
    public ContentValues toContentValues(T o) {
        ContentValues values = new ContentValues();
        values.put("id", o.getId().toString());
        return values;
    }
}