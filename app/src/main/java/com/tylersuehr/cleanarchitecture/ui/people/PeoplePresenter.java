package com.tylersuehr.cleanarchitecture.ui.people;
import com.tylersuehr.cleanarchitecture.data.models.Person;
import com.tylersuehr.cleanarchitecture.domain.UseCaseCallback;
import com.tylersuehr.cleanarchitecture.domain.people.AllPeopleTask;
import com.tylersuehr.cleanarchitecture.domain.people.DeleteAllPeopleTask;
import com.tylersuehr.cleanarchitecture.domain.people.CreatePersonTask;
import com.tylersuehr.cleanarchitecture.ui.AbstractBasePresenter;
import com.tylersuehr.cleanarchitecture.ui.shared.Mock;

import java.util.List;

/**
 * Copyright 2017 Tyler Suehr
 *
 * View: {@link PeopleActivity}
 *
 * @author Tyler Suehr
 * @version 1.0
 */
class PeoplePresenter
        extends AbstractBasePresenter<PeopleContract.IPeopleView>
        implements PeopleContract.IPeoplePresenter {
    private final AllPeopleTask allPeopleTask;
    private final CreatePersonTask savePersonTask;
    private final DeleteAllPeopleTask deleteAllPeopleTask;


    PeoplePresenter(AllPeopleTask task1, CreatePersonTask task2, DeleteAllPeopleTask task3) {
        this.allPeopleTask = task1;
        this.savePersonTask = task2;
        this.deleteAllPeopleTask = task3;
    }

    @Override
    public void loadPeople() {
        schedule(allPeopleTask, null, new UseCaseCallback<List<Person>>() {
            @Override
            public void onSuccess(List<Person> people) {
                getView().onPeopleReady(people);
            }

            @Override
            public void onFailure(Exception ex) {
                getView().onPeopleUnavailable();
            }
        });
    }

    @Override
    public void createPerson(String first, String last, int age) {
        CreatePersonTask.Request request = new CreatePersonTask.Request(first, last, Mock.randomImage(), age);
        schedule(savePersonTask, request, new UseCaseCallback<Person>() {
            @Override
            public void onSuccess(Person createdPerson) {
                getView().onPersonCreated(createdPerson);
            }

            @Override
            public void onFailure(Exception ex) {
                if (getView().isDeviceOnline()) {
                    getView().onShowMsg(ex.getMessage());
                }
            }
        });
    }

    /**
     * Deletes all people from the repository.
     */
    @Override
    public void deleteAllPeople() {
        schedule(deleteAllPeopleTask, null, new UseCaseCallback<Object>() {
            @Override
            public void onSuccess(Object response) {
                getView().onPeopleDeleted();
            }

            @Override
            public void onFailure(Exception ex) {
                if (getView().isDeviceOnline()) {
                    getView().onShowMsg(ex.getMessage());
                }
            }
        });
    }
}