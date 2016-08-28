package com.tylersuehr.cleanarchitecture;
import android.database.MatrixCursor;
import java.util.UUID;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 8/28/2016.
 */
public class TestWatchCursor extends MatrixCursor {
    private static final String[] COLUMNS = { "id", "brand", "model", "price", "iconResName", "isCircular" };


    public TestWatchCursor(String brand) {
        super(COLUMNS, COLUMNS.length);
        String[] brands = brand.split(",");
        for (String b : brands) {
            addRow(new Object[] {UUID.randomUUID(), b, "Test", 199.99, null, 1 });
        }
    }
}