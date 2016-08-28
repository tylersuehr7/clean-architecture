package com.tylersuehr.cleanarchitecture.data;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.tylersuehr.cleanarchitecture.R;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 8/28/2016.
 *
 * This handles the creation/update of our local database. It also manages
 * connections to it as well.
 */
public final class DatabaseManager {
    public static final String TABLE_PHONES = "phones";
    public static final String TABLE_TABLETS = "tablets";
    public static final String TABLE_WATCHES = "watches";
    public static final String TABLE_USERS = "users";
    private static final String DB_NAME = "database_ca";
    private DatabaseHelper helper;
    private SQLiteDatabase db;


    public DatabaseManager(Context c) {
        this.helper = new DatabaseHelper(c);
        this.db = helper.getWritableDatabase();
    }

    /**
     * Closes all connections to our database.
     */
    public void close() {
        this.db.close();
        this.helper.close();
    }

    /**
     * Returns a valid instance of our database.
     * @return {@link SQLiteDatabase}
     */
    public SQLiteDatabase getDb() {
        return db;
    }


    // Helper to manage the creation of our local database
    private class DatabaseHelper extends SQLiteOpenHelper {
        private Resources res;


        public DatabaseHelper(Context c) {
            super(c, DB_NAME, null, 1);
            this.res = c.getResources();
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(res.getString(R.string.table_phones_create));
            db.execSQL(res.getString(R.string.table_tablets_create));
            db.execSQL(res.getString(R.string.table_watches_create));
            db.execSQL(res.getString(R.string.table_users_create));
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("DROP TABLE IF EXISTS [" + TABLE_PHONES + "]");
            db.execSQL("DROP TABLE IF EXISTS [" + TABLE_TABLETS + "]");
            db.execSQL("DROP TABLE IF EXISTS [" + TABLE_WATCHES + "]");
            db.execSQL("DROP TABLE IF EXISTS [" + TABLE_USERS + "]");
            onCreate(db);
        }
    }
}