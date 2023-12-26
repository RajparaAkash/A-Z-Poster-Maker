package com.letsmake.atoz.design.app_utils;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

import androidx.annotation.NonNull;

public class OwnTypefaceSpan extends MetricAffectingSpan {
    private final Typeface typeface;

    public OwnTypefaceSpan(Typeface typeface) {
        this.typeface = typeface;
    }

    public void updateDrawState(TextPaint textPaint) {
        apply(textPaint);
    }

    public void updateMeasureState(@NonNull TextPaint textPaint) {
        apply(textPaint);
    }

    private void apply(Paint paint) {
        paint.setFakeBoldText(true);
        paint.setTypeface(this.typeface);
    }
}
