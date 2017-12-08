package com.tylersuehr.cleanarchitecture.data.repositories;

/**
 * Copyright 2017 Tyler Suehr
 *
 * Concretely defines the structure of our local database tables.
 *
 * @author Tyler Suehr
 * @version 1.0
 */
public final class DatabaseContract {
    /* Cannot instantiate */
    private DatabaseContract() {}

    public static abstract class People {
        public static final String NAME = "people";
        public static final String COL_ID = "pId";
        public static final String COL_FIRST_NAME = "pFirst";
        public static final String COL_LAST_NAME = "pLast";
        public static final String COL_IMAGE = "pImage";
        public static final String COL_AGE = "pAge";
    }
}