package com.tylersuehr.cleanarchitecture.data.mappers;
import android.content.ContentValues;
import android.database.Cursor;
import com.tylersuehr.cleanarchitecture.data.models.Person;
import static com.tylersuehr.cleanarchitecture.data.repositories.DatabaseContract.People;
/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 7/3/2017.
 *
 * Concrete implementation of {@link IEntityMapper} for {@link Person} model.
 */
public class PersonMapper implements IEntityMapper<Person> {
    @Override
    public Person map(Cursor c) {
        Person person = new Person();
        person.setId(c.getString(c.getColumnIndex(People.COL_ID)));
        person.setFirstName(c.getString(c.getColumnIndex(People.COL_FIRST_NAME)));
        person.setLastName(c.getString(c.getColumnIndex(People.COL_LAST_NAME)));
        person.setImage(c.getString(c.getColumnIndex(People.COL_IMAGE)));
        person.setAge(c.getInt(c.getColumnIndex(People.COL_AGE)));
        return person;
    }

    @Override
    public ContentValues map(Person entity) {
        ContentValues values = new ContentValues();
        values.put(People.COL_ID, entity.getId());
        values.put(People.COL_FIRST_NAME, entity.getFirstName());
        values.put(People.COL_LAST_NAME, entity.getLastName());
        values.put(People.COL_IMAGE, entity.getImage());
        values.put(People.COL_AGE, entity.getAge());
        return values;
    }
}