package com.tylersuehr.cleanarchitecture.data.exceptions;

/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 7/3/2017.
 */
public class EmptyQueryException extends QueryException {
    public EmptyQueryException(String table) {
        super("Querying table " + table + " return 0 results!");
    }
}