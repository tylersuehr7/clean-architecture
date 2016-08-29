package com.tylersuehr.cleanarchitecture.tasks;
import android.os.AsyncTask;
import com.tylersuehr.cleanarchitecture.data.models.Entity;
import java.util.Collection;
import java.util.List;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 8/28/2016.
 */
public abstract class Action<T> extends AsyncTask<Void, Void, T> {
    private Collection<IAction> observers;


    @Override
    protected void onPostExecute(T t) {
        super.onPostExecute(t);

        if (observers != null) {
            if (t instanceof List) {
                for (IAction a : observers) {
                    a.onLoaded((List<? extends Entity>)t);
                }
            } else if (t instanceof Entity) {
                for (IAction a : observers) {
                    a.onLoaded((Entity)t);
                }
            }
        }
    }

    public void setObservers(Collection<IAction> observers) {
        this.observers = observers;
    }
}