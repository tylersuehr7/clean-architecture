package com.tylersuehr.cleanarchitecture.ui.shared;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AlignmentSpan;
/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 7/4/2017.
 *
 * Utility to help manipulate spans.
 */
public final class SpanUtils {
    public static Spanned center(CharSequence text) {
        SpannableString span = new SpannableString(text);
        span.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return span;
    }
}