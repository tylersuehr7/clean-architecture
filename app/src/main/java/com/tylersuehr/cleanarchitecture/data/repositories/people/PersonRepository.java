package com.tylersuehr.cleanarchitecture.data.repositories.people;
import com.tylersuehr.cleanarchitecture.data.models.Person;
import com.tylersuehr.cleanarchitecture.data.repositories.ListCallback;
import com.tylersuehr.cleanarchitecture.data.repositories.SingleCallback;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 7/3/2017.
 *
 * <b>Summary</b>
 * This manages all data sources for {@link IPersonRepository}.
 *
 * NOTE: We also utilize a basic caching system here too.
 */
public final class PersonRepository implements IPersonRepository {
    private static volatile PersonRepository instance;
    private final IPersonRepository local;

    private boolean invalidCache = false;
    private Map<String, Person> cache;


    private PersonRepository(IPersonRepository local) {
        this.local = local;
    }

    public static synchronized PersonRepository getInstance(IPersonRepository local) {
        if (instance == null) {
            instance = new PersonRepository(local);
        }
        return instance;
    }

    @Override
    public void savePerson(Person person) {
        this.local.savePerson(person);
        addToCache(person);
    }

    @Override
    public void deletePerson(Person person) {
        this.local.deletePerson(person);
        if (cache != null) {
            this.cache.remove(person.getId());
        }
    }

    @Override
    public void findAllPeople(final ListCallback<Person> callback) {
        // Attempt to load all from cache
        if (validCache()) {
            callback.onListLoaded(new ArrayList<>(cache.values()));
            return;
        }

        // Fallback by loading all from local data source
        this.local.findAllPeople(new ListCallback<Person>() {
            @Override
            public void onListLoaded(List<Person> objects) {
                refreshCache(objects);
                callback.onListLoaded(objects);
            }

            @Override
            public void onNotAvailable(Exception ex) {
                callback.onNotAvailable(ex);
            }
        });
    }

    @Override
    public void findPersonById(String personId, final SingleCallback<Person> callback) {
        // Attempt to find a person from cache
        if (validCache()) {
            Person person = cache.get(personId);
            if (person != null) {
                callback.onSingleLoaded(person);
                return;
            }
        }

        // Fallback by loading person from local data source
        this.local.findPersonById(personId, new SingleCallback<Person>() {
            @Override
            public void onSingleLoaded(Person object) {
                addToCache(object);
                callback.onSingleLoaded(object);
            }

            @Override
            public void onNotAvailable(Exception ex) {
                callback.onNotAvailable(ex);
            }
        });
    }

    public void invalidateCache() {
        this.invalidCache = true;
    }

    private boolean validCache() {
        return !(invalidCache || cache == null || cache.isEmpty());
    }

    private void addToCache(Person person) {
        if (cache == null) {
            this.cache = new LinkedHashMap<>();
        }
        this.cache.put(person.getId(), person);
    }

    private void refreshCache(List<Person> people) {
        if (cache == null) {
            this.cache = new LinkedHashMap<>();
        }
        this.cache.clear();
        for (Person p : people) {
            this.cache.put(p.getId(), p);
        }
    }
}