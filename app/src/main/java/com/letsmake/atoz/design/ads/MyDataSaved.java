package com.letsmake.atoz.design.ads;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class MyDataSaved {

    public Editor mySaveEditor;
    public SharedPreferences mysavePref;

    String value_ad_status = "value_ad_status";
    String value_click_flag = "value_click_flag";
    String value_ad_style = "value_ad_style";
    String value_ad_time = "value_ad_time";
    String value_splash_ad_style = "value_splash_ad_style";
    String value_ad_click = "value_ad_click";

    String value_google_appopen = "value_google_appopen";
    String value_google_interstitial = "value_google_interstitial";
    String value_google_native = "value_google_native";
    String value_google_banner = "value_google_banner";
    String value_google_reward = "value_google_reward";

    Context contexts;

    public MyDataSaved(Context context) {
        this.mysavePref = context.getSharedPreferences("USER PREFS", 0);
        this.mySaveEditor = this.mysavePref.edit();
        contexts = context;
    }

    public String get_ad_status() {
        return this.mysavePref.getString(this.value_ad_status, "");
    }

    public void set_ad_status(String str) {
        this.mySaveEditor.putString(this.value_ad_status, str).commit();
    }

    public String get_click_flag() {
        return this.mysavePref.getString(this.value_click_flag, "");
    }

    public void set_click_flag(String str) {
        this.mySaveEditor.putString(this.value_click_flag, str).commit();
    }

    public String get_ad_style() {
        return this.mysavePref.getString(this.value_ad_style, "");
    }

    public void set_ad_style(String str) {
        this.mySaveEditor.putString(this.value_ad_style, str).commit();
    }

    public String get_ad_time() {
        return this.mysavePref.getString(this.value_ad_time, "");
    }

    public void set_ad_time(String str) {
        this.mySaveEditor.putString(this.value_ad_time, str).commit();
    }

    public String get_splash_ad_style() {
        return this.mysavePref.getString(this.value_splash_ad_style, "");
    }

    public void set_splash_ad_style(String str) {
        this.mySaveEditor.putString(this.value_splash_ad_style, str).commit();
    }

    public String get_ad_click() {
        return this.mysavePref.getString(this.value_ad_click, "");
    }

    public void set_ad_click(String str) {
        this.mySaveEditor.putString(this.value_ad_click, str).commit();
    }

    public String get_google_appopen() {
        return this.mysavePref.getString(this.value_google_appopen, "");
    }

    public void set_google_appopen(String str) {
        this.mySaveEditor.putString(this.value_google_appopen, str).commit();
    }

    public String get_google_interstitial() {
        return this.mysavePref.getString(this.value_google_interstitial, "");
    }

    public void set_google_interstitial(String str) {
        this.mySaveEditor.putString(this.value_google_interstitial, str).commit();
    }

    public String get_google_native() {
        return this.mysavePref.getString(this.value_google_native, "");
    }

    public void set_google_native(String str) {
        this.mySaveEditor.putString(this.value_google_native, str).commit();
    }

    public String get_google_banner() {
        return this.mysavePref.getString(this.value_google_banner, "");
    }

    public void set_google_banner(String str) {
        this.mySaveEditor.putString(this.value_google_banner, str).commit();
    }

    public String get_google_reward() {
        return this.mysavePref.getString(this.value_google_reward, "");
    }

    public void set_google_reward(String str) {
        this.mySaveEditor.putString(this.value_google_reward, str).commit();
    }
}