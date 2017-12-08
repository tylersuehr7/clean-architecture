package com.tylersuehr.cleanarchitecture.domain;

/**
 * Copyright 2017 Tyler Suehr
 *
 * This defines callbacks for our {@link UseCase} executions.
 *
 * @author Tyler Suehr
 * @version 1.0
 */
public interface UseCaseCallback<Response> {
    /**
     * Called when a use case successfully executes.
     * @param response Contains a response from the use case
     */
    void onSuccess(Response response);

    /**
     * Called when a use case fails to execute.
     * @param ex Contains error information
     */
    void onFailure(Exception ex);
}