package com.tylersuehr.cleanarchitecture.data.exceptions;

/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 7/3/2017.
 *
 * To be created and thrown when a query for a list is empty.
 */
public class EmptyQueryException extends QueryException {
    public EmptyQueryException(String table) {
        super("Querying table " + table + " return 0 results!");
    }
}