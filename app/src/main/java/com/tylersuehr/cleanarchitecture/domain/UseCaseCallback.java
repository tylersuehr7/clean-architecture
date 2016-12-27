package com.tylersuehr.cleanarchitecture.domain;

/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 12/25/2016.
 */
public interface UseCaseCallback<V> {
    void onSuccess(V response);
    void onFailure(Object reason);
}