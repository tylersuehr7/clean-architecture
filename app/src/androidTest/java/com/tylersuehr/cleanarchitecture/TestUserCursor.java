package com.tylersuehr.cleanarchitecture;
import android.database.MatrixCursor;
import java.util.UUID;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 8/28/2016.
 */
public class TestUserCursor extends MatrixCursor {
    private static final String[] columns = { "id", "firstName", "lastName", "email" };


    public TestUserCursor(String name) {
        super(columns, columns.length);
        String[] names = name.split(",");
        for (String n : names) {
            addRow(new Object[] {UUID.randomUUID(), n, "Jackson", n + "@gmail.com" });
        }
    }
}