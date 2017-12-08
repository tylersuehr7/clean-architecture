package com.tylersuehr.cleanarchitecture.ui;

import com.tylersuehr.cleanarchitecture.domain.UseCase;
import com.tylersuehr.cleanarchitecture.domain.UseCaseCallback;
import com.tylersuehr.cleanarchitecture.domain.UseCaseScheduler;

/**
 * Copyright © 2017 Tyler Suehr
 *
 * The parent class for any presenter in this project.
 *
 * This contains the core implementation of {@link IBasePresenter} to manage the attached
 * view and schedule use case executions.
 *
 * Important note: to prevent memory leakage, it's extremely important to recognize that
 * you must explicitly attach and detach the presenter's view in the appropriate Android
 * framework lifecycle events depending on how you're using the presenter.
 *
 * A memory leak will occur when you schedule the execution of a long-running use case that
 * fails to complete before the Android framework component's lifecycle ends without detach-
 * ing the presenter's view. This is a memory leak because the Android framework ends the
 * life of a component, but it is still referenced in the presenter; thus, not allowing the
 * GC to deallocate it in-memory. Basically does the same thing if you store an Activity
 * Context in a singleton.
 *
 * Here's an example of the above memory leak:
 * <code>
 *     public class ExampleFragment extends Fragment implements IExampleView {
 *         private ExamplePresenter presenter;
 *
 *         public static ExampleFragment create(ExamplePresenter p) {
 *             ExampleFragment frag = new ExampleFragment();
 *             frag.presenter = p;
 *             frag.presenter.attach(frag);
 *             return frag;
 *         }
 *
 *         // ...onCreateView(...) override
 *
 *         @Override
 *         public void onActivityCreated(Bundle savedInstanceState) {
 *             super.onActivityCreated(savedInstanceState);
 *             this.presenter.loadLongExampleTask();
 *             getActivity().onBackPressed();
 *         }
 *
 *         @Override
 *         public void onExampleTaskDone() {
 *             // ...do something
 *         }
 *     }
 * </code>
 *
 * Here's how to prevent the above memory leak, just detach the presenter's view
 * when you no longer need it (in the above example, you'd just add this):
 * <code>
 *     @Override
 *     public void onDestroyView() {
 *         super.onDestroyView();
 *         this.presenter.detach();
 *     }
 * </code>
 *
 * You could also stop any scheduled use cases from running as well (not required):
 * <code>
 *     @Override
 *     public void onStop() {
 *         super.onStop();
 *         this.presenter.stopTasks();
 *     }
 * </code>
 *
 * @author Tyler Suehr
 * @version 1.0
 */
public abstract class AbstractBasePresenter<IView extends IBaseView> implements IBasePresenter<IView> {
    /* Stores reference to the scheduler for our use cases */
    private final UseCaseScheduler scheduler = UseCaseScheduler.getInstance();
    /* Stores reference to the presenter's view */
    private IView view;


    @Override
    public final IView getView() {
        return view;
    }

    @Override
    public void attach(IView view) {
        this.view  = view;
    }

    @Override
    public void detach() {
        this.view = null;
    }

    @Override
    public void stopTasks() {
        this.scheduler.shutdownExecution();
    }

    /**
     * Schedules the execution of a use case using the presenter's scheduler.
     *
     * @param useCase {@link UseCase}
     * @param request Request data
     * @param callback {@link UseCaseCallback}
     * @param <T> Request data type
     * @param <V> Response data type
     */
    protected final <T,V> void schedule(UseCase<T,V> useCase, T request, UseCaseCallback<V> callback) {
        this.scheduler.execute(useCase, request, new NullCheckCallback<>(callback));
    }


    /**
     * Nested inner-class implementation of {@link UseCaseCallback} to handle
     * preventing a {@link NullPointerException} when invoking wrapped callback
     * methods if the view is no longer attached to the presenter.
     *
     * Note: this cannot leak memory because when the view is detached from the
     * presenter, its reference is lost and it will be collected by the GC.
     */
    private final class NullCheckCallback<V> implements UseCaseCallback<V> {
        private final UseCaseCallback<V> wrap;

        NullCheckCallback(final UseCaseCallback<V> wrap) {
            this.wrap = wrap;
        }

        @Override
        public void onSuccess(V v) {
            if (view != null) { wrap.onSuccess(v); }
        }

        @Override
        public void onFailure(Exception ex) {
            if (view != null) { wrap.onFailure(ex); }
        }
    }
}