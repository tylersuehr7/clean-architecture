package com.tylersuehr.cleanarchitecture.data.models;
import android.content.ContentValues;
import java.util.ArrayList;
import java.util.Collection;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 8/28/2016.
 */
public class Phone extends Technology {
    private Collection<String> eligibleProviders;


    public Phone() {
        this.eligibleProviders = new ArrayList<>();
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues values = super.toContentValues();
        values.put("providers", eligibleProviders.toString().replace("[", "").replace("]", ""));
        return values;
    }

    public Collection<String> getEligibleProviders() {
        return eligibleProviders;
    }

    public Phone addEligibleProvider(String name) {
        this.eligibleProviders.add(name);
        return this;
    }
}