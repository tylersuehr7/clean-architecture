package com.tylersuehr.cleanarchitecture.data.repositories;
import android.database.sqlite.SQLiteDatabase;
/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 7/3/2017.
 *
 * <b>Summary</b>
 * Defines a way to access the {@link SQLiteDatabase}. This is used to decouple anything
 * that will provide an instance of {@link SQLiteDatabase} for us to use.
 *
 * NOTE: Can be used for production and testing databases.
 */
public interface IDatabaseClient {
    SQLiteDatabase getDb();
}