package com.tylersuehr.cleanarchitecture.domain;
import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 7/3/2017.
 *
 * This is used to schedule execution for each use case, to ensure that it runs on a valid
 * worker thread, and to ensure that the use case calls back on the UI thread.
 */
public final class UseCaseScheduler {
    private static UseCaseScheduler instance;
    private static ThreadPoolExecutor executor;
    private final Handler handler;


    private UseCaseScheduler() {
        this.handler = new Handler(Looper.getMainLooper());
    }

    public static synchronized UseCaseScheduler getInstance() {
        if (instance == null) {
            UseCaseScheduler.instance = new UseCaseScheduler();
            UseCaseScheduler.executor = getExecutor();
        }
        return instance;
    }

    /**
     * Executes a use case on a worker thread.
     * @param useCase {@link UseCase}
     * @param request Use case request
     * @param callback {@link UseCaseCallback}
     */
    public <T, V> void execute(final UseCase<T, V> useCase, T request, UseCaseCallback<V> callback) {
        useCase.setCallback(new UICallback<>(handler, callback));
        useCase.setRequest(request);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                useCase.onExecute();
            }
        });
    }

    /**
     * Lazy-loads a valid instance of {@link ThreadPoolExecutor}.
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
     * Ensures that our {@link UseCaseCallback} methods are run on the UI thread.
     */
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
        public void onFailure(final Exception ex) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onFailure(ex);
                }
            });
        }
    }
}