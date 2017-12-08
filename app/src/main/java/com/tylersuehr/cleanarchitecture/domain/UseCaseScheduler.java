package com.tylersuehr.cleanarchitecture.domain;
import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Copyright 2017 Tyler Suehr
 *
 * This schedules the execution of a {@link UseCase}.
 *
 * This is also what we will use to ensure that a UseCase is run on a worker-Thread and its
 * callbacks are triggered on the UI-Thread. This is also where we'll manage Thread creation.
 *
 * The same instance of this object should be reused by anything that will run UseCases because
 * it will be using the same Thread-pool and factory for Thread creation. Therefore, to acquire
 * an instance of this object you must call {@link #getInstance()}.
 *
 * @author Tyler Suehr
 * @version 1.0
 */
public final class UseCaseScheduler {
    /* Stores singleton instance of scheduler */
    private static volatile UseCaseScheduler instance;
    /* Stores reference to thread-pool executor that's lazy loaded */
    private static ThreadPoolExecutor executor;
    /* Stores handler for running on UI-Thread */
    private final Handler handler;


    private UseCaseScheduler() {
        this.handler = new Handler(Looper.getMainLooper());
    }

    public static UseCaseScheduler getInstance() {
        if (instance == null) {
            synchronized (UseCaseScheduler.class) {
                if (instance == null) {
                    instance = new UseCaseScheduler();
                }
            }
        }
        return instance;
    }

    /**
     * Prepares a given use case and then executes it on a new Thread.
     *
     * @param useCase {@link UseCase}
     * @param request Request data
     * @param callback {@link UseCaseCallback}
     * @param <T> Request data type
     * @param <V> Response data type
     */
    public <T, V> void execute(final UseCase<T, V> useCase, T request, UseCaseCallback<V> callback) {
        useCase.setCallback(new UICallback<>(handler, callback));
        useCase.setRequest(request);
        getExecutor().execute(new Runnable() {
            @Override
            public void run() {
                useCase.onExecute();
            }
        });
    }

    /**
     * Lazy-loads an instance of a thread-pool executor.
     * @return {@link ThreadPoolExecutor}
     */
    private static ThreadPoolExecutor getExecutor() {
        if (executor == null) {
            final int SIZE = 2;
            final int MAX = 4;
            final int TIMEOUT = 30;
            executor = new ThreadPoolExecutor(SIZE, MAX, TIMEOUT, TimeUnit.SECONDS,
                    new ArrayBlockingQueue<Runnable>(MAX));
        }
        return executor;
    }


    /**
     * Internal implementation of {@link UseCaseCallback} that ensures the provided
     * callback's methods are run on the UI-Thread.
     */
    private static final class UICallback<V> implements UseCaseCallback<V> {
        private final UseCaseCallback<V> callback;
        private final Handler handler;

        UICallback(final Handler handler, final UseCaseCallback<V> callback) {
            this.handler = handler;
            this.callback = callback;
        }

        @Override
        public void onSuccess(final V response) {
            this.handler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onSuccess(response);
                }
            });
        }

        @Override
        public void onFailure(final Exception ex) {
            this.handler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onFailure(ex);
                }
            });
        }
    }
}