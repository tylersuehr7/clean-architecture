package com.tylersuehr.cleanarchitecture.ui;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 7/4/2017.
 *
 * Utility to help with manipulating Intent actions.
 */
public final class Navigator {
    private final Activity activity;


    private Navigator(Activity activity) {
        this.activity = activity;
    }

    public static Navigator from(Activity activity) {
        return new Navigator(activity);
    }

    public INavResult to(Class<?> klass) {
        return new NavResultImpl(new Intent(activity, klass));
    }

    public void finish() {
        ActivityCompat.finishAfterTransition(activity);
    }


    public interface INavResult {
        Navigator go();
    }

    private final class NavResultImpl implements INavResult {
        private final Intent intent;

        private NavResultImpl(Intent intent) {
            this.intent = intent;
        }

        @Override
        public Navigator go() {
            ActivityOptionsCompat op = ActivityOptionsCompat.makeSceneTransitionAnimation(activity);
            activity.startActivity(intent, op.toBundle());
            return Navigator.this;
        }
    }
}