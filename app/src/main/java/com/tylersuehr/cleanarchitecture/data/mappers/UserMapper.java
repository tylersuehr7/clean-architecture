package com.tylersuehr.cleanarchitecture.data.mappers;

import android.content.ContentValues;
import android.database.Cursor;

import com.tylersuehr.cleanarchitecture.data.models.User;

import java.util.UUID;

import static com.tylersuehr.cleanarchitecture.data.repositories.DatabaseContract.Users.COL_FIRSTNAME;
import static com.tylersuehr.cleanarchitecture.data.repositories.DatabaseContract.Users.COL_ID;
import static com.tylersuehr.cleanarchitecture.data.repositories.DatabaseContract.Users.COL_LASTNAME;
import static com.tylersuehr.cleanarchitecture.data.repositories.DatabaseContract.Users.COL_PASSWORD;
import static com.tylersuehr.cleanarchitecture.data.repositories.DatabaseContract.Users.COL_USERNAME;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 12/25/2016.
 */
public class UserMapper implements IEntityMapper<User> {
    @Override
    public User map(Cursor c) {
        User user = new User();
        user.setId(UUID.fromString(c.getString(c.getColumnIndex(COL_ID))));
        user.setFirstName(c.getString(c.getColumnIndex(COL_FIRSTNAME)));
        user.setLastName(c.getString(c.getColumnIndex(COL_LASTNAME)));
        user.setUsername(c.getString(c.getColumnIndex(COL_USERNAME)));
        user.setPassword(c.getString(c.getColumnIndex(COL_PASSWORD)));
        return user;
    }

    @Override
    public ContentValues toContentValues(User entity) {
        ContentValues values = new ContentValues();
        values.put(COL_ID, entity.getId().toString());
        values.put(COL_FIRSTNAME, entity.getFirstName());
        values.put(COL_LASTNAME, entity.getLastName());
        values.put(COL_USERNAME, entity.getUsername());
        values.put(COL_PASSWORD, entity.getPassword());
        return values;
    }
}