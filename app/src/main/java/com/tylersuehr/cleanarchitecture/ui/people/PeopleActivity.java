package com.tylersuehr.cleanarchitecture.ui.people;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.tylersuehr.cleanarchitecture.R;
import com.tylersuehr.cleanarchitecture.data.Injector;
import com.tylersuehr.cleanarchitecture.data.repositories.people.IPersonRepository;
import com.tylersuehr.cleanarchitecture.domain.people.AllPeopleTask;
import com.tylersuehr.cleanarchitecture.domain.people.DeleteAllPeopleTask;
import com.tylersuehr.cleanarchitecture.domain.people.CreatePersonTask;

/**
 * Copyright 2017 Tyler Suehr
 *
 * @author Tyler Suehr
 * @version 1.0
 */
public class PeopleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);

        // Setup toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Show the people fragment
        IPersonRepository personRepo = Injector.providePeopleRepo(getApplicationContext());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.body, PeopleFragment.create(
                        new PeoplePresenter(
                                new AllPeopleTask(personRepo),
                                new CreatePersonTask(personRepo),
                                new DeleteAllPeopleTask(personRepo)
                        )
                )).commit();
    }
}