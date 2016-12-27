package com.tylersuehr.cleanarchitecture.data.mappers;

import android.content.ContentValues;
import android.database.Cursor;

import com.tylersuehr.cleanarchitecture.data.models.Car;

import java.util.UUID;

import static com.tylersuehr.cleanarchitecture.data.repositories.DatabaseContract.Cars.COL_COLOR;
import static com.tylersuehr.cleanarchitecture.data.repositories.DatabaseContract.Cars.COL_ID;
import static com.tylersuehr.cleanarchitecture.data.repositories.DatabaseContract.Cars.COL_IS_USED;
import static com.tylersuehr.cleanarchitecture.data.repositories.DatabaseContract.Cars.COL_MAKE;
import static com.tylersuehr.cleanarchitecture.data.repositories.DatabaseContract.Cars.COL_MODEL;
import static com.tylersuehr.cleanarchitecture.data.repositories.DatabaseContract.Cars.COL_YEAR;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 12/25/2016.
 */
public class CarMapper implements IEntityMapper<Car> {
    @Override
    public Car map(Cursor c) {
        Car car = new Car();
        car.setId(UUID.fromString(c.getString(c.getColumnIndex(COL_ID))));
        car.setMake(c.getString(c.getColumnIndex(COL_MAKE)));
        car.setModel(c.getString(c.getColumnIndex(COL_MODEL)));
        car.setColor(c.getString(c.getColumnIndex(COL_COLOR)));
        car.setYear(c.getInt(c.getColumnIndex(COL_YEAR)));
        car.setUsed(c.getInt(c.getColumnIndex(COL_IS_USED)) == 1);
        return car;
    }

    @Override
    public ContentValues toContentValues(Car entity) {
        ContentValues values = new ContentValues();
        values.put(COL_ID, entity.getId().toString());
        values.put(COL_MAKE, entity.getMake());
        values.put(COL_MODEL, entity.getModel());
        values.put(COL_COLOR, entity.getColor());
        values.put(COL_YEAR, entity.getYear());
        values.put(COL_IS_USED, entity.isUsed());
        return values;
    }
}