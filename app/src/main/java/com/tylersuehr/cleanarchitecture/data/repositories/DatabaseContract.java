package com.tylersuehr.cleanarchitecture.data.repositories;

/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 7/3/2017.
 *
 * Concretely defines the structure of our local database tables.
 */
public final class DatabaseContract {
    public static abstract class People {
        public static final String NAME = "people";
        public static final String COL_ID = "pId";
        public static final String COL_FIRST_NAME = "pFirst";
        public static final String COL_LAST_NAME = "pLast";
        public static final String COL_IMAGE = "pImage";
        public static final String COL_AGE = "pAge";
    }
}