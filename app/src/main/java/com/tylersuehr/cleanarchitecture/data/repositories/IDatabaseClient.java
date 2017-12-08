package com.tylersuehr.cleanarchitecture.data.repositories;
import android.database.sqlite.SQLiteDatabase;
/**
 * Copyright 2017 Tyler Suehr
 *
 * Defines a way to access the {@link SQLiteDatabase}. This is used to decouple anything
 * that will provide an instance of {@link SQLiteDatabase} for us to use.
 *
 * Note: Helps with using both production and testing databases.
 *
 * @author Tyler Suehr
 * @version 1.0
 */
public interface IDatabaseClient {
    SQLiteDatabase getDb();
}