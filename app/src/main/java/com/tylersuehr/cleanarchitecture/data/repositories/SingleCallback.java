package com.tylersuehr.cleanarchitecture.data.repositories;

/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 7/3/2017.
 */
public interface SingleCallback<T> {
    void onSingleLoaded(T object);
    void onNotAvailable(Exception ex);
}