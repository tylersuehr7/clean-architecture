package com.tylersuehr.cleanarchitecture.ui.models;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 8/30/2016.
 */
public class HelpItem {
    private String description;
    private String title;


    public HelpItem(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }
}