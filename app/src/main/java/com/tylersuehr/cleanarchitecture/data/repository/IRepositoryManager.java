package com.tylersuehr.cleanarchitecture.data.repository;
import com.tylersuehr.cleanarchitecture.data.models.*;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 8/28/2016.
 *
 * This is the generic functionality we will need to ACCESS all of our
 * different pieces of data.
 */
public interface IRepositoryManager {
    IRepository<User> getUsers();
    IRepository<Phone> getPhones();
    IRepository<Tablet> getTablets();
    IRepository<Watch> getWatches();
}