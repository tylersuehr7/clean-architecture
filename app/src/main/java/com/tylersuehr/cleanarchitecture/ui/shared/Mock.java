package com.tylersuehr.cleanarchitecture.ui.shared;
import android.support.annotation.DrawableRes;
import com.tylersuehr.cleanarchitecture.R;
import java.util.Random;

/**
 * Copyright 2017 Tyler Suehr
 *
 * Utility that provides mock data.
 *
 * @author Tyler Suehr
 * @version 1.0
 */
public final class Mock {
    private static final String[] pics = {
            "img_profile_0", "img_profile_2", "img_profile_3",
            "imp_profile_4", "img_profile_5"
    };

    public static String randomImage() {
        final Random rnd = new Random();
        return pics[rnd.nextInt(pics.length - 1)];
    }
}