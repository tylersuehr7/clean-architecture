package com.tylersuehr.cleanarchitecture.ui.people;
import com.tylersuehr.cleanarchitecture.data.models.Person;
import com.tylersuehr.cleanarchitecture.domain.UseCaseCallback;
import com.tylersuehr.cleanarchitecture.domain.people.AllPeopleTask;
import com.tylersuehr.cleanarchitecture.domain.people.DeleteAllPeopleTask;
import com.tylersuehr.cleanarchitecture.domain.people.SavePersonTask;
import com.tylersuehr.cleanarchitecture.ui.shared.Mock;
import com.tylersuehr.cleanarchitecture.ui.BasePresenter;
import java.util.List;

/**
 * Copyright 2017 Tyler Suehr
 *
 * View: {@link PeopleActivity}
 *
 * @author Tyler Suehr
 * @version 1.0
 */
class PeoplePresenter extends BasePresenter<PeoplePresenter.PeopleView> {
    private final AllPeopleTask allPeopleTask;
    private final SavePersonTask savePersonTask;
    private final DeleteAllPeopleTask deleteAllPeopleTask;


    PeoplePresenter(PeopleView v, AllPeopleTask task1, SavePersonTask task2, DeleteAllPeopleTask task3) {
        super(v);
        this.allPeopleTask = task1;
        this.savePersonTask = task2;
        this.deleteAllPeopleTask = task3;
    }

    /**
     * Loads all the people from the repository.
     */
    void loadAllPeople() {
        scheduler.execute(allPeopleTask, null, new UseCaseCallback<List<Person>>() {
            @Override
            public void onSuccess(List<Person> response) {
                getView().onPeopleReady(response);
            }

            @Override
            public void onFailure(Exception ex) {
                if (!(ex.getCause() instanceof EmptyQueryException)) {
                    getView().onShowMsg(ex.getMessage());
                }
            }
        });
    }

    /**
     * Saves a person in the repository.
     * @param first First name
     * @param last Last name
     * @param age Age
     */
    void savePerson(String first, String last, int age) {
        SavePersonTask.Request request = new SavePersonTask.Request(first, last, Mock.randomImage(), age);
        scheduler.execute(savePersonTask, request, new UseCaseCallback<Person>() {
            @Override
            public void onSuccess(Person response) {
                getView().onPersonSaved(response);
            }

            @Override
            public void onFailure(Exception ex) {
                getView().onShowMsg(ex.getMessage());
            }
        });
    }

    /**
     * Deletes all people from the repository.
     */
    void clearPeople() {
        scheduler.execute(deleteAllPeopleTask, null, new UseCaseCallback<Object>() {
            @Override
            public void onSuccess(Object response) {}

            @Override
            public void onFailure(Exception ex) {
                getView().onShowMsg(ex.getMessage());
            }
        });
    }


    interface PeopleView {
        void onPeopleReady(List<Person> people);
        void onPersonSaved(Person person);
        void onShowMsg(String msg);
    }
}