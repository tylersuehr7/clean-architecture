package com.tylersuehr.cleanarchitecture.data.repositories;
import java.util.List;
/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 7/3/2017.
 */
public interface ListCallback<T> {
    void onListLoaded(List<T> objects);
    void onNotAvailable(Exception ex);
}