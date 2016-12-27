package com.tylersuehr.cleanarchitecture.domain;

/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 12/25/2016.
 */
public abstract class UseCase<T, V> {
    private UseCaseCallback<V> callback;
    private T request;


    public UseCase() {}

    protected abstract void execute();

    public UseCaseCallback<V> getCallback() {
        return callback;
    }

    void setCallback(UseCaseCallback<V> callback) {
        this.callback = callback;
    }

    public T getRequest() {
        return request;
    }

    void setRequest(T request) {
        this.request = request;
    }
}