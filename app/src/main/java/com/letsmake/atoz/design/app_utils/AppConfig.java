package com.letsmake.atoz.design.app_utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class AppConfig {
    public static SharedPreferences sharedPreferences;

    public static void storeInt(String str, int i, Activity activity) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        Editor edit = sharedPreferences.edit();
        edit.putInt(str, i);
        edit.apply();
    }
}
