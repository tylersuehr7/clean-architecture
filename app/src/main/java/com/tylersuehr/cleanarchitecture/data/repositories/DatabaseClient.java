package com.tylersuehr.cleanarchitecture.data.repositories;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import static com.tylersuehr.cleanarchitecture.data.repositories.DatabaseContract.*;
/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 7/3/2017.
 *
 * This handles our local database management and allows us to interface with the
 * {@link SQLiteDatabase}.
 *
 * We decouple this object by using {@link IDatabaseClient} to provide access to the database.
 *
 * <b>Important</b>
 * We use a singleton so that we prevent creating multiple instances of {@link SQLiteDatabase}
 * and causing a memory leakage.
 */
public final class DatabaseClient extends SQLiteOpenHelper implements IDatabaseClient {
    private static final String DB_NAME = "db_clean_architecture";
    private static final int DB_VERSION = 1;
    private static volatile DatabaseClient instance;
    private final SQLiteDatabase db;


    private DatabaseClient(Context c) {
        super(c, DB_NAME, null, DB_VERSION);
        this.db = getWritableDatabase();
    }

    public static synchronized DatabaseClient getInstance(Context c) {
        if (instance == null) {
            instance = new DatabaseClient(c);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createPeopleTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS [" + People.NAME + "];");
        onCreate(db);
    }

    @Override
    public SQLiteDatabase getDb() {
        return db;
    }

    /**
     * Creates the 'people' SQLite database table.
     * @param db {@link SQLiteDatabase}
     */
    private void createPeopleTable(@NonNull SQLiteDatabase db) {
        db.execSQL("CREATE TABLE [" + People.NAME + "]([" +
                People.COL_ID + "] TEXT UNIQUE PRIMARY KEY,[" +
                People.COL_FIRST_NAME + "] TEXT NOT NULL,[" +
                People.COL_LAST_NAME + "] TEXT NOT NULL,[" +
                People.COL_IMAGE + "] TEXT NOT NULL,[" +
                People.COL_AGE + "] INTEGER);");
    }
}