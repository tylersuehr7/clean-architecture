package com.tylersuehr.cleanarchitecture.data.exceptions;

/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 7/3/2017.
 *
 * To be created and thrown when a query fails.
 */
public class QueryException extends RuntimeException {
    public QueryException(String table) {
        super("Error when querying table " + table);
    }

    public QueryException(String table, Throwable cause) {
        super("Error when querying table " + table, cause);
    }
}