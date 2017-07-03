package com.tylersuehr.cleanarchitecture.data;
import android.content.Context;
import com.tylersuehr.cleanarchitecture.data.mappers.PersonMapper;
import com.tylersuehr.cleanarchitecture.data.repositories.DatabaseClient;
import com.tylersuehr.cleanarchitecture.data.repositories.people.IPersonRepository;
import com.tylersuehr.cleanarchitecture.data.repositories.people.LocalPersonRepository;
import com.tylersuehr.cleanarchitecture.data.repositories.people.PersonRepository;
/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 7/3/2017.
 *
 * <b>Summary</b>
 * We use this to provide production instances of our repositories.
 *
 * <b>Important</b>
 * Make sure to use getApplicationContext() only, so we don't leak memory!!!!
 */
public final class Injection {
    public static PersonRepository providePeopleRepo(Context c) {
        IPersonRepository local = new LocalPersonRepository(DatabaseClient.getInstance(c), new PersonMapper());
        return PersonRepository.getInstance(local);
    }
}