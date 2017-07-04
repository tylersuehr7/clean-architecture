package com.tylersuehr.cleanarchitecture.ui.shared;
import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.view.View;
/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 7/4/2017.
 *
 * Utility to help with alerts such as SnackBars.
 */
public final class AlertUtils {
    public static void confirm(Activity a, String msg, String action, final Runnable callback) {
        View v = a.findViewById(android.R.id.content);
        Snackbar snack = Snackbar.make(v, msg, Snackbar.LENGTH_SHORT);
        snack.setAction(action, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Trigger callback when action is pressed
                callback.run();
            }
        });
        snack.show();
    }
}