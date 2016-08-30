package com.tylersuehr.cleanarchitecture.tasks;
import android.content.Context;
import com.tylersuehr.cleanarchitecture.data.repository.IRepositoryManager;
import java.util.ArrayList;
import java.util.Collection;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 8/29/2016.
 *
 * This task allows us to find all objects from all tables in our database
 * and return them as a collection of objects.
 */
public class FindAllTask extends CollectionTask {
    public FindAllTask(IRepositoryManager m, Context c) {
        super(m, c);
    }

    @Override
    protected Collection<Object> doInBackground(Object... params) {
        // Collect all objects from users, phones, tablets, and watches
        Collection<Object> objects = new ArrayList<>();
        objects.addAll(manager.getUsers().find(null, null, null));
        objects.addAll(manager.getPhones().find(null, null, null));
        objects.addAll(manager.getTablets().find(null, null, null));
        objects.addAll(manager.getWatches().find(null, null, null));
        return objects;
    }
}