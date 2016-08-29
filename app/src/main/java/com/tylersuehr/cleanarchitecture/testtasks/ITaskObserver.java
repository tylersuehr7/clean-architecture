package com.tylersuehr.cleanarchitecture.testtasks;
import com.tylersuehr.cleanarchitecture.data.models.Entity;
import java.util.List;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 8/28/2016.
 */
public interface ITaskObserver {
    void onTaskCompleted(Entity entity);
    void onTaskCompleted(List<? extends Entity> entities);
}