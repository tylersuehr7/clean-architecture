package com.tylersuehr.cleanarchitecture.data.mappers;
import android.database.Cursor;
import com.tylersuehr.cleanarchitecture.data.models.Entity;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 8/28/2016.
 */
public interface IMapper<T extends Entity> {
    T map(Cursor c);
}