package com.tylersuehr.cleanarchitecture.ui;
import java.util.Random;
/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 7/4/2017.
 *
 * Utility, that can be used anywhere, that provides mock data.
 */
public final class Mock {
    public static String randomImage() {
        String[] images = new String[] { "img_profile_0", "img_profile_1", "img_profile_2",
                "img_profile_3", "img_profile_4", "img_profile_5" };
        return images[new Random().nextInt(images.length)];
    }
}