package com.tylersuehr.cleanarchitecture.data.mappers;
import android.content.ContentValues;
import android.database.Cursor;
import com.tylersuehr.cleanarchitecture.data.models.Tablet;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 8/28/2016.
 */
public class TabletMapper extends TechnologyMapper<Tablet> {
    @Override
    public Tablet map(Cursor c) {
        Tablet tablet = new Tablet();
        tablet.setTechnology(mapTechnology(c));
        tablet.setSize(c.getInt(c.getColumnIndex("size")));
        return tablet;
    }

    @Override
    public ContentValues toContentValues(Tablet t) {
        ContentValues values = super.toContentValues(t);
        values.put("size", t.getSize());
        return values;
    }
}