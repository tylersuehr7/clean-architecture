package com.tylersuehr.cleanarchitecture.data;
import android.app.Activity;
import android.content.Context;
import com.tylersuehr.cleanarchitecture.data.mappers.PersonMapper;
import com.tylersuehr.cleanarchitecture.data.repositories.DatabaseClient;
import com.tylersuehr.cleanarchitecture.data.repositories.people.LocalPersonRepository;
import com.tylersuehr.cleanarchitecture.data.repositories.people.PersonRepository;

/**
 * Copyright 2017 Tyler Suehr
 *
 * We use this to provide production instances of our repositories.
 * Note: ensure to use {@link Activity#getApplicationContext()} only to prevent
 *       memory leakage!!
 *
 * @author Tyler Suehr
 * @version 1.0
 */
public final class Injector {
    public static PersonRepository providePeopleRepo(Context appCtx) {
        return PersonRepository.getInstance(new LocalPersonRepository(
                DatabaseClient.getInstance(appCtx), new PersonMapper()
        ));
    }
}