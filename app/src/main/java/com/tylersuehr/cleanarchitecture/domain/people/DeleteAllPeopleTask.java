package com.tylersuehr.cleanarchitecture.domain.people;
import com.tylersuehr.cleanarchitecture.data.repositories.people.IPersonRepository;
import com.tylersuehr.cleanarchitecture.domain.UseCase;
/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 7/4/2017.
 *
 * Deletes all people in the repository.
 *
 * <b>Request</b>: None.
 * <b>Response</b>: None.
 */
public class DeleteAllPeopleTask extends UseCase<Object, Object> {
    private final IPersonRepository personRepo;


    public DeleteAllPeopleTask(IPersonRepository personRepo) {
        this.personRepo = personRepo;
    }

    @Override
    protected void onExecute() {
        try {
            this.personRepo.deletePerson(null);
        } catch (Exception ex) {
            fail(ex);
        }
    }
}