package com.tylersuehr.cleanarchitecture.domain.people;
import com.tylersuehr.cleanarchitecture.data.exceptions.UseCaseFailedException;
import com.tylersuehr.cleanarchitecture.data.models.Person;
import com.tylersuehr.cleanarchitecture.data.repositories.people.IPersonRepository;
import com.tylersuehr.cleanarchitecture.domain.UseCase;
import java.util.UUID;
/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 7/3/2017.
 *
 * <b>Summary</b>
 * Saves a person in the repository.
 *
 * <b>Request</b>: Valid {@link Request}.
 * <b>Response</b>: Valid {@link Person}.
 */
public class SavePersonTask extends UseCase<SavePersonTask.Request, Person> {
    private final IPersonRepository personRepo;


    public SavePersonTask(IPersonRepository personRepo) {
        this.personRepo = personRepo;
    }

    @Override
    protected void onExecute() {
        try {
            final Request request = getRequest();

            // Create the person model
            Person person = new Person();
            person.setId(UUID.randomUUID().toString());
            person.setFirstName(request.first);
            person.setLastName(request.last);
            person.setImage(request.image);
            person.setAge(request.age);

            // Save person in the repository
            this.personRepo.savePerson(person);

            // Callback with the valid model
            getCallback().onSuccess(person);
        } catch (Exception ex) {
            UseCaseFailedException wrap = new UseCaseFailedException(this, ex);
            logFail(wrap);
            getCallback().onFailure(wrap);
        }
    }

    public static final class Request {
        private final String first;
        private final String last;
        private final String image;
        private final int age;

        public Request(String first, String last, String image, int age) {
            this.first = first;
            this.last = last;
            this.image = image;
            this.age = age;
        }
    }
}