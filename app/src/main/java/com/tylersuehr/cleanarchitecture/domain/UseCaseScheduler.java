package com.tylersuehr.cleanarchitecture.domain;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 12/25/2016.
 */
public final class UseCaseScheduler {
    private static UseCaseScheduler instance;
    private static ThreadPoolExecutor executor;
    private final Handler handler = new Handler(Looper.getMainLooper());


    private UseCaseScheduler() {}
    public static synchronized UseCaseScheduler getInstance() {
        if (instance == null) {
            instance = new UseCaseScheduler();
            executor = getValidInstance();
        }
        return instance;
    }

    public <T, V> void execute(final UseCase<T, V> useCase, T request, UseCaseCallback<V> callback) {
        useCase.setCallback(new UICallback<V>(handler, callback));
        useCase.setRequest(request);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                useCase.execute();
            }
        });
    }

    public static ThreadPoolExecutor getValidInstance() {
        if (executor == null) {
            final int SIZE = 2;
            final int MAX = 4;
            final int TIMEOUT = 30;
            executor = new ThreadPoolExecutor(SIZE, MAX, TIMEOUT, TimeUnit.SECONDS,
                    new ArrayBlockingQueue<Runnable>(MAX));
        }
        return executor;
    }


    private static final class UICallback<V> implements UseCaseCallback<V> {
        private final UseCaseCallback<V> callback;
        private final Handler handler;


        private UICallback(Handler handler, UseCaseCallback<V> callback) {
            this.handler = handler;
            this.callback = callback;
        }

        @Override
        public void onSuccess(final V response) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onSuccess(response);
                }
            });
        }

        @Override
        public void onFailure(final Object reason) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onFailure(reason);
                }
            });
        }
    }
}