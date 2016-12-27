package com.tylersuehr.cleanarchitecture.data.repositories;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Closeable;

import static com.tylersuehr.cleanarchitecture.data.repositories.DatabaseContract.*;
import static com.tylersuehr.cleanarchitecture.data.repositories.DatabaseContract.NAME;
import static com.tylersuehr.cleanarchitecture.data.repositories.DatabaseContract.VERSION;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 12/25/2016.
 */
final class DatabaseClient implements Closeable {
    private static volatile DatabaseClient instance;
    private final DatabaseHelper helper;
    private final SQLiteDatabase db;


    private DatabaseClient(Context c) {
        this.helper = new DatabaseHelper(c);
        this.db = helper.getWritableDatabase();
    }

    static DatabaseClient getInstance(Context c) {
        if (instance == null) {
            synchronized (DatabaseClient.class) {
                if (instance == null) {
                    instance = new DatabaseClient(c);
                }
            }
        }
        return instance;
    }

    @Override
    public void close() {
        this.db.close();
        this.helper.close();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        close();
    }

    public SQLiteDatabase getDb() {
        return db;
    }


    private class DatabaseHelper extends SQLiteOpenHelper {
        private DatabaseHelper(Context c) {
            super(c, NAME, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(Users.CREATE);
            db.execSQL(Phones.CREATE);
            db.execSQL(Cars.CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS [" + Users.NAME + "];");
            db.execSQL("DROP TABLE IF EXISTS [" + Phones.NAME + "];");
            db.execSQL("DROP TABLE IF EXISTS [" + Cars.NAME + "];");
            onCreate(db);
        }
    }
}