package com.tylersuehr.cleanarchitecture.data.models;
import android.content.ContentValues;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 8/28/2016.
 */
public class Tablet extends Technology {
    public static final int NORMAL = 0;
    public static final int LARGE = 1;
    public static final int XLARGE = 2;
    private int size;


    public Tablet() {}

    @Override
    public ContentValues toContentValues() {
        ContentValues values = super.toContentValues();
        values.put("size", size);
        return values;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}