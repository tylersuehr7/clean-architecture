package com.tylersuehr.cleanarchitecture.domain.people;
import com.tylersuehr.cleanarchitecture.data.models.Person;
import com.tylersuehr.cleanarchitecture.data.repositories.people.IPersonRepository;
import com.tylersuehr.cleanarchitecture.domain.UseCase;

/**
 * Copyright 2017 Tyler Suehr
 *
 * Business logic to delete a person in the repository.
 * Request: Valid {@link Person}.
 * Response: Valid {@link Person}.
 *
 * @author Tyler Suehr
 * @version 1.0
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
            pass(person);
        } catch (Exception ex) {
            fail(ex);
        }
    }
}