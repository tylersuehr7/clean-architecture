package com.tylersuehr.cleanarchitecture.data.mappers;
import android.content.ContentValues;
import android.support.annotation.CallSuper;
import com.tylersuehr.cleanarchitecture.data.models.Entity;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 8/28/2016.
 */
public abstract class Mapper<T extends Entity> implements IMapper<T> {
    @CallSuper
    public ContentValues toContentValues(T entity) {
        ContentValues values = new ContentValues();
        values.put("id", entity.getId().toString());
        return values;
    }
}