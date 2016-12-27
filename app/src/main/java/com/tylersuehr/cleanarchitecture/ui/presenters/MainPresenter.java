package com.tylersuehr.cleanarchitecture.ui.presenters;

import com.tylersuehr.cleanarchitecture.data.models.Car;
import com.tylersuehr.cleanarchitecture.data.models.Entity;
import com.tylersuehr.cleanarchitecture.data.models.Phone;
import com.tylersuehr.cleanarchitecture.data.models.User;
import com.tylersuehr.cleanarchitecture.domain.UseCaseCallback;
import com.tylersuehr.cleanarchitecture.domain.tasks.AddCarTask;
import com.tylersuehr.cleanarchitecture.domain.tasks.AddPhoneTask;
import com.tylersuehr.cleanarchitecture.domain.tasks.AddUserTask;
import com.tylersuehr.cleanarchitecture.domain.tasks.ClearAllTask;
import com.tylersuehr.cleanarchitecture.domain.tasks.LoadAllTask;

import java.util.List;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 12/25/2016.
 */
public class MainPresenter extends Presenter<MainPresenter.View> {
    private final LoadAllTask loadAllTask;
    private final AddUserTask addUserTask;
    private final AddPhoneTask addPhoneTask;
    private final AddCarTask addCarTask;
    private final ClearAllTask clearAllTask;


    public MainPresenter(View view, LoadAllTask task1, AddUserTask task2, AddPhoneTask task3, AddCarTask task4, ClearAllTask task5) {
        super(view);
        this.loadAllTask = task1;
        this.addUserTask = task2;
        this.addPhoneTask = task3;
        this.addCarTask = task4;
        this.clearAllTask = task5;
    }

    /**
     * We want to load all entities when the app starts.
     */
    public void onStart() {
        scheduler.execute(loadAllTask, null, new UseCaseCallback<List<Entity>>() {
            @Override
            public void onSuccess(List<Entity> response) {
                getView().onDisplayEntities(response, true);
            }

            @Override
            public void onFailure(Object reason) {
                getView().onShowMessage((String)reason);
            }
        });
    }

    public void addTestUser(User user) {
        scheduler.execute(addUserTask, user, new UseCaseCallback<User>() {
            @Override
            public void onSuccess(User response) {
                getView().onShowMessage("User added!");
            }

            @Override
            public void onFailure(Object reason) {
                getView().onShowMessage("Failed to add user!");
            }
        });
    }

    public void addTestPhone(Phone phone) {
        scheduler.execute(addPhoneTask, phone, new UseCaseCallback<Phone>() {
            @Override
            public void onSuccess(Phone response) {
                getView().onShowMessage("Phone added!");
            }

            @Override
            public void onFailure(Object reason) {
                getView().onShowMessage("Failed to add phone!");
            }
        });
    }

    public void addTestCar(Car car) {
        scheduler.execute(addCarTask, car, new UseCaseCallback<Car>() {
            @Override
            public void onSuccess(Car response) {
                getView().onShowMessage("Car added!");
            }

            @Override
            public void onFailure(Object reason) {
                getView().onShowMessage("Failed to add car!");
            }
        });
    }

    public void clearAll() {
        scheduler.execute(clearAllTask, null, new UseCaseCallback<Object>() {
            @Override
            public void onSuccess(Object response) {
                getView().onShowMessage("All entities cleared!");
            }

            @Override
            public void onFailure(Object reason) {
                getView().onShowMessage("Failed to clear all entities!");
            }
        });
    }


    public interface View {
        void onDisplayEntities(List<Entity> entities, boolean refresh);
        void onShowMessage(String msg);
    }
}