package com.tylersuehr.cleanarchitecture.ui;
import com.tylersuehr.cleanarchitecture.domain.UseCaseScheduler;

/**
 * Copyright 2017 Tyler Suehr
 *
 * This is the base presenter for all presenters.
 *
 * @author Tyler Suehr
 * @version 1.0
 */
public abstract class BasePresenter<T> {
    protected final UseCaseScheduler scheduler = UseCaseScheduler.getInstance();
    private T view;


    public BasePresenter() {}

    public BasePresenter(T view) {
        this.view = view;
    }

    public T getView() {
        return view;
    }

    public void setView(T view) {
        this.view = view;
    }
}