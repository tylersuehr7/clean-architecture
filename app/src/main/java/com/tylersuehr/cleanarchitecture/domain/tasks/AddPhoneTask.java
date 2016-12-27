package com.tylersuehr.cleanarchitecture.domain.tasks;
import com.tylersuehr.cleanarchitecture.data.models.Phone;
import com.tylersuehr.cleanarchitecture.data.repositories.IRepository;
import com.tylersuehr.cleanarchitecture.domain.UseCase;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 12/26/2016.
 */
public class AddPhoneTask extends UseCase<Phone, Phone> {
    private final IRepository<Phone> repo;


    public AddPhoneTask(IRepository<Phone> repo) {
        this.repo = repo;
    }

    @Override
    protected void execute() {
        final Phone phone = getRequest();
        repo.add(phone);
        getCallback().onSuccess(phone);
    }
}