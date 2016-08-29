package com.tylersuehr.cleanarchitecture.tasks;
import com.tylersuehr.cleanarchitecture.data.models.Entity;
import java.util.List;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 8/28/2016.
 */
public interface IAction {
    void onLoaded(Entity entity);
    void onLoaded(List<? extends Entity> entities);
}