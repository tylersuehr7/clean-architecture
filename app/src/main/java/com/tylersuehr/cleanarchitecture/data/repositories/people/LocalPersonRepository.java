package com.tylersuehr.cleanarchitecture.data.repositories.people;
import android.database.sqlite.SQLiteDatabase;
import com.tylersuehr.cleanarchitecture.data.mappers.IEntityMapper;
import com.tylersuehr.cleanarchitecture.data.models.Person;
import com.tylersuehr.cleanarchitecture.data.repositories.IDatabaseClient;
import com.tylersuehr.cleanarchitecture.data.repositories.ListCallback;
import com.tylersuehr.cleanarchitecture.data.repositories.SQLQuery;
import com.tylersuehr.cleanarchitecture.data.repositories.SingleCallback;
import static com.tylersuehr.cleanarchitecture.data.repositories.DatabaseContract.People;
/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 7/3/2017.
 *
 * This manages the local data source for {@link IPersonRepository}.
 */
public class LocalPersonRepository implements IPersonRepository {
    private final IEntityMapper<Person> mapper;
    private final SQLiteDatabase db;


    public LocalPersonRepository(IDatabaseClient client, IEntityMapper<Person> mapper) {
        this.db = client.getDb();
        this.mapper = mapper;
    }

    @Override
    public void savePerson(Person person) {
        this.db.insertWithOnConflict(People.NAME, null, mapper.map(person), SQLiteDatabase.CONFLICT_REPLACE);
    }

    @Override
    public void deletePerson(Person person) {
        if (person == null) {
            // Delete all rows in table
            this.db.delete(People.NAME, null, null);
        } else {
            String where = People.COL_ID + "='" + person.getId() + "'";
            this.db.delete(People.NAME, where, null);
        }
    }

    @Override
    public void findAllPeople(ListCallback<Person> callback) {
        SQLQuery.queryForEmpty(db, mapper, People.NAME, null, null, null, callback);
    }

    @Override
    public void findPersonById(String personId, SingleCallback<Person> callback) {
        String where = People.COL_ID + "='" + personId + "'";
        SQLQuery.query(db, mapper, People.NAME, where, callback);
    }
}