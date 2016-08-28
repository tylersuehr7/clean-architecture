package com.tylersuehr.cleanarchitecture.data.repository;
import android.content.Context;
import com.tylersuehr.cleanarchitecture.data.DatabaseManager;
import com.tylersuehr.cleanarchitecture.data.mappers.PhoneMapper;
import com.tylersuehr.cleanarchitecture.data.mappers.TabletMapper;
import com.tylersuehr.cleanarchitecture.data.mappers.UserMapper;
import com.tylersuehr.cleanarchitecture.data.mappers.WatchMapper;
import com.tylersuehr.cleanarchitecture.data.models.Phone;
import com.tylersuehr.cleanarchitecture.data.models.Tablet;
import com.tylersuehr.cleanarchitecture.data.models.User;
import com.tylersuehr.cleanarchitecture.data.models.Watch;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 8/28/2016.
 *
 * This is our implementation of {@link IRepositoryManager}. This allows
 * us to have access to all the local repositories for each model we have
 * created.
 *
 * NOTE: This uses lazy initialization for our repositories. Don't instantiate
 *       a repository unless we are actually going to use it.
 */
public class RepositoryManager implements IRepositoryManager {
    private static volatile RepositoryManager instance;
    private IRepository<Phone> phones;
    private IRepository<Tablet> tablets;
    private IRepository<Watch> watches;
    private IRepository<User> users;
    private Context c;


    private RepositoryManager() {}
    public static RepositoryManager getInstance(Context c) {
        if (instance == null) {
            synchronized (RepositoryManager.class) {
                if (instance == null) {
                    instance = new RepositoryManager();
                    instance.c = c;
                }
            }
        }
        return instance;
    }

    @Override
    public IRepository<Phone> getPhones() {
        if (phones == null) {
            phones = new Repository<>(c, DatabaseManager.TABLE_PHONES, new PhoneMapper());
        }
        return phones;
    }

    @Override
    public IRepository<User> getUsers() {
        if (users == null) {
            users = new Repository<>(c, DatabaseManager.TABLE_USERS, new UserMapper());
        }
        return users;
    }

    @Override
    public IRepository<Tablet> getTablets() {
        if (tablets == null) {
            tablets = new Repository<>(c, DatabaseManager.TABLE_TABLETS, new TabletMapper());
        }
        return tablets;
    }

    @Override
    public IRepository<Watch> getWatches() {
        if (watches == null) {
            watches = new Repository<>(c, DatabaseManager.TABLE_WATCHES, new WatchMapper());
        }
        return watches;
    }
}