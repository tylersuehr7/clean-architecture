package com.tylersuehr.cleanarchitecture.data.models;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 8/28/2016.
 */
public class Watch extends Technology {
    private boolean circular = false;


    public Watch() {}

    public void setCircular(boolean value) {
        this.circular = value;
    }

    public boolean isCircular() {
        return circular;
    }
}