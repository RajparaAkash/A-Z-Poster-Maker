package com.letsmake.atoz.design.custom_view;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;

import java.util.HashMap;

public class GetFontTypeFace {
    private static HashMap<String, Typeface> fontCache = new HashMap();

    public static Typeface getFontTypeface(String str, Context context) {
        Typeface typeface = fontCache.get(str);
        if (typeface == null) {
            try {
                AssetManager assets = context.getAssets();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("font/");
                stringBuilder.append(str);
                typeface = Typeface.createFromAsset(assets, stringBuilder.toString());
                fontCache.put(str, typeface);
            } catch (Exception unused) {
                return null;
            }
        }
        return typeface;
    }
}
