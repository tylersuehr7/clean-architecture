package com.tylersuehr.cleanarchitecture.tasks;
import android.util.Log;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 8/30/2016.
 *
 * This is another layer for our tasks. It allows us to register a constant
 * callback and execute tasks through the use of a static method.
 */
public class TaskExecutor {
    private static final String TAG = "TASK >";
    private static ITask callback;


    public TaskExecutor() {}

    public static void register(ITask callback) {
        TaskExecutor.callback = callback;
        Log.i(TAG, "Executor registered!");
    }

    public static void unregister(ITask callback) {
        if (TaskExecutor.callback == callback) {
            TaskExecutor.callback = null;
            Log.i(TAG, "Executor unregistered!");
        }
    }

    public static void execute(CollectionTask task) {
        if (callback != null) {
            task.setCallback(callback);
        }
        task.execute();
    }
}