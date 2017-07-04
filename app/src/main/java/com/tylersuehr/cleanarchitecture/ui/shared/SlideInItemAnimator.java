package com.tylersuehr.cleanarchitecture.ui.shared;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

import java.util.ArrayList;
import java.util.List;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 8/15/2016.
 *
 * An animator for RecyclerView that fades & slides newly added items
 * in from a given direction.
 */
public class SlideInItemAnimator extends DefaultItemAnimator {
    private final List<RecyclerView.ViewHolder> pendingAdds = new ArrayList<>();
    private final int slideFromEdge;


    public SlideInItemAnimator() {
        this(Gravity.BOTTOM, -1);
    }

    public SlideInItemAnimator(int slideFromEdge, int layoutDirection) {
        this.slideFromEdge = Gravity.getAbsoluteGravity(slideFromEdge, layoutDirection);
        setAddDuration(160L);
    }

    @Override
    public boolean animateAdd(RecyclerView.ViewHolder holder) {
        holder.itemView.setAlpha(0f);
        switch (slideFromEdge) {
            case Gravity.LEFT:
                holder.itemView.setTranslationX(-holder.itemView.getWidth() / 3);
                break;
            case Gravity.TOP:
                holder.itemView.setTranslationY(-holder.itemView.getHeight() / 3);
                break;
            case Gravity.RIGHT:
                holder.itemView.setTranslationX(holder.itemView.getWidth() / 3);
                break;
            default: // Gravity.BOTTOM
                holder.itemView.setTranslationY(holder.itemView.getHeight() / 3);
                break;
        }
        pendingAdds.add(holder);
        return true;
    }

    @Override
    public void runPendingAnimations() {
        super.runPendingAnimations();
        if (!pendingAdds.isEmpty()) {
            for (int i = pendingAdds.size() - 1; i >= 0; i--) {
                final RecyclerView.ViewHolder holder = pendingAdds.get(i);
                holder.itemView.animate()
                        .alpha(1f)
                        .translationX(0f)
                        .translationY(0f)
                        .setDuration(getAddDuration())
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                                dispatchAddStarting(holder);
                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                animation.getListeners().remove(this);
                                dispatchAddFinished(holder);
                                dispatchFinishedWhenDone();
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {
                                clearAnimatedValues(holder.itemView);
                            }
                        }).setInterpolator(getInterpolatorCompat(holder));
                pendingAdds.remove(i);
            }
        }
    }

    @Override
    public void endAnimation(RecyclerView.ViewHolder holder) {
        holder.itemView.animate().cancel();
        if (pendingAdds.remove(holder)) {
            dispatchAddFinished(holder);
            clearAnimatedValues(holder.itemView);
        }
        super.endAnimation(holder);
    }

    @Override
    public void endAnimations() {
        RecyclerView.ViewHolder holder;
        for (int i = pendingAdds.size() - 1; i >= 0; i--) {
            holder = pendingAdds.get(i);
            clearAnimatedValues(holder.itemView);
            dispatchAddFinished(holder);
            pendingAdds.remove(i);
        }
        super.endAnimations();
    }

    @Override
    public boolean isRunning() {
        return !pendingAdds.isEmpty() || super.isRunning();
    }

    private void dispatchFinishedWhenDone() {
        if (!isRunning()) {
            dispatchAnimationsFinished();
        }
    }

    private void clearAnimatedValues(View view) {
        view.setAlpha(1f);
        view.setTranslationX(0f);
        view.setTranslationY(0f);
    }

    private Interpolator getInterpolatorCompat(RecyclerView.ViewHolder holder) {
        Context c = holder.itemView.getContext();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return AnimationUtils.loadInterpolator(c, android.R.interpolator.linear_out_slow_in);
        }
        return AnimationUtils.loadInterpolator(c, android.R.interpolator.accelerate_decelerate);
    }
}