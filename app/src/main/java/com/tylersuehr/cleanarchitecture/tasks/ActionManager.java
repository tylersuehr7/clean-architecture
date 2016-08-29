package com.tylersuehr.cleanarchitecture.tasks;
import java.util.ArrayList;
import java.util.Collection;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 8/28/2016.
 */
public class ActionManager {
    static Collection<IAction> observers;


    public ActionManager() {}

    public static void execute(Action action) {
        action.setObservers(observers);
        action.execute();
    }

    public static void register(IAction observer) {
        if (observers == null) {
            observers = new ArrayList<>();
        }
        observers.add(observer);
    }

    public static void unregister(IAction observer) {
        if (observers != null) {
            observers.remove(observer);
        }
    }
}