package com.tylersuehr.cleanarchitecture.domain.tasks;
import com.tylersuehr.cleanarchitecture.data.models.Car;
import com.tylersuehr.cleanarchitecture.data.models.Phone;
import com.tylersuehr.cleanarchitecture.data.models.User;
import com.tylersuehr.cleanarchitecture.data.repositories.IRepository;
import com.tylersuehr.cleanarchitecture.domain.UseCase;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 12/26/2016.
 */
public class ClearAllTask extends UseCase<Object, Object> {
    private final IRepository<User> userRepo;
    private final IRepository<Phone> phoneRepo;
    private final IRepository<Car> carRepo;


    public ClearAllTask(IRepository<User> userRepo, IRepository<Phone> phoneRepo, IRepository<Car> carRepo) {
        this.userRepo = userRepo;
        this.phoneRepo = phoneRepo;
        this.carRepo = carRepo;
    }

    @Override
    protected void onExecute() {
        userRepo.remove(null);
        phoneRepo.remove(null);
        carRepo.remove(null);
        getCallback().onSuccess(null);
    }
}