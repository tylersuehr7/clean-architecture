package com.tylersuehr.cleanarchitecture.data.models;
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

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}