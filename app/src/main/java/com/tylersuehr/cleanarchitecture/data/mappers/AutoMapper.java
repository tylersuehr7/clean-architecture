package com.tylersuehr.cleanarchitecture.data.mappers;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;
import com.tylersuehr.cleanarchitecture.data.models.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 8/28/2016.
 *
 * This is a test utility to provide automatic mapping of an object
 * by just giving its class type.
 */
public final class AutoMapper<T extends Entity> {
    private static final String TAG = "AUTO-MAP >";
    private Class<T> type;


    public AutoMapper(Class<T> type) {
        this.type = type;
    }

    public T map(Cursor c) {
        try {
            // Create new instance of our entity object
            T instance = type.newInstance();
            instance.setId(c.getString(c.getColumnIndex("id")));

            // Check if it is a technology object
            if (instance instanceof Technology) {
                ((Technology)instance).setBrand(c.getString(c.getColumnIndex("brand")));
                ((Technology)instance).setModel(c.getString(c.getColumnIndex("model")));
                ((Technology)instance).setPrice(c.getDouble(c.getColumnIndex("price")));
                ((Technology)instance).setIconResName(c.getString(c.getColumnIndex("iconResName")));
                if (instance instanceof Phone) { // Phone
                    String[] providers = c.getString(c.getColumnIndex("providers")).split(",");
                    for (String p : providers)
                        ((Phone)instance).addEligibleProvider(p);
                } else if (instance instanceof Tablet) { // Tablet
                    ((Tablet)instance).setSize(c.getInt(c.getColumnIndex("size")));
                } else if (instance instanceof Watch) { // Watch
                    ((Watch)instance).setCircular(c.getInt(c.getColumnIndex("isCircular")) == 1);
                }
            } else {
                if (instance instanceof User) { // User
                    ((User)instance).setFirstName(c.getString(c.getColumnIndex("firstName")));
                    ((User)instance).setLastName(c.getString(c.getColumnIndex("lastName")));
                    ((User)instance).setEmail(c.getString(c.getColumnIndex("email")));
                }
            }
            return instance;
        } catch (Exception ex) {
            Log.e(TAG, "Couldn't map!", ex);
        }
        return null;
    }

    // Assumes the table columns are written exactly as the field names
    public ContentValues toContentValues(T o) {
        try {
            ContentValues values = new ContentValues();
            values.put("id", o.getId().toString());

            Collection<Field> fields = getAllFields(type);
            String fName;
            for (Field f : fields) {
                // Make sure we can read private fields
                f.setAccessible(true);
                fName = f.getName();
                if (isString(f.getType())) {
                    values.put(fName, (String)f.get(o));
                } else if (f.getType().isAssignableFrom(int.class)) {
                    values.put(fName, (int)f.get(o));
                } else if (f.getType().isAssignableFrom(long.class)) {
                    values.put(fName, (long)f.get(o));
                } else if (f.getType().isAssignableFrom(float.class)) {
                    values.put(fName, (float)f.get(o));
                } else if (f.getType().isAssignableFrom(double.class)) {
                    values.put(fName, (double)f.get(o));
                } else if (f.getType().isAssignableFrom(boolean.class)) {
                    values.put(fName, (boolean)f.get(o));
                }
            }
            return values;
        } catch (Exception ex) {
            Log.e(TAG, "Couldn't to content values!", ex);
        }
        throw new RuntimeException("Couldn't create a valid ContentValues object!");
    }

    public Collection<Field> getAllFields(Class<?> type) {
        // Collect all the fields in our object
        Collection<Field> allFields = new ArrayList<>();
        Collections.addAll(allFields, type.getDeclaredFields());

        // Check our object's superclass and look for more fields to collect.
        // Make sure it isn't entity as we already set the id value
        if (!type.getSuperclass().isAssignableFrom(Entity.class)) {
            Class<?> newType = type.getSuperclass();
            allFields.addAll(getAllFields(newType));
        }
        return allFields;
    }

    private boolean isString(Class<?> test) {
        Class<?>[] textTypes = { String.class, UUID.class };
        for (Class<?> c : textTypes) {
            if (test.isAssignableFrom(c)) {
                return true;
            }
        }
        return false;
    }
}