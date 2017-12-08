package com.tylersuehr.cleanarchitecture.ui;

/**
 * Copyright © 2017 Tyler Suehr
 *
 * Defines the core methods for any view in this project.
 *
 * @author Tyler Suehr
 * @version 1.0
 */
public interface IBaseView {
    /**
     * Shows a message to the user on the screen.
     * @param msg Message to display
     */
    void onShowMsg(String msg);

    /**
     * Checks if the current device is online.
     * @return True if device is online
     */
    boolean isDeviceOnline();
}