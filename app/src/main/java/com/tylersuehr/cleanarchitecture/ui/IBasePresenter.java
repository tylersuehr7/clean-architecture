package com.tylersuehr.cleanarchitecture.ui;

/**
 * Copyright © 2017 Tyler Suehr
 *
 * Defines the core methods for any presenter in this project.
 *
 * @author Tyler Suehr
 * @version 1.0
 */
public interface IBasePresenter<IView extends IBaseView> {
    /**
     * Gets the presenter's current view.
     * @return {@link IView}
     */
    IView getView();

    /**
     * Attaches the given view to the presenter.
     * @param view {@link IView}
     */
    void attach(IView view);

    /**
     * Detaches the presenter's current view.
     */
    void detach();

    /**
     * Stops any active or pending use case executions.
     */
    void stopTasks();
}