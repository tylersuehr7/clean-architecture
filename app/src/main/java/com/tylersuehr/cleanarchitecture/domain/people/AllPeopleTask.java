package com.tylersuehr.cleanarchitecture.domain.people;
import com.tylersuehr.cleanarchitecture.data.exceptions.UseCaseFailedException;
import com.tylersuehr.cleanarchitecture.data.models.Person;
import com.tylersuehr.cleanarchitecture.data.repositories.ListCallback;
import com.tylersuehr.cleanarchitecture.data.repositories.people.IPersonRepository;
import com.tylersuehr.cleanarchitecture.domain.UseCase;
import java.util.List;
/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 7/3/2017.
 *
 * Loads all people from the repository.
 *
 * <b>Request</b>: none.
 * <b>Response</b>: List of {@link Person}.
 */
public class AllPeopleTask extends UseCase<Object, List<Person>> {
    private IPersonRepository personRepo;


    public AllPeopleTask(IPersonRepository personRepo) {
        this.personRepo = personRepo;
    }

    @Override
    protected void onExecute() {
        this.personRepo.findAllPeople(new ListCallback<Person>() {
            @Override
            public void onListLoaded(List<Person> objects) {
                getCallback().onSuccess(objects);
            }

            @Override
            public void onNotAvailable(Exception ex) {
                UseCaseFailedException wrap = new UseCaseFailedException(AllPeopleTask.this, ex);
                logFail(wrap);
                getCallback().onFailure(wrap);
            }
        });
    }
}