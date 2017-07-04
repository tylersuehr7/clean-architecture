package com.tylersuehr.cleanarchitecture.domain.people;
import com.tylersuehr.cleanarchitecture.data.exceptions.UseCaseFailedException;
import com.tylersuehr.cleanarchitecture.data.models.Person;
import com.tylersuehr.cleanarchitecture.data.repositories.SingleCallback;
import com.tylersuehr.cleanarchitecture.data.repositories.people.IPersonRepository;
import com.tylersuehr.cleanarchitecture.domain.UseCase;
/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 7/3/2017.
 *
 * Finds a person in the repository.
 *
 * <b>Request</b>: Person's id.
 * <b>Response</b>: Valid {@link Person}.
 */
public class FindPersonTask extends UseCase<String, Person> {
    private final IPersonRepository personRepo;


    public FindPersonTask(IPersonRepository personRepo) {
        this.personRepo = personRepo;
    }

    @Override
    protected void onExecute() {
        final String personId = getRequest();
        this.personRepo.findPersonById(personId, new SingleCallback<Person>() {
            @Override
            public void onSingleLoaded(Person object) {
                getCallback().onSuccess(object);
            }

            @Override
            public void onNotAvailable(Exception ex) {
                UseCaseFailedException wrap = new UseCaseFailedException(FindPersonTask.this, ex);
                logFail(wrap);
                getCallback().onFailure(wrap);
            }
        });
    }
}