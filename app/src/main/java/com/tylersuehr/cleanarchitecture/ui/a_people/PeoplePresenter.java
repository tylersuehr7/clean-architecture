package com.tylersuehr.cleanarchitecture.ui.a_people;
import com.tylersuehr.cleanarchitecture.data.exceptions.EmptyQueryException;
import com.tylersuehr.cleanarchitecture.data.models.Person;
import com.tylersuehr.cleanarchitecture.domain.UseCaseCallback;
import com.tylersuehr.cleanarchitecture.domain.people.AllPeopleTask;
import com.tylersuehr.cleanarchitecture.domain.people.DeleteAllPeopleTask;
import com.tylersuehr.cleanarchitecture.domain.people.SavePersonTask;
import com.tylersuehr.cleanarchitecture.ui.Presenter;
import java.util.List;
/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 7/3/2017.
 *
 * View: {@link PeopleActivity}
 */
class PeoplePresenter extends Presenter<PeoplePresenter.PeopleView> {
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
     * @param image Image for the person
     * @param age Age
     */
    void savePerson(String first, String last, String image, int age) {
        SavePersonTask.Request request = new SavePersonTask.Request(first, last, image, age);
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