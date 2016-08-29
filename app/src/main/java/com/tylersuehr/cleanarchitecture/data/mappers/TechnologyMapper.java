package com.tylersuehr.cleanarchitecture.data.mappers;
import android.database.Cursor;
import com.tylersuehr.cleanarchitecture.data.models.Entity;
import com.tylersuehr.cleanarchitecture.data.models.Technology;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 8/28/2016.
 */
public abstract class TechnologyMapper<T extends Entity> implements IMapper<T> {
    public Technology mapTechnology(Cursor c) {
        Technology t = new Technology();
        t.setId(c.getString(c.getColumnIndex("id")));
        t.setBrand(c.getString(c.getColumnIndex("brand")));
        t.setModel(c.getString(c.getColumnIndex("model")));
        t.setPrice(c.getDouble(c.getColumnIndex("price")));
        t.setIconResName(c.getString(c.getColumnIndex("iconResName")));
        return t;
    }
}