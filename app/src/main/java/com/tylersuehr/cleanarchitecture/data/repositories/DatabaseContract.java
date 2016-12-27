package com.tylersuehr.cleanarchitecture.data.repositories;

/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 12/25/2016.
 */
public final class DatabaseContract {
    public static final String NAME = "clean_architecture_db";
    public static final int VERSION = 1;


    public static abstract class Users {
        public static final String NAME = "users";
        public static final String COL_ID = "id";
        public static final String COL_FIRSTNAME = "firstName";
        public static final String COL_LASTNAME = "lastName";
        public static final String COL_USERNAME = "username";
        public static final String COL_PASSWORD = "password";
        static final String CREATE = "CREATE TABLE [users] (" +
                "[id] TEXT UNIQUE PRIMARY KEY," +
                "[firstName] TEXT," +
                "[lastName] TEXT," +
                "[username] TEXT," +
                "[password] TEXT);";
    }

    public static abstract class Phones {
        public static final String NAME = "phones";
        public static final String COL_ID = "id";
        public static final String COL_MAKE = "make";
        public static final String COL_MODEL = "model";
        public static final String COL_CARRIER = "carrier";
        public static final String COL_PRICE = "price";
        static final String CREATE = "CREATE TABLE [phones] (" +
                "[id] TEXT UNIQUE PRIMARY KEY," +
                "[make] TEXT," +
                "[model] TEXT," +
                "[carrier] TEXT," +
                "[price] REAL);";
    }

    public static abstract class Cars {
        public static final String NAME = "cars";
        public static final String COL_ID = "id";
        public static final String COL_MAKE = "make";
        public static final String COL_MODEL = "model";
        public static final String COL_COLOR = "color";
        public static final String COL_YEAR = "year";
        public static final String COL_IS_USED = "isUsed";
        static final String CREATE = "CREATE TABLE [cars] (" +
                "[id] TEXT UNIQUE PRIMARY KEY," +
                "[make] TEXT," +
                "[model] TEXT," +
                "[color] TEXT," +
                "[year] INTEGER," +
                "[isUsed] INTEGER DEFAULT 0);";
    }
}