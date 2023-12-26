package com.letsmake.atoz.design.ads;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.letsmake.atoz.design.R;

public class NativeAds {

    public Context context;
    MyDataSaved preference;

    public NativeAds(Context context) {
        this.context = context;
        preference = new MyDataSaved(this.context);
    }

    public void Native_Ad(final ViewGroup viewGroup) {
        new NativeAdsStatic(context).Native_Ads_Google(viewGroup);
        Admob_Native_Ads(viewGroup);
    }

    public void Native_Banner_Ads(final ViewGroup viewGroup) {
        new NativeAdsStatic(context).Native_Banner_Ads_Google(viewGroup);
        Admob_Native_Banner_Ads(viewGroup);
    }

    public void Adaptive_Banner(final ViewGroup viewGroup) {
        new NativeAdsStatic(context).Adaptive_Banner_Ads_Google(viewGroup);
        Admob_Adaptive_Banner(viewGroup);
    }

    private void Admob_Native_Ads(final ViewGroup viewGroup) {
        if (preference.get_ad_status().equalsIgnoreCase("on")) {
            try {
                View inflate = LayoutInflater.from(context).inflate(R.layout.ads_templateview_layout, viewGroup, false);
                final TemplateView templateView = inflate.findViewById(R.id.my_template_large);
                inflate.findViewById(R.id.my_template_small).setVisibility(View.GONE);
                templateView.setVisibility(View.GONE);
                AdLoader.Builder builder = new AdLoader.Builder(context, preference.get_google_native());
                builder.forNativeAd(nativeAd -> {
                    NativeTemplateStyle build = new NativeTemplateStyle.Builder().build();
                    templateView.setVisibility(View.VISIBLE);
                    templateView.setStyles(build);
                    templateView.setNativeAd(nativeAd);
                    TextView button = templateView.findViewById(R.id.cta);
                    viewGroup.removeAllViews();
                    viewGroup.addView(inflate);
                });

                builder.withAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        Log.e("TAG", "Admob Fail -> onAdFailedToLoad: Native" + loadAdError.getMessage());
                        super.onAdFailedToLoad(loadAdError);
                        templateView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAdOpened() {
                        super.onAdOpened();
                    }

                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();
                        Log.e("TAG", "Admob Load -> onNativeAdLoaded: Native");
                    }
                }).build().loadAd(new AdRequest.Builder().build());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void Admob_Native_Banner_Ads(final ViewGroup viewGroup) {
        if (preference.get_ad_status().equalsIgnoreCase("on")) {
            try {
                View inflate = LayoutInflater.from(context).inflate(R.layout.ads_templateview_layout, viewGroup, false);
                final TemplateView templateView = inflate.findViewById(R.id.my_template_small);
                inflate.findViewById(R.id.my_template_small).setVisibility(View.GONE);
                templateView.setVisibility(View.GONE);
                AdLoader.Builder builder = new AdLoader.Builder(context, preference.get_google_native());
                builder.forNativeAd(nativeAd -> {
                    NativeTemplateStyle build = new NativeTemplateStyle.Builder().build();
                    templateView.setVisibility(View.VISIBLE);
                    templateView.setStyles(build);
                    templateView.setNativeAd(nativeAd);
                    TextView button = templateView.findViewById(R.id.cta);
                    viewGroup.removeAllViews();
                    viewGroup.addView(inflate);
                });

                builder.withAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                        templateView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAdOpened() {
                        super.onAdOpened();
                    }

                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();
                    }
                }).build().loadAd(new AdRequest.Builder().build());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void Admob_Adaptive_Banner(final ViewGroup viewGroup) {
        if (preference.get_ad_status().equalsIgnoreCase("on")) {
            try {
                final AdView adView = new AdView(context);
                adView.setAdSize(getAdSize(context));
                adView.setAdUnitId(preference.get_google_banner());
                AdRequest adRequest = new AdRequest.Builder().build();
                adView.loadAd(adRequest);
                adView.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        Log.e("TAG", "Admob  Fail -> onAdFailedToLoad: Banner" + loadAdError.getMessage());
                        super.onAdFailedToLoad(loadAdError);
                    }

                    @Override
                    public void onAdOpened() {
                        super.onAdOpened();
                    }

                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();

                        try {
                            viewGroup.removeAllViews();
                            viewGroup.addView(adView);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onAdClicked() {
                        super.onAdClicked();
                    }

                    @Override
                    public void onAdImpression() {
                        super.onAdImpression();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static AdSize getAdSize(Context context) {
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;
        int adWidth = (int) (widthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth);
    }

}
