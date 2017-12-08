package com.tylersuehr.cleanarchitecture.ui.shared;
import android.app.Activity;
import android.os.Build;
import android.support.annotation.TransitionRes;
import android.transition.Transition;
import android.transition.TransitionInflater;

/**
 * Copyright 2017 Tyler Suehr
 *
 * Utility to help with various activity transitions.
 *
 * @author Tyler Suehr
 * @version 1.0
 */
public final class TransUtils {
    public static void setEnter(Activity activity, @TransitionRes int res) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Transition trans = TransitionInflater.from(activity).inflateTransition(res);
            activity.getWindow().setEnterTransition(trans);
        }
    }

    public static void setReturn(Activity activity, @TransitionRes int res) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Transition trans = TransitionInflater.from(activity).inflateTransition(res);
            activity.getWindow().setReturnTransition(trans);
        }
    }

    public static void setExit(Activity activity, @TransitionRes int res) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Transition trans = TransitionInflater.from(activity).inflateTransition(res);
            activity.getWindow().setExitTransition(trans);
        }
    }
}