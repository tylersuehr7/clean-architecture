package com.tylersuehr.cleanarchitecture.domain.tasks;

import com.tylersuehr.cleanarchitecture.data.models.Car;
import com.tylersuehr.cleanarchitecture.data.models.Entity;
import com.tylersuehr.cleanarchitecture.data.models.Phone;
import com.tylersuehr.cleanarchitecture.data.models.User;
import com.tylersuehr.cleanarchitecture.data.repositories.IRepository;
import com.tylersuehr.cleanarchitecture.domain.UseCase;

import java.util.LinkedList;
import java.util.List;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 12/25/2016.
 */
public class LoadAllTask extends UseCase<Object, List<Entity>> {
    private final IRepository<User> userRepo;
    private final IRepository<Phone> phoneRepo;
    private final IRepository<Car> carRepo;


    public LoadAllTask(IRepository<User> users, IRepository<Phone> phones, IRepository<Car> cars) {
        this.userRepo = users;
        this.phoneRepo = phones;
        this.carRepo = cars;
    }

    @Override
    protected void execute() {
        List<Entity> entities = new LinkedList<>();

        collectUsers(entities);
        collectPhones(entities);
        collectCars(entities);

        getCallback().onSuccess(entities);
    }

    private void collectUsers(final List<Entity> entities) {
        userRepo.findAll(new IRepository.OnEntitiesCallback<User>() {
            @Override
            public void onEntitiesLoaded(List<User> users) {
                entities.addAll(users);
            }

            @Override
            public void onNotAvailable() {
                getCallback().onFailure("Couldn't load users!");
            }
        });
    }

    private void collectPhones(final List<Entity> entities) {
        phoneRepo.findAll(new IRepository.OnEntitiesCallback<Phone>() {
            @Override
            public void onEntitiesLoaded(List<Phone> phones) {
                entities.addAll(phones);
            }

            @Override
            public void onNotAvailable() {
                getCallback().onFailure("Couldn't load phones!");
            }
        });
    }

    private void collectCars(final List<Entity> entities) {
        carRepo.findAll(new IRepository.OnEntitiesCallback<Car>() {
            @Override
            public void onEntitiesLoaded(List<Car> cars) {
                entities.addAll(cars);
            }

            @Override
            public void onNotAvailable() {
                getCallback().onFailure("Couldn't load cars!");
            }
        });
    }
}