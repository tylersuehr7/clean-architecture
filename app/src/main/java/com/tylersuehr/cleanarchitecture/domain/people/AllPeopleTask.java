package com.tylersuehr.cleanarchitecture.domain.people;
import com.tylersuehr.cleanarchitecture.data.models.Person;
import com.tylersuehr.cleanarchitecture.data.repositories.Callbacks;
import com.tylersuehr.cleanarchitecture.data.repositories.people.IPersonRepository;
import com.tylersuehr.cleanarchitecture.domain.UseCase;
import java.util.List;

/**
 * Copyright 2017 Tyler Suehr
 *
 * Business logic to load all people from the repository.
 * Request: nothing.
 * Response: List of {@link Person}
 *
 * @author Tyler Suehr
 * @version 1.0
 */
public class AllPeopleTask extends UseCase<Object, List<Person>> {
    private IPersonRepository personRepo;


    public AllPeopleTask(IPersonRepository personRepo) {
        this.personRepo = personRepo;
    }

    @Override
    protected void onExecute() {
        this.personRepo.findAllPeople(new Callbacks.IList<Person>() {
            @Override
            public void onListLoaded(List<Person> people) {
                pass(people);
            }

            @Override
            public void onNotAvailable(Exception ex) {
                fail(ex);
            }
        });
    }
}