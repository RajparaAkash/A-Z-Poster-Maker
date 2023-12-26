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

public class Poster_Glide_IMG_Loader {

    private ProgressBar mProgressBar;
    private ImageView mImageView;

    public Poster_Glide_IMG_Loader(ImageView imageView, ProgressBar progressBar) {
        mImageView = imageView;
        mProgressBar = progressBar;
    }

    private void onConnecting() {
        ProgressBar progressBar = this.mProgressBar;
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }


    private void onFinished() {
        ProgressBar progressBar = this.mProgressBar;
        if (progressBar != null && this.mImageView != null) {
            progressBar.setVisibility(8);
            this.mImageView.setVisibility(0);
        }
    }

    public void loadPosterImgFromStr(final String str, RequestOptions requestOptions) {
        if (str != null && requestOptions != null) {
            onConnecting();
            Progress_Module.expect(str, new Progress_Module.UIonProgressModuleListener() {
                public float getGranualityPercentage() {
                    return 1.0f;
                }

                public void onProgress(long j, long j2) {
                    if (mProgressBar != null) {
                        mProgressBar.setProgress((int) ((j * 100) / j2));
                    }
                }
            });

            Glide.with(mImageView.getContext()).load(str).transition(DrawableTransitionOptions.withCrossFade()).thumbnail(0.01f).listener(new RequestListener<Drawable>() {
                public boolean onLoadFailed(@Nullable GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                    Progress_Module.forget(str);
                    Poster_Glide_IMG_Loader.this.onFinished();
                    return false;
                }

                public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                    Progress_Module.forget(str);
                    Poster_Glide_IMG_Loader.this.onFinished();
                    return false;
                }
            }).into(this.mImageView);
        }
    }

}