package com.tylersuehr.cleanarchitecture.ui;
import com.tylersuehr.cleanarchitecture.domain.UseCaseScheduler;
/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 7/3/2017.
 *
 * <b>Summary</b>
 * This is the base presenter for all presenters.
 */
public abstract class Presenter<T> {
    protected final UseCaseScheduler scheduler = UseCaseScheduler.getInstance();
    private T view;


    public Presenter() {}

    public Presenter(T view) {
        this.view = view;
    }

    public T getView() {
        return view;
    }

    public void setView(T view) {
        this.view = view;
    }
}