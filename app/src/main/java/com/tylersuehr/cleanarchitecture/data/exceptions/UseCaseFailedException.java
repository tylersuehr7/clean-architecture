package com.tylersuehr.cleanarchitecture.data.exceptions;
import com.tylersuehr.cleanarchitecture.domain.UseCase;
/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 7/3/2017.
 *
 * To be created and thrown when a use case fails to execute successfully.
 */
public class UseCaseFailedException extends RuntimeException {
    public UseCaseFailedException(UseCase useCase) {
        super(useCase.getClass().getSimpleName() + " failed to execute!");
    }

    public UseCaseFailedException(UseCase useCase, Throwable cause) {
        super(useCase.getClass().getSimpleName() + " failed to execute!", cause);
    }
}