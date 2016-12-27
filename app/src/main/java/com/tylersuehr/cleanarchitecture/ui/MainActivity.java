package com.tylersuehr.cleanarchitecture.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.tylersuehr.cleanarchitecture.R;
import com.tylersuehr.cleanarchitecture.data.models.Car;
import com.tylersuehr.cleanarchitecture.data.models.Entity;
import com.tylersuehr.cleanarchitecture.data.models.Phone;
import com.tylersuehr.cleanarchitecture.data.models.User;
import com.tylersuehr.cleanarchitecture.data.repositories.RepositoryFactory;
import com.tylersuehr.cleanarchitecture.domain.tasks.AddCarTask;
import com.tylersuehr.cleanarchitecture.domain.tasks.AddPhoneTask;
import com.tylersuehr.cleanarchitecture.domain.tasks.AddUserTask;
import com.tylersuehr.cleanarchitecture.domain.tasks.ClearAllTask;
import com.tylersuehr.cleanarchitecture.domain.tasks.LoadAllTask;
import com.tylersuehr.cleanarchitecture.ui.HelpActivity;
import com.tylersuehr.cleanarchitecture.ui.adapters.ItemAdapter;
import com.tylersuehr.cleanarchitecture.ui.presenters.MainPresenter;
import com.tylersuehr.cleanarchitecture.ui.utils.SlideInItemAnimator;
import com.tylersuehr.cleanarchitecture.ui.utils.TestUtils;
import com.tylersuehr.cleanarchitecture.ui.views.CardSpacer;

import java.util.List;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 12/25/2016.
 */
public class MainActivity extends AppCompatActivity implements MainPresenter.View {
    private MainPresenter presenter;
    private ItemAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup recycler
        adapter = new ItemAdapter();
        RecyclerView recycler = (RecyclerView)findViewById(R.id.recycler);
        recycler.addItemDecoration(new CardSpacer());
        recycler.setItemAnimator(new SlideInItemAnimator());
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);
        adapter.setDelegate(recycler);

        // Setup presenter
        LoadAllTask loadAllTask = new LoadAllTask(
                RepositoryFactory.getUsers(this),
                RepositoryFactory.getPhones(this),
                RepositoryFactory.getCars(this));
        ClearAllTask clearAllTask = new ClearAllTask(
                RepositoryFactory.getUsers(this),
                RepositoryFactory.getPhones(this),
                RepositoryFactory.getCars(this));
        presenter = new MainPresenter(this, loadAllTask,
                new AddUserTask(RepositoryFactory.getUsers(this)),
                new AddPhoneTask(RepositoryFactory.getPhones(this)),
                new AddCarTask(RepositoryFactory.getCars(this)),
                clearAllTask);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_user:
                // Add a test user
                User user = TestUtils.createUser();
                adapter.add(user);
                presenter.addTestUser(user);
                break;
            case R.id.action_phone:
                // Add a test phone
                Phone phone = TestUtils.createPhone();
                adapter.add(phone);
                presenter.addTestPhone(phone);
                break;
            case R.id.action_car:
                // Add a test tablet
                Car car = TestUtils.createCar();
                adapter.add(car);
                presenter.addTestCar(car);
                break;
            case R.id.action_clear:
                // Ask to remove items. If confirmed, remove all items in database
                Snackbar conf = Snackbar.make(findViewById(R.id.root), "Are you sure?", Snackbar.LENGTH_LONG);
                conf.setAction("Delete", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.clear();
                        presenter.clearAll();
                    }
                });
                conf.show();
                break;
            case R.id.action_about:
                // Open the help activity
                ActivityOptionsCompat op = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
                startActivity(new Intent(this, HelpActivity.class), op.toBundle());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDisplayEntities(List<Entity> entities, boolean refresh) {
        if (refresh) {
            adapter.clear();
        }
        adapter.add(entities);
    }

    @Override
    public void onShowMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}