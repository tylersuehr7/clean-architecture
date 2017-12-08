package com.tylersuehr.cleanarchitecture.data.repositories.people;
import android.database.sqlite.SQLiteDatabase;
import com.tylersuehr.cleanarchitecture.data.mappers.IEntityMapper;
import com.tylersuehr.cleanarchitecture.data.models.Person;
import com.tylersuehr.cleanarchitecture.data.repositories.Callbacks;
import com.tylersuehr.cleanarchitecture.data.repositories.IDatabaseClient;
import com.tylersuehr.cleanarchitecture.data.repositories.SQLQuery;

import static com.tylersuehr.cleanarchitecture.data.repositories.DatabaseContract.People;

/**
 * Copyright 2017 Tyler Suehr
 *
 * This manages the local data source for {@link IPersonRepository}.
 *
 * @author Tyler Suehr
 * @version 1.0
 */
public class LocalPersonRepository implements IPersonRepository {
    private final IEntityMapper<Person> mapper;
    private final SQLiteDatabase db;


    public LocalPersonRepository(IDatabaseClient client, IEntityMapper<Person> mapper) {
        this.db = client.getDb();
        this.mapper = mapper;
    }

    @Override
    public void createPerson(Person person) {
        this.db.insertOrThrow(People.NAME, null, mapper.map(person));
    }

    @Override
    public void deletePerson(Person person) {
        if (person == null) {
            // Delete all rows in table
            this.db.delete(People.NAME, null, null);
        } else {
            final String where = People.COL_ID + "='" + person.getId() + "'";
            this.db.delete(People.NAME, where, null);
        }
    }

    @Override
    public void findAllPeople(Callbacks.IList<Person> callback) {
        SQLQuery.query(db, mapper, People.NAME, null, null, null, callback);
    }

    @Override
    public void findPersonById(String personId, Callbacks.ISingle<Person> callback) {
        final String where = People.COL_ID + "='" + personId + "'";
        SQLQuery.query(db, mapper, People.NAME, where, callback);
    }
}