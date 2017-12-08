package com.tylersuehr.cleanarchitecture.domain.people;
import com.tylersuehr.cleanarchitecture.data.models.Person;
import com.tylersuehr.cleanarchitecture.data.repositories.people.IPersonRepository;
import com.tylersuehr.cleanarchitecture.domain.UseCase;
import java.util.UUID;

/**
 * Copyright 2017 Tyler Suehr
 *
 * Business logic to Save a person in the repository.
 * Request: Valid {@link Request}.
 * Response: Valid {@link Person}.
 *
 * @author Tyler Suehr
 * @version 1.0
 */
public class CreatePersonTask extends UseCase<CreatePersonTask.Request, Person> {
    private final IPersonRepository personRepo;


    public CreatePersonTask(IPersonRepository personRepo) {
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
            this.personRepo.createPerson(person);

            // Callback with the valid model
            pass(person);
        } catch (Exception ex) {
            fail(ex);
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