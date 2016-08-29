package com.tylersuehr.cleanarchitecture.data.mappers;
import android.content.ContentValues;
import android.database.Cursor;
import com.tylersuehr.cleanarchitecture.data.models.User;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 8/28/2016.
 */
public class UserMapper extends Mapper<User> {
    @Override
    public User map(Cursor c) {
        User user = new User();
        user.setId(c.getString(c.getColumnIndex("id")));
        user.setFirstName(c.getString(c.getColumnIndex("firstName")));
        user.setLastName(c.getString(c.getColumnIndex("lastName")));
        user.setEmail(c.getString(c.getColumnIndex("email")));
        return user;
    }

    @Override
    public ContentValues toContentValues(User u) {
        ContentValues values = super.toContentValues(u);
        values.put("firstName", u.getFirstName());
        values.put("lastName", u.getLastName());
        values.put("email", u.getEmail());
        return values;
    }
}