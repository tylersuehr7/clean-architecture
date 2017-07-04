package com.tylersuehr.cleanarchitecture.domain.people;
import com.tylersuehr.cleanarchitecture.data.exceptions.UseCaseFailedException;
import com.tylersuehr.cleanarchitecture.data.models.Person;
import com.tylersuehr.cleanarchitecture.data.repositories.people.IPersonRepository;
import com.tylersuehr.cleanarchitecture.domain.UseCase;
/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 7/3/2017.
 *
 * Delete a person in the repository.
 *
 * <b>Request</b>: Valid {@link Person}.
 * <b>Response</b>: Valid {@link Person}.
 */
public class DeletePersonTask extends UseCase<Person, Person> {
    private final IPersonRepository personRepo;


    public DeletePersonTask(IPersonRepository personRepo) {
        this.personRepo = personRepo;
    }

    @Override
    protected void onExecute() {
        try {
            final Person person = getRequest();
            this.personRepo.deletePerson(person);
        } catch (Exception ex) {
            UseCaseFailedException wrap = new UseCaseFailedException(DeletePersonTask.this, ex);
            logFail(wrap);
            getCallback().onFailure(wrap);
        }
    }
}