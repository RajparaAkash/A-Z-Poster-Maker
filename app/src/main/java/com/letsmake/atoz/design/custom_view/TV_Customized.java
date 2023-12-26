package com.letsmake.atoz.design.custom_view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class TV_Customized extends TextView {
    public TV_Customized(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public TV_Customized(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        applyCustomFont(context);
    }

    public TV_Customized(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        setTypeface(GetFontTypeFace.getFontTypeface("font/Montserrat-Medium.ttf", context));
    }
}
