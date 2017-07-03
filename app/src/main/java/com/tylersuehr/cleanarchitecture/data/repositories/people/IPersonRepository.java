package com.tylersuehr.cleanarchitecture.data.repositories.people;
import com.tylersuehr.cleanarchitecture.data.models.Person;
import com.tylersuehr.cleanarchitecture.data.repositories.ListCallback;
import com.tylersuehr.cleanarchitecture.data.repositories.SingleCallback;
/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 7/3/2017.
 */
public interface IPersonRepository {
    void savePerson(Person person);
    void deletePerson(Person person);
    void findAllPeople(ListCallback<Person> callback);
    void findPersonById(String personId, SingleCallback<Person> callback);
}