package com.tylersuehr.cleanarchitecture.ui.a_people;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.tylersuehr.cleanarchitecture.R;
import com.tylersuehr.cleanarchitecture.data.Injection;
import com.tylersuehr.cleanarchitecture.data.models.Person;
import com.tylersuehr.cleanarchitecture.data.repositories.people.IPersonRepository;
import com.tylersuehr.cleanarchitecture.domain.people.AllPeopleTask;
import com.tylersuehr.cleanarchitecture.domain.people.DeleteAllPeopleTask;
import com.tylersuehr.cleanarchitecture.domain.people.SavePersonTask;
import com.tylersuehr.cleanarchitecture.ui.shared.SlideInItemAnimator;
import java.util.List;
/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 7/3/2017.
 *
 * Presenter: {@link PeoplePresenter}
 */
public class PeopleActivity extends AppCompatActivity implements PeoplePresenter.PeopleView {
    private PeoplePresenter presenter;
    private PersonAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);

        // Setup toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setup the people list
        adapter = new PersonAdapter();
        RecyclerView recycler = (RecyclerView)findViewById(R.id.recycler);
        recycler.setItemAnimator(new SlideInItemAnimator());
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);

        // Setup the people presenter
        IPersonRepository personRepo = Injection.providePeopleRepo(getApplicationContext());
        this.presenter = new PeoplePresenter(this,
                new AllPeopleTask(personRepo),
                new SavePersonTask(personRepo),
                new DeleteAllPeopleTask(personRepo));

        // Load all the existing people
        this.presenter.loadAllPeople();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                PersonBottomSheet.show(getSupportFragmentManager(), new PersonBottomSheet.PersonDialogListener() {
                    @Override
                    public void onPersonAdded(String first, String last, int age) {
                        presenter.savePerson(first, last, age);
                    }
                });
                break;
            case R.id.action_clear:
                this.adapter.clear();
                this.presenter.clearPeople();
                break;
            case R.id.action_about:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onShowMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPeopleReady(List<Person> people) {
        this.adapter.clear();
        this.adapter.addAll(people);
    }

    @Override
    public void onPersonSaved(Person person) {
        this.adapter.add(person);
    }
}