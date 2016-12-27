package com.tylersuehr.cleanarchitecture.data.mappers;

import android.content.ContentValues;
import android.database.Cursor;

import com.tylersuehr.cleanarchitecture.data.models.Phone;

import java.util.UUID;

import static com.tylersuehr.cleanarchitecture.data.repositories.DatabaseContract.Phones.COL_CARRIER;
import static com.tylersuehr.cleanarchitecture.data.repositories.DatabaseContract.Phones.COL_ID;
import static com.tylersuehr.cleanarchitecture.data.repositories.DatabaseContract.Phones.COL_MAKE;
import static com.tylersuehr.cleanarchitecture.data.repositories.DatabaseContract.Phones.COL_MODEL;
import static com.tylersuehr.cleanarchitecture.data.repositories.DatabaseContract.Phones.COL_PRICE;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 12/25/2016.
 */
public class PhoneMapper implements IEntityMapper<Phone> {
    @Override
    public Phone map(Cursor c) {
        Phone phone = new Phone();
        phone.setId(UUID.fromString(c.getString(c.getColumnIndex(COL_ID))));
        phone.setMake(c.getString(c.getColumnIndex(COL_MAKE)));
        phone.setModel(c.getString(c.getColumnIndex(COL_MODEL)));
        phone.setCarrier(c.getString(c.getColumnIndex(COL_CARRIER)));
        phone.setPrice(c.getDouble(c.getColumnIndex(COL_PRICE)));
        return phone;
    }

    @Override
    public ContentValues toContentValues(Phone entity) {
        ContentValues values = new ContentValues();
        values.put(COL_ID, entity.getId().toString());
        values.put(COL_MAKE, entity.getMake());
        values.put(COL_MODEL, entity.getModel());
        values.put(COL_CARRIER, entity.getCarrier());
        values.put(COL_PRICE, entity.getPrice());
        return values;
    }
}