package com.tylersuehr.cleanarchitecture.domain.tasks;
import com.tylersuehr.cleanarchitecture.data.models.Car;
import com.tylersuehr.cleanarchitecture.data.repositories.IRepository;
import com.tylersuehr.cleanarchitecture.domain.UseCase;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 12/26/2016.
 */
public class AddCarTask extends UseCase<Car, Car> {
    private final IRepository<Car> repo;


    public AddCarTask(IRepository<Car> repo) {
        this.repo = repo;
    }

    @Override
    protected void onExecute() {
        final Car car = getRequest();
        repo.add(car);
        getCallback().onSuccess(car);
    }
}