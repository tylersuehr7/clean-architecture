package com.tylersuehr.cleanarchitecture.ui.presenters;
import com.tylersuehr.cleanarchitecture.domain.UseCaseScheduler;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 12/25/2016.
 */
class Presenter<T> {
    protected final UseCaseScheduler scheduler = UseCaseScheduler.getInstance();
    private final T view;


    Presenter(T view) {
        this.view = view;
    }

    T getView() {
        return view;
    }
}