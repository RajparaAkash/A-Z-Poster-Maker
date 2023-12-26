package com.letsmake.atoz.design.imageloader;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import com.letsmake.atoz.design.imageloader.Progress_Module.UIonProgressModuleListener;

public class Custom_Glide_IMG_Loader {

    private ImageView mImageView;
    private ProgressBar mProgressBar;

    public Custom_Glide_IMG_Loader(ImageView imageView, ProgressBar progressBar) {
        this.mImageView = imageView;
        this.mProgressBar = progressBar;
    }

    public void loadImgFromUrl(final String str, RequestOptions requestOptions) {
        if (str != null && requestOptions != null) {
            onProgressConnecting();
            Progress_Module.expect(str, new UIonProgressModuleListener() {
                public float getGranualityPercentage() {
                    return 1.0f;
                }

                public void onProgress(long j, long j2) {
                    if (Custom_Glide_IMG_Loader.this.mProgressBar != null) {
                        Custom_Glide_IMG_Loader.this.mProgressBar.setProgress((int) ((j * 100) / j2));
                    }
                }
            });
            Glide.with(this.mImageView.getContext()).load(str).transition(DrawableTransitionOptions.withCrossFade()).thumbnail(0.1f).listener(new RequestListener<Drawable>() {
                public boolean onLoadFailed(@Nullable GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                    Progress_Module.forget(str);
                    Custom_Glide_IMG_Loader.this.onProgressFinished();
                    return false;
                }

                public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                    Progress_Module.forget(str);
                    Custom_Glide_IMG_Loader.this.onProgressFinished();
                    return false;
                }
            }).into(this.mImageView);
        }
    }

    private void onProgressConnecting() {
        ProgressBar progressBar = this.mProgressBar;
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    public void onProgressFinished() {
        ProgressBar progressBar = this.mProgressBar;
        if (progressBar != null && this.mImageView != null) {
            progressBar.setVisibility(View.GONE);
            this.mImageView.setVisibility(View.VISIBLE);
        }
    }
}
