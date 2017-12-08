package com.tylersuehr.cleanarchitecture.domain.people;
import com.tylersuehr.cleanarchitecture.data.repositories.people.IPersonRepository;
import com.tylersuehr.cleanarchitecture.domain.UseCase;

/**
 * Copyright 2017 Tyler Suehr
 *
 * Business logic to delete all people in the repository.
 * Request: nothing.
 * Response: nothing.
 *
 * @author Tyler Suehr
 * @version 1.0
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
            pass(null);
        } catch (Exception ex) {
            fail(ex);
        }
    }
}