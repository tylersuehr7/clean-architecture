package com.tylersuehr.cleanarchitecture.data.repositories;

import com.tylersuehr.cleanarchitecture.data.models.Entity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 12/25/2016.
 */
final class Repository<T extends Entity> implements IRepository<T> {
    private final IRepository<T> local;
    private boolean invalidCache = false;
    private Map<Object, T> cache;


    Repository(IRepository<T> local) {
        this.local = local;
    }

    @Override
    public void add(T entity) {
        local.add(entity);

        if (cache == null) {
            cache = new HashMap<>();
        }
        cache.put(entity.getId(), entity);
    }

    @Override
    public void update(T entity) {
        local.update(entity);

        if (cache == null) {
            cache = new HashMap<>();
        }
        cache.put(entity.getId(), entity);
    }

    @Override
    public void remove(T entity) {
        local.remove(entity);

        if (cache != null) {
            if (entity == null) {
                cache.clear();
            } else {
                cache.remove(entity.getId());
            }
        }
    }

    @Override
    public void findById(String id, final OnEntityCallback<T> callback) {
        if (validCache()) {
            T entity = cache.get(id);
            if (entity != null) {
                callback.onEntityLoaded(entity);
                return;
            }
        }

        local.findById(id, new OnEntityCallback<T>() {
            @Override
            public void onEntityLoaded(T entity) {
                // Add to cache
                collectInCache(entity);
                callback.onEntityLoaded(entity);
            }

            @Override
            public void onNotAvailable() {
                callback.onNotAvailable();
            }
        });
    }

    @Override
    public void findAllSQL(String sql, final OnEntitiesCallback<T> callback) {
        // Only perform on the local data source
        local.findAllSQL(sql, new OnEntitiesCallback<T>() {
            @Override
            public void onEntitiesLoaded(List<T> entities) {
                collectInCache(entities);
                callback.onEntitiesLoaded(entities);
            }

            @Override
            public void onNotAvailable() {
                callback.onNotAvailable();
            }
        });
    }

    @Override
    public void findAll(String where, String order, String limit, final OnEntitiesCallback<T> callback) {
        // Only perform on the local data source
        local.findAll(where, order, limit, new OnEntitiesCallback<T>() {
            @Override
            public void onEntitiesLoaded(List<T> entities) {
                collectInCache(entities);
                callback.onEntitiesLoaded(entities);
            }

            @Override
            public void onNotAvailable() {
                callback.onNotAvailable();
            }
        });
    }

    @Override
    public void findAll(final OnEntitiesCallback<T> callback) {
        if (validCache()) {
            callback.onEntitiesLoaded(new LinkedList<>(cache.values()));
            return;
        }

        local.findAll(new OnEntitiesCallback<T>() {
            @Override
            public void onEntitiesLoaded(List<T> entities) {
                collectInCache(entities);
                callback.onEntitiesLoaded(entities);
            }

            @Override
            public void onNotAvailable() {
                callback.onNotAvailable();
            }
        });
    }

    public void invalidateCache() {
        this.invalidCache = true;
    }

    private void collectInCache(List<T> entities) {
        if (cache == null) {
            cache = new HashMap<>();
        }
        for (T entity : entities) {
            cache.put(entity.getId(), entity);
        }
    }

    private void collectInCache(T entity) {
        if (cache == null) {
            cache = new HashMap<>();
        }
        cache.put(entity.getId(), entity);
    }

    private boolean validCache() {
        return (cache != null && !cache.isEmpty() && !invalidCache);
    }
}