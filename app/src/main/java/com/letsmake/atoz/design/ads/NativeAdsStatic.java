package com.letsmake.atoz.design.ads;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.letsmake.atoz.design.R;

public class NativeAdsStatic {
    Context context;

    public NativeAdsStatic(Context context) {
        this.context = context;
    }

    public void Native_Ads_Google(ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.ads_static_native_big, null);
        viewGroup.removeAllViews();
        ShimmerFrameLayout shimmerContainer = view.findViewById(R.id.shimmer_view_container);
        shimmerContainer.startShimmer();
        viewGroup.addView(view);
    }

    public void Native_Banner_Ads_Google(ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.ads_static_native_small, null);
        viewGroup.removeAllViews();
        ShimmerFrameLayout shimmerContainer = view.findViewById(R.id.shimmer_view_container);
        shimmerContainer.startShimmer();
        viewGroup.addView(view);
    }

    public void Adaptive_Banner_Ads_Google(ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.ads_static_adaptive_banner, null);
        viewGroup.removeAllViews();
        ShimmerFrameLayout shimmerContainer = view.findViewById(R.id.shimmer_view_container);
        shimmerContainer.startShimmer();
        viewGroup.addView(view);
    }

}
