package com.tylersuehr.cleanarchitecture.data.repositories.people;
import android.support.annotation.Nullable;
import com.tylersuehr.cleanarchitecture.data.models.Person;
import com.tylersuehr.cleanarchitecture.data.repositories.Callbacks;

/**
 * Copyright 2017 Tyler Suehr
 *
 * Defines the core methods needed to manipulate the person repository.
 *
 * @author Tyler Suehr
 * @version 1.0
 */
public interface IPersonRepository {
    /**
     * Creates a new person in the repository.
     * @param person {@link Person}
     * @throws Exception if create fails
     */
    void createPerson(Person person) throws Exception;

    /**
     * Deletes a person in the repository. Leave null to delete all
     * people in the repository.
     * @param person {@link Person}
     *
     * @throws Exception if deletion fails
     */
    void deletePerson(@Nullable Person person) throws Exception;

    /**
     * Finds all people in the repository.
     * @param callback {@link Callbacks.IList}
     */
    void findAllPeople(Callbacks.IList<Person> callback);

    /**
     * Finds a person in the repository using their profile ID.
     * @param personId Person's profile ID
     * @param callback {@link Callbacks.ISingle}
     */
    void findPersonById(String personId, Callbacks.ISingle<Person> callback);
}