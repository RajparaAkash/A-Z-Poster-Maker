package com.letsmake.atoz.design.ads;

import com.google.android.gms.ads.MobileAds;
import com.letsmake.atoz.design.Application;
import com.onesignal.OneSignal;

public class MyApplication extends Application {

    private static final String ONESIGNAL_APP_ID = "248d0fa0-a0d7-4dc5-b68f-157df0d43097";

    @Override
    public void onCreate() {
        super.onCreate();

        MobileAds.initialize(
                this,
                initializationStatus -> {
                });
        new AppOpenManager(this);

        /*OneSignal*/
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
        OneSignal.promptForPushNotifications();

    }
}
