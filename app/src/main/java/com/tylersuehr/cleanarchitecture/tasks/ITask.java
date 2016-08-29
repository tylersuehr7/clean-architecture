package com.tylersuehr.cleanarchitecture.tasks;
import java.util.Collection;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 8/28/2016.
 */
public interface ITask {
    void onTaskCompleted(Collection<Object> objects);
}