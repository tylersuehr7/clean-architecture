package com.tylersuehr.cleanarchitecture.ui.utils;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AlignmentSpan;
/**
 * Copyright 2016 Tyler Suehr
 * Created by tyler on 8/30/2016.
 */
public final class SpanUtils {
    public static SpannableString createCenterSpan(CharSequence text) {
        SpannableString sb = new SpannableString(text);
        sb.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }
}