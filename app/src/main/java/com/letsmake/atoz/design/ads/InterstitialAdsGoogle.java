package com.letsmake.atoz.design.ads;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class InterstitialAdsGoogle {

    public static InterstitialAd mInterstitialAd_admob;
    private static InterstitialAdsGoogle sharedInstance;
    private static boolean isReloaded = false;

    private static boolean canShowInterstitialAd() {
        return mInterstitialAd_admob != null;
    }

    public static void loadGoogleInterstitialAd(final Context context) {

        MyDataSaved preference = new MyDataSaved(context);

        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(context, preference.get_google_interstitial(), adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);

                InterstitialAdsGoogle.mInterstitialAd_admob = interstitialAd;
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);

                if (isReloaded == false) {
                    isReloaded = true;
                    loadGoogleInterstitialAd(context);
                }
            }
        });
    }

    public static void ShowAd_Full(Activity context, InterstitialAds.AdCloseListener adCloseListener) {

        MyDataSaved preference = new MyDataSaved(context);
        Constant.Front_Counter++;

        final Ad_Dialog Ad_Dialog = new Ad_Dialog(context, "Please Wait...");
        Ad_Dialog.setCancelable(false);
        Ad_Dialog.show();

        if (canShowInterstitialAd()) {
            isReloaded = false;

            InterstitialAdsGoogle.mInterstitialAd_admob.setFullScreenContentCallback(new FullScreenContentCallback() {

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                    super.onAdFailedToShowFullScreenContent(adError);

                    if (Ad_Dialog.isShowing()) {
                        Ad_Dialog.dismiss();
                    }
                    if (adCloseListener != null) {
                        adCloseListener.onAdClosed();
                    }
                    Constant.IS_TIME_INTERVAL = false;
                    new Handler().postDelayed(() -> Constant.IS_TIME_INTERVAL = true, Long.parseLong(String.valueOf(preference.get_ad_time())) * 1000);
                }

                @Override
                public void onAdDismissedFullScreenContent() {

                    if (Ad_Dialog.isShowing()) {
                        Ad_Dialog.dismiss();
                    }

                    if (adCloseListener != null) {
                        adCloseListener.onAdClosed();
                    }

                    Constant.IS_TIME_INTERVAL = false;
                    new Handler().postDelayed(() -> Constant.IS_TIME_INTERVAL = true, Long.parseLong(String.valueOf(preference.get_ad_time())) * 1000);

                    loadGoogleInterstitialAd(context);

                }
            });
            mInterstitialAd_admob.show(context);

        } else {

            if (Ad_Dialog.isShowing()) {
                Ad_Dialog.dismiss();
            }

            if (adCloseListener != null) {
                adCloseListener.onAdClosed();
            }

            Constant.IS_TIME_INTERVAL = false;
            new Handler().postDelayed(() -> Constant.IS_TIME_INTERVAL = true, Long.parseLong(String.valueOf(preference.get_ad_time())) * 1000);

            loadGoogleInterstitialAd(context);
        }
    }

    public static void ShowAd_Full_1(Activity source_class, InterstitialAds.AdCloseListener adCloseListener) {
        MyDataSaved preference = new MyDataSaved(source_class);
        if (preference.get_ad_status().equalsIgnoreCase("on")) {
            Constant.Front_Counter++;
            final Ad_Dialog Ad_Dialog = new Ad_Dialog(source_class, "Please Wait...");
            Ad_Dialog.setCancelable(false);
            Ad_Dialog.show();
            AdRequest adRequest = new AdRequest.Builder().build();
            InterstitialAd.load(source_class, preference.get_google_interstitial(), adRequest, new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    mInterstitialAd_admob = interstitialAd;
                    mInterstitialAd_admob.show(source_class);
                    mInterstitialAd_admob.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                            super.onAdFailedToShowFullScreenContent(adError);
                            mInterstitialAd_admob = null;
                            if (Ad_Dialog.isShowing()) {
                                Ad_Dialog.dismiss();
                            }
                            if (adCloseListener != null) {
                                adCloseListener.onAdClosed();
                            }
                            Constant.IS_TIME_INTERVAL = false;
                            new Handler().postDelayed(() -> Constant.IS_TIME_INTERVAL = true, Long.parseLong(String.valueOf(preference.get_ad_time())) * 1000);
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            super.onAdShowedFullScreenContent();
                            mInterstitialAd_admob = null;
                        }

                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            if (Ad_Dialog.isShowing()) {
                                Ad_Dialog.dismiss();
                            }
                            if (adCloseListener != null) {
                                adCloseListener.onAdClosed();
                            }
                            Constant.IS_TIME_INTERVAL = false;
                            new Handler().postDelayed(() -> Constant.IS_TIME_INTERVAL = true, Long.parseLong(String.valueOf(preference.get_ad_time())) * 1000);

                        }

                        @Override
                        public void onAdImpression() {
                            super.onAdImpression();
                        }
                    });
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    mInterstitialAd_admob = null;

                    if (Ad_Dialog.isShowing()) {
                        Ad_Dialog.dismiss();
                    }
                    if (adCloseListener != null) {
                        adCloseListener.onAdClosed();
                    }

                    Constant.IS_TIME_INTERVAL = false;
                    new Handler().postDelayed(() -> Constant.IS_TIME_INTERVAL = true, Long.parseLong(String.valueOf(preference.get_ad_time())) * 1000);
                }
            });
        } else {
            if (adCloseListener != null) {
                adCloseListener.onAdClosed();
            }
        }
    }
}
