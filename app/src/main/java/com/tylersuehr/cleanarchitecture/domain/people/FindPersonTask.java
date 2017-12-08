package com.tylersuehr.cleanarchitecture.domain.people;
import com.tylersuehr.cleanarchitecture.data.models.Person;
import com.tylersuehr.cleanarchitecture.data.repositories.SingleCallback;
import com.tylersuehr.cleanarchitecture.data.repositories.people.IPersonRepository;
import com.tylersuehr.cleanarchitecture.domain.UseCase;

/**
 * Copyright 2017 Tyler Suehr
 *
 * Business logic to find a person in the repository.
 * Request: Person's id.
 * Response: Valid {@link Person}.
 *
 * @author Tyler Suehr
 * @version 1.0
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
            public void onSingleLoaded(Person foundPerson) {
                pass(foundPerson);
            }

            @Override
            public void onNotAvailable(Exception ex) {
                fail(ex);
            }
        });
    }
}