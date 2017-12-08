package com.tylersuehr.cleanarchitecture.domain;
import android.support.annotation.WorkerThread;
import android.util.Log;

/**
 * Copyright 2017 Tyler Suehr
 *
 * A UseCase is a single task that takes a request and gives a response. It is effectively only
 * Business logic and should not contain any Application logic whatsoever.
 *
 * UseCases are executed on a worker-Thread and then called back onto the UI-Thread once they're
 * completed, and with a response.
 *
 * Although UseCases are meant for request-response actions, requests and responses are not
 * mandatory and can be ignored simply by declaring either or's generic type as Object and passing
 * a null value for it.
 *
 * Since UseCases are Business logic, they will definitely be using data sources of some kind.
 * In FirstPick, they will be using mostly, if not always, repositories. It's important to note
 * that these repositories are NEVER compositions; they are ONLY aggregations and should be
 * supplied to each UseCase using dependency injection techniques.
 *
 *
 * To use a UseCase properly we must do the following:
 * (1) Create a sub-class of {@link UseCase}.
 * (2) Establish our generics for the request and response.
 * (3) Implement the {@link #onExecute()} method.
 * (4) Ensure that the UseCase passes, {@link #pass(Object)}, or fails, {@link #fail(Exception)}.
 *
 * {@link Request}: Represents the request for a UseCase.
 * {@link Response}: Represents the response for a UseCase.
 *
 *
 * Example of proper UseCase usage:
 * {@code
 *     public class FindUserTask extends UseCase<String, User> {
 *         private final IUserRepository userRepo;
 *
 *         public FindUserTask(IUserRepository userRepo) {
 *              this.userRepo = userRepo;
 *         }
 *
 *         @Override
 *         protected void onExecute() {
 *              final String userProfileId = getRequest();
 *              this.userRepo.findUserById(userProfileId, new SingleCallback<User>() {
 *                  @Override
 *                  public void onAvailable(User foundUser) {
 *                      pass(foundUser);
 *                  }
 *
 *                  @Override
 *                  public void onNotAvailable(Exception ex) {
 *                      fail(ex);
 *                  }
 *              });
 *         }
 *     }
 * }
 *
 * @author Tyler Suehr
 * @version 1.0
 */
public abstract class UseCase<Request, Response> {
    private UseCaseCallback<Response> callback;
    private Request request;


    @WorkerThread
    protected abstract void onExecute();

    /**
     * Passes the use case by logging it and invoking the appropriate callback
     * with the response data from the successful execution.
     * @param response Response data
     */
    protected final void pass(final Response response) {
        Log.i(getClass().getSimpleName().toUpperCase(), "Success!");
        this.callback.onSuccess(response);
    }

    /**
     * Fails the use case by logging it and invoking the appropriate callback
     * containing information about by it fail execution.
     * @param ex {@link Exception}
     */
    protected final void fail(final Exception ex) {
        Log.wtf(getClass().getSimpleName().toUpperCase(), "Failed!", ex);
        this.callback.onFailure(ex);
    }

    public Request getRequest() {
        return request;
    }

    void setRequest(final Request request) {
        this.request = request;
    }

    void setCallback(final UseCaseCallback<Response> callback) {
        this.callback = callback;
    }
}