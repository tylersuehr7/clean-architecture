package com.tylersuehr.cleanarchitecture.domain;

/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 7/3/2017.
 *
 * <b>Summary</b>
 * This is the base 'executable' task to push work into a worker thread quickly
 * and handle using the given request and return a response on the UI thread.
 *
 * NOTE: All use cases are executed with {@link UseCaseScheduler}.
 */
public abstract class UseCase<T, V> {
    private UseCaseCallback<V> callback;
    private T request;


    public UseCase() {}

    protected abstract void onExecute();

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