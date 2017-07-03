package com.tylersuehr.cleanarchitecture.data.models;

/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 7/3/2017.
 */
public class Person extends Entity {
    private String firstName;
    private String lastName;
    private String image;
    private int age;


    public Person() {}

    @Override
    public String getId() {
        return (String)super.getId();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}