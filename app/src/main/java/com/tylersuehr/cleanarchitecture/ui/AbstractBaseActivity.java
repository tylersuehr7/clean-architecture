package com.tylersuehr.cleanarchitecture.ui;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Copyright © 2017 Tyler Suehr
 *
 * The parent class for any Activity in this project. This affords the most basic
 * implementation of {@link IBaseView} to be used with any presenter.
 *
 * @author Tyler Suehr
 * @version 1.0
 */
public abstract class AbstractBaseActivity extends AppCompatActivity implements IBaseView {
    @Override
    public void onShowMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isDeviceOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        final boolean online = (nInfo != null && nInfo.isConnected());
        if (!online) { onDeviceOffline(); }
        return online;
    }

    /**
     * Called when a call to {@link #isDeviceOnline()} returns false.
     * Override to do something neat when device is offline.
     */
    protected void onDeviceOffline() {}
}