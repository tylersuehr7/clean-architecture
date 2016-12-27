package com.tylersuehr.cleanarchitecture.data.repositories;

import android.content.Context;

import com.tylersuehr.cleanarchitecture.data.mappers.CarMapper;
import com.tylersuehr.cleanarchitecture.data.mappers.PhoneMapper;
import com.tylersuehr.cleanarchitecture.data.mappers.UserMapper;
import com.tylersuehr.cleanarchitecture.data.models.Car;
import com.tylersuehr.cleanarchitecture.data.models.Phone;
import com.tylersuehr.cleanarchitecture.data.models.User;

import java.util.HashMap;
import java.util.Map;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 12/25/2016.
 */
@SuppressWarnings("unchecked")
public final class RepositoryFactory {
    private static final Map<Class, IRepository> cache = new HashMap<>();


    public static IRepository<User> getUsers(Context c) {
        IRepository<User> repo = (IRepository<User>)cache.get(User.class);
        if (repo == null) {
            repo = new Repository<>(new LocalRepository<>(c, DatabaseContract.Users.NAME, new UserMapper()));
            cache.put(User.class, repo);
        }
        return repo;
    }

    public static IRepository<Phone> getPhones(Context c) {
        IRepository<Phone> repo = (IRepository<Phone>)cache.get(Phone.class);
        if (repo == null) {
            repo = new Repository<>(new LocalRepository<>(c, DatabaseContract.Phones.NAME, new PhoneMapper()));
            cache.put(Phone.class, repo);
        }
        return repo;
    }

    public static IRepository<Car> getCars(Context c) {
        IRepository<Car> repo = (IRepository<Car>)cache.get(Car.class);
        if (repo == null) {
            repo = new Repository<>(new LocalRepository<>(c, DatabaseContract.Cars.NAME, new CarMapper()));
            cache.put(Car.class, repo);
        }
        return repo;
    }
}