package com.tylersuehr.cleanarchitecture.data.models;
import android.content.ContentValues;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 8/28/2016.
 */
public class Watch extends Technology {
    private boolean circular = false;


    public Watch() {}

    @Override
    public ContentValues toContentValues() {
        ContentValues values = super.toContentValues();
        values.put("isCircular", circular);
        return values;
    }

    public void setCircular(boolean value) {
        this.circular = value;
    }

    public boolean isCircular() {
        return circular;
    }
}