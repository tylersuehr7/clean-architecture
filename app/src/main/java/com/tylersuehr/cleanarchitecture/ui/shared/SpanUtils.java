package com.tylersuehr.cleanarchitecture.ui.shared;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AlignmentSpan;

/**
 * Copyright 2017 Tyler Suehr
 *
 * Utility to help manipulate spans.
 *
 * @author Tyler Suehr
 * @version 1.0
 */
public final class SpanUtils {
    public static Spanned center(CharSequence text) {
        SpannableString span = new SpannableString(text);
        span.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return span;
    }
}