package com.letsmake.atoz.design;

import android.content.Context;
import android.os.StrictMode;
import android.os.StrictMode.VmPolicy.Builder;
import android.text.TextUtils;
import androidx.multidex.BuildConfig;
import androidx.multidex.MultiDexApplication;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.Crashlytics;
import com.letsmake.atoz.design.editor_intelligence.AppConstants;
import com.onesignal.OneSignal;

import io.fabric.sdk.android.Fabric;


public class Application extends MultiDexApplication {

    private static final String TAG = "Application";
    private static Application mInstance;
    private RequestQueue mRequestQueue;

    public static synchronized Application getInstance() {
        Application application;
        synchronized (Application.class) {
            synchronized (Application.class) {
                application = mInstance;
            }
        }
        return application;
    }

    public RequestQueue getRequestQueue() {
        if (this.mRequestQueue == null) {
            this.mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return this.mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request, String str) {
        Object str2 = null;
        if (TextUtils.isEmpty((CharSequence) str2)) {
            str2 = TAG;
        }
        request.setTag(str2);
        request.setShouldCache(false);
        request.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(request);
    }

    public <T> void addToRequestQueue(Request<T> request) {
        request.setTag(TAG);
        getRequestQueue().add(request);
    }

    public void cancelPendingRequests(Object obj) {
        RequestQueue requestQueue = this.mRequestQueue;
        if (requestQueue != null) {
            requestQueue.cancelAll(obj);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        if (!BuildConfig.DEBUG) {
            final Fabric fabric = new Fabric.Builder(this)
                    .kits(new Crashlytics())
                    .build();
            Fabric.with(fabric);
        }

        AppConstants.BASE_URL_POSTER = "http://bhargav.fadootutorial.com/api/";
        AppConstants.BASE_URL_STICKER = "http://bhargav.fadootutorial.com/";
        AppConstants.BASE_URL_BG = "http://bhargav.fadootutorial.com/uploads/";
        AppConstants.BASE_URL = "http://threemartians.com/poster1/Resources/Poster.php";
        AppConstants.stickerURL = "";
        AppConstants.fURL = "";
        AppConstants.bgURL = "";

        StrictMode.setVmPolicy(new Builder().build());
    }

    public static Context getContext() {
        return getInstance().getApplicationContext();
    }
}
