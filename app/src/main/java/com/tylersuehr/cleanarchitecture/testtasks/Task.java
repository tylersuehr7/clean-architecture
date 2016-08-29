package com.tylersuehr.cleanarchitecture.testtasks;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 8/28/2016.
 */
public abstract class Task<T> {
    protected abstract T doInBackground();
    protected abstract void onPostExecute(T data);


    public Task() {}

    public void execute() {

    }
}