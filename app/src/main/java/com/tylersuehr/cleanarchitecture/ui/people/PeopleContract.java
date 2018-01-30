package com.tylersuehr.cleanarchitecture.ui.people;

import com.tylersuehr.cleanarchitecture.data.models.Person;
import com.tylersuehr.cleanarchitecture.ui.IBasePresenter;
import com.tylersuehr.cleanarchitecture.ui.IBaseView;

import java.util.List;

/**
 * Copyright © 2017 Tyler Suehr
 *
 * A contract to define the decouple presenter and view for conceptual people.
 *
 * @author Tyler Suehr
 * @version 1.0
 */
interface PeopleContract {
    /**
     * Defines core methods for people presenter.
     */
    interface IPeoplePresenter extends IBasePresenter<IPeopleView> {
        void loadPeople();
        void createPerson(String first, String last, int age);
        void deleteAllPeople();
    }

    /**
     * Defines core methods for people view.
     */
    interface IPeopleView extends IBaseView {
        void onPeopleReady(List<Person> people);
        void onPeopleUnavailable();
        void onPersonCreated(Person person);
        void onPeopleDeleted();
    }
}