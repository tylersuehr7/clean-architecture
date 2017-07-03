package com.tylersuehr.cleanarchitecture.domain;

/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 7/3/2017.
 *
 * <b>Summary</b>
 * Defines callbacks for the use case execution.
 */
public interface UseCaseCallback<V> {
    /**
     * Called when a use case successfully executes.
     * @param response Contains a response from the use case
     */
    void onSuccess(V response);

    /**
     * Called when a use case fails to execute.
     * @param ex Contains error information
     */
    void onFailure(Exception ex);
}