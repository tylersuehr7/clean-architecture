package com.tylersuehr.cleanarchitecture.domain.tasks;
import com.tylersuehr.cleanarchitecture.data.models.User;
import com.tylersuehr.cleanarchitecture.data.repositories.IRepository;
import com.tylersuehr.cleanarchitecture.domain.UseCase;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 12/26/2016.
 */
public class AddUserTask extends UseCase<User, User> {
    private final IRepository<User> repo;


    public AddUserTask(IRepository<User> repo) {
        this.repo = repo;
    }

    @Override
    protected void execute() {
        final User user = getRequest();
        repo.add(user);
        getCallback().onSuccess(user);
    }
}