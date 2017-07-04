package com.tylersuehr.cleanarchitecture.ui.views;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import com.tylersuehr.cleanarchitecture.R;
import java.util.ArrayList;
import java.util.List;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 8/11/2016.
 *
 * A view group that responds to nested scrolls to creeate drag-dismissible layouts.
 * NOTE: ONLY COMPATIBLE WITH API 21+
 */
@TargetApi(21)
public class ElasticFrameLayout extends FrameLayout {
    // State
    private boolean draggingDown = false;
    private boolean draggingUp = false;
    private float totalDrag;

    // Properties
    private List<IDragDismiss> callbacks;
    private float dragDismissDistance = Float.MAX_VALUE;
    private float dragDismissFraction = -1f;
    private float dragDismissScale = 1;
    private float dragElasticity = 0.8f;
    private boolean shouldScale = false;


    public ElasticFrameLayout(Context context) {
        this(context, null, 0);
    }

    public ElasticFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ElasticFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ElasticFrameLayout);
            dragDismissDistance = a.getDimensionPixelSize(R.styleable.ElasticFrameLayout_dragDismissDistance, 0);
            dragDismissFraction = a.getFloat(R.styleable.ElasticFrameLayout_dragDismissFraction, dragDismissFraction);
            dragDismissScale = a.getFloat(R.styleable.ElasticFrameLayout_dragDismissScale, dragDismissScale);
            shouldScale = dragDismissScale != 1f;
            dragElasticity = a.getFloat(R.styleable.ElasticFrameLayout_dragElasticity, dragElasticity);
            a.recycle();
        }
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & View.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        // If we're in a drag gesture and the user reverses up, we should take those events
        if (draggingDown && dy > 0 || draggingUp && dy < 0) {
            dragScale(dy);
            consumed[1] = dy;
        }
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        dragScale(dyUnconsumed);
    }

    @Override
    public void onStopNestedScroll(View child) {
        if (Math.abs(totalDrag) >= dragDismissDistance) {
            dispatchDismissCallback();
        } else {
            // Go back to natural position
            animate().translationY(0f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(200L)
                    .setInterpolator(AnimationUtils.loadInterpolator(getContext(), android.R.interpolator.fast_out_slow_in))
                    .setListener(null)
                    .start();
            totalDrag = 0;
            draggingDown = draggingUp = false;
            dispatchDragCallback(0f, 0f, 0f, 0f);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        if (dragDismissFraction > 0f) {
            dragDismissDistance = h * dragDismissFraction;
        }
    }

    public void addListener(IDragDismiss listener) {
        if (callbacks == null) {
            this.callbacks = new ArrayList<>();
        }
        this.callbacks.add(listener);
    }

    public void removeListener(IDragDismiss listener) {
        if (callbacks != null) {
            callbacks.remove(listener);
        }
    }

    private void dragScale(int scroll) {
        if (scroll == 0) { return; }
        totalDrag += scroll;

        // Track the direction & set the pivot point for scaling
        // don't double track i.e. if start dragging down and then reverse, keep tracking as
        // dragging down until they reach the 'natural' position
        if (scroll < 0 && !draggingUp && !draggingDown) {
            draggingDown = true;
            if (shouldScale) setPivotY(getHeight());
        } else if (scroll > 0 && !draggingDown && !draggingUp) {
            draggingUp = true;
            if (shouldScale) setPivotY(0f);
        }
        // How far have we dragged relative to the distance to perform a dismiss
        // (0–1 where 1 = dismiss distance). Decreasing logarithmically as we approach the limit
        float dragFraction = (float) Math.log10(1 + (Math.abs(totalDrag) / dragDismissDistance));

        // Calculate the desired translation given the drag fraction
        float dragTo = dragFraction * dragDismissDistance * dragElasticity;

        if (draggingUp) {
            // As we use the absolute magnitude when calculating the drag fraction, need to
            // re-apply the drag direction
            dragTo *= -1;
        }
        setTranslationY(dragTo);

        if (shouldScale) {
            final float scale = 1 - ((1 - dragDismissScale) * dragFraction);
            setScaleX(scale);
            setScaleY(scale);
        }

        // If we've reversed direction and gone past the settle point then clear the flags to
        // allow the list to get the scroll events & reset any transforms
        if ((draggingDown && totalDrag >= 0)
                || (draggingUp && totalDrag <= 0)) {
            totalDrag = dragTo = dragFraction = 0;
            draggingDown = draggingUp = false;
            setTranslationY(0f);
            setScaleX(1f);
            setScaleY(1f);
        }
        dispatchDragCallback(dragFraction, dragTo,
                Math.min(1f, Math.abs(totalDrag) / dragDismissDistance), totalDrag);
    }

    private void dispatchDragCallback(float elasticOffset, float elasticOffsetPixels, float rawOffset, float rawOffsetPixels) {
        if (callbacks != null && !callbacks.isEmpty()) {
            for (IDragDismiss callback : callbacks) {
                callback.onDrag(elasticOffset, elasticOffsetPixels, rawOffset, rawOffsetPixels);
            }
        }
    }

    private void dispatchDismissCallback() {
        if (callbacks != null && !callbacks.isEmpty()) {
            for (IDragDismiss callback : callbacks) {
                callback.onDragDismissed();
            }
        }
    }


    public interface IDragDismiss {
        void onDrag(float elasticOffset, float elasticOffsetPixels, float rawOffset, float rawOffsetPixels);
        void onDragDismissed();
    }

    /**
     * An {@link IDragDismiss} which fades system chrome (status bar and navigation bar)
     * whilst elastic drags are performed and finishAfterTransition() the activity when
     * drag dismissed.
     */
    public static class SystemChromeFader implements IDragDismiss {
        private final Activity activity;
        private final boolean fadeNavBar;
        private final int statusBarAlpha;
        private final int navBarAlpha;


        public SystemChromeFader(Activity activity) {
            this.activity = activity;
            this.statusBarAlpha = Color.alpha(activity.getWindow().getStatusBarColor());
            this.navBarAlpha = Color.alpha(activity.getWindow().getNavigationBarColor());
            this.fadeNavBar = isNavBarOnBottom(activity.getResources());
        }

        @Override
        public void onDrag(float elasticOffset, float elasticOffsetPixels, float rawOffset, float rawOffsetPixels) {
            if (elasticOffsetPixels > 0) {
                // Dragging downward, fade the status bar in proportion
                activity.getWindow().setStatusBarColor(modifyAlpha(activity.getWindow()
                        .getStatusBarColor(), (int) ((1f - rawOffset) * statusBarAlpha)));
            } else if (elasticOffsetPixels == 0) {
                // Reset
                activity.getWindow().setStatusBarColor(modifyAlpha(activity.getWindow()
                        .getStatusBarColor(), statusBarAlpha));
                activity.getWindow().setNavigationBarColor(modifyAlpha(activity.getWindow()
                        .getNavigationBarColor(), navBarAlpha));
            } else if (fadeNavBar) {
                // Dragging upward, fade the navigation bar in proportion
                activity.getWindow().setNavigationBarColor(modifyAlpha(activity.getWindow()
                        .getNavigationBarColor(), (int)((1f - rawOffset) * navBarAlpha)));
            }
        }

        @Override
        public void onDragDismissed() {
            activity.finishAfterTransition();
        }

        private int modifyAlpha(@ColorInt int color, @IntRange(from=0,to=255) int alpha) {
            return (color & 0x00ffffff) | (alpha << 24);
        }
    }

    /**
     * Determines if the navigation bar is on the bottom of the screen.
     * @param res Application resources
     * @return True if on bottom
     */
    private static boolean isNavBarOnBottom(@NonNull Resources res) {
        final Configuration cfg = res.getConfiguration();
        final DisplayMetrics dm = res.getDisplayMetrics();
        boolean canMove = (dm.widthPixels != dm.heightPixels
                && cfg.smallestScreenWidthDp < 600);
        return(!canMove || dm.widthPixels < dm.heightPixels);
    }
}