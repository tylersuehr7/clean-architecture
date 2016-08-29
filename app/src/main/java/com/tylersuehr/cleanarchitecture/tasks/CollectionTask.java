package com.tylersuehr.cleanarchitecture.tasks;
import android.content.Context;
import android.os.AsyncTask;
import com.tylersuehr.cleanarchitecture.data.repository.IRepositoryManager;
import java.util.Collection;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 8/29/2016.
 */
public abstract class CollectionTask extends AsyncTask<Object, Void, Collection<Object>> {
    protected IRepositoryManager manager;
    protected Context c;
    private ITask callback;


    public CollectionTask(IRepositoryManager manager, Context c) {
        this.manager = manager;
        this.c = c;
    }

    @Override
    protected void onPostExecute(Collection<Object> data) {
        if (callback != null) {
            callback.onTaskCompleted(data);
        }
    }

    public CollectionTask setCallback(ITask callback) {
        this.callback = callback;
        return this;
    }
}