package com.tylersuehr.cleanarchitecture.data.models;
import android.content.ContentValues;
import android.content.res.Resources;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 8/28/2016.
 *
 * This is the basis for all of our technological models.
 */
public class Technology extends Entity {
    private String iconResName;
    private String brand;
    private String model;
    private double price;


    public Technology() {}

    @Override
    public ContentValues toContentValues() {
        ContentValues values = super.toContentValues();
        values.put("brand", brand);
        values.put("model", model);
        values.put("price", price);
        values.put("iconResName", iconResName);
        return values;
    }

    public void setTechnology(Technology tech) {
        this.brand = tech.brand;
        this.model = tech.model;
        this.price = tech.price;
        this.iconResName = tech.iconResName;
    }

    public int getIcon(Resources res) {
        return res.getIdentifier(iconResName, "drawable", "drawable");
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getIconResName() {
        return iconResName;
    }

    public void setIconResName(String iconResName) {
        this.iconResName = iconResName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}