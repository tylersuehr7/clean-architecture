package com.tylersuehr.cleanarchitecture.ui.views;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.tylersuehr.cleanarchitecture.R;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 8/28/2016.
 */
public class CardSpacer extends RecyclerView.ItemDecoration {
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int small = view.getResources().getDimensionPixelSize(R.dimen.padding_small);
        outRect.left = small;
        outRect.right = small;
        outRect.bottom = small;
    }
}