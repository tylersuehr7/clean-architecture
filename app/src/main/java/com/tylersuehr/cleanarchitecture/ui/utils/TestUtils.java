package com.tylersuehr.cleanarchitecture.ui.utils;
import com.tylersuehr.cleanarchitecture.data.models.Car;
import com.tylersuehr.cleanarchitecture.data.models.Phone;
import com.tylersuehr.cleanarchitecture.data.models.User;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 12/26/2016.
 */
public final class TestUtils {
    public static User createUser() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("johndoe123");
        user.setPassword("P@ssw0rd");
        return user;
    }

    public static Phone createPhone() {
        Phone phone = new Phone();
        phone.setMake("Google");
        phone.setModel("Nexus 6P");
        phone.setCarrier("Sprint");
        phone.setPrice(499.99);
        return phone;
    }

    public static Car createCar() {
        Car car = new Car();
        car.setMake("Chevrolet");
        car.setModel("Chevelle");
        car.setColor("red");
        car.setYear(1969);
        car.setUsed(true);
        return car;
    }
}