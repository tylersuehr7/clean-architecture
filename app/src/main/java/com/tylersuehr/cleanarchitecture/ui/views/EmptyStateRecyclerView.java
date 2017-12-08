package com.tylersuehr.cleanarchitecture.ui.views;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import com.tylersuehr.cleanarchitecture.R;

/**
 * Copyright 2017 Tyler Suehr
 *
 * A normal RecyclerView that has the ability to display a placeholder for an 'empty state'.
 *
 * @author Tyler Suehr
 * @version 1.0
 */
public class EmptyStateRecyclerView extends RecyclerView {
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Drawable emptyStateIcon;
    private String emptyStateText;

    private final int emptyStateIconSize;
    private boolean showEmptyState = false;
    private boolean autoEmptyState = false;


    public EmptyStateRecyclerView(Context context) {
        this(context, null);
    }

    public EmptyStateRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyStateRecyclerView(Context c, @Nullable AttributeSet attrs, int defStyle) {
        super(c, attrs, defStyle);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        this.emptyStateIconSize = (int)(56f * dm.density);
        float emptyStateTextSize = (14f * dm.scaledDensity);

        // Set XML attributes
        TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.EmptyStateRecyclerView);
        this.emptyStateIcon = a.hasValue(R.styleable.EmptyStateRecyclerView_emptyStateIcon)
                ? a.getDrawable(R.styleable.EmptyStateRecyclerView_emptyStateIcon)
                : ContextCompat.getDrawable(c, R.drawable.ic_comment_24dp);
        this.emptyStateText = a.hasValue(R.styleable.EmptyStateRecyclerView_emptyStateText)
                ? a.getString(R.styleable.EmptyStateRecyclerView_emptyStateText) : "No Content";
        this.autoEmptyState = a.getBoolean(R.styleable.EmptyStateRecyclerView_emptyStateAuto, true);
        a.recycle();

        // Adjust the drawable's colors and bounds
        this.emptyStateIcon.setColorFilter(ContextCompat.getColor(c, R.color.grey_600), PorterDuff.Mode.SRC_ATOP);
        this.emptyStateIcon.setBounds(0, 0, emptyStateIconSize, emptyStateIconSize);

        // Setup paint
        this.paint.setColor(ContextCompat.getColor(c, R.color.grey_600));
        this.paint.setTextSize(emptyStateTextSize);
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);

        // The empty state can be drawn automatically or manually
        if ((autoEmptyState && getAdapter() != null && getAdapter().getItemCount() <= 0) || showEmptyState) {
            int width = getMeasuredWidth();
            int height = getMeasuredHeight();
            float dy, dx;

            // Align the drawable to the exact center
            dy = (height >> 1) - (emptyStateIconSize >> 1);
            dx = (width >> 1) - (emptyStateIconSize >> 1);

            // Draw the icon
            c.save();
            c.translate(dx, dy);
            emptyStateIcon.draw(c);
            c.restore();

            // Align the text below the icon in the horizontal center
            float fontHeight = (paint.descent() + paint.ascent());
            float textWidth = paint.measureText(emptyStateText, 0, emptyStateText.length());
            dy += -fontHeight * 2 + emptyStateIconSize;
            dx = (width >> 1) - ((int)textWidth >> 1);

            // Draw the text
            c.drawText(emptyStateText, dx, dy, paint);
        }
    }

    public void setAutoEmptyState(boolean value) {
        this.autoEmptyState = value;
        if (value) {
            this.showEmptyState = false;
        }
    }

    public void setShowEmptyState(boolean value) {
        this.showEmptyState = value;
        if (value) {
            this.autoEmptyState = false;
        }
    }

    public Drawable getEmptyStateIcon() {
        return emptyStateIcon;
    }

    public void setEmptyStateIcon(Drawable emptyStateIcon) {
        this.emptyStateIcon = emptyStateIcon;
        invalidate();
    }

    public String getEmptyStateText() {
        return emptyStateText;
    }

    public void setEmptyStateText(String emptyStateText) {
        this.emptyStateText = emptyStateText;
        invalidate();
    }
}