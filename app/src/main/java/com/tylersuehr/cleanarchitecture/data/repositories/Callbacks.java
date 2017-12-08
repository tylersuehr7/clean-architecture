package com.tylersuehr.cleanarchitecture.data.repositories;

import com.tylersuehr.cleanarchitecture.data.models.Entity;

import java.util.List;

/**
 * Copyright © 2017 Tyler Suehr
 *
 * @author Tyler Suehr
 * @version 1.0
 */
public interface Callbacks {
    /**
     * Defines an interface for handling an error callback.
     */
    interface IError {
        void onNotAvailable(Exception ex);
    }

    /**
     * Defines an interface for handling a single response callback.
     */
    interface ISingle<T extends Entity> extends IError {
        void onSingleLoaded(T value);
    }

    /**
     * Defines an interface for handling a list response callback.
     */
    interface IList<T extends Entity> extends IError {
        void onListLoaded(List<T> values);
    }
}