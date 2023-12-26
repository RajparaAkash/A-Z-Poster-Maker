package com.letsmake.atoz.design.imageloader;

import android.content.res.Resources.Theme;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.Drawable;

import androidx.annotation.CheckResult;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;
import com.bumptech.glide.request.RequestOptions;

public final class Custom_Glide_Options extends RequestOptions implements Cloneable {

    private static Custom_Glide_Options centerCropTransform2, noAnimation5, fitCenterTransform0, circleCropTransform3, noTransformation4, centerInsideTransform1;

    @Override
    @CheckResult
    @NonNull
    public final Custom_Glide_Options sizeMultiplier(@FloatRange(from = 0.0d, to = 1.0d) float f) {
        return (Custom_Glide_Options) super.sizeMultiplier(f);
    }

    @Override
    @CheckResult
    @NonNull
    public final Custom_Glide_Options useUnlimitedSourceGeneratorsPool(boolean z) {
        return (Custom_Glide_Options) super.useUnlimitedSourceGeneratorsPool(z);
    }

    @Override
    @CheckResult
    @NonNull
    public final Custom_Glide_Options useAnimationPool(boolean z) {
        return (Custom_Glide_Options) super.useAnimationPool(z);
    }

    @Override
    @CheckResult
    @NonNull
    public final Custom_Glide_Options onlyRetrieveFromCache(boolean z) {
        return (Custom_Glide_Options) super.onlyRetrieveFromCache(z);
    }

    @Override
    @CheckResult
    @NonNull
    public final Custom_Glide_Options diskCacheStrategy(@NonNull DiskCacheStrategy diskCacheStrategy) {
        return (Custom_Glide_Options) super.diskCacheStrategy(diskCacheStrategy);
    }

    @Override
    @CheckResult
    @NonNull
    public final Custom_Glide_Options priority(@NonNull Priority priority) {
        return (Custom_Glide_Options) super.priority(priority);
    }


    @CheckResult
    @NonNull
    public static Custom_Glide_Options sizeGLideMultiplierOf(@FloatRange(from = 0.0d, to = 1.0d) float f) {
        return new Custom_Glide_Options().sizeMultiplier(f);
    }


    @CheckResult
    @NonNull
    public static Custom_Glide_Options fitCenterGLideTransform() {
        if (fitCenterTransform0 == null) {
            fitCenterTransform0 = new Custom_Glide_Options().fitCenter().autoClone();
        }
        return fitCenterTransform0;
    }

    @CheckResult
    @NonNull
    public static Custom_Glide_Options centerInsideGLideTransform() {
        if (centerInsideTransform1 == null) {
            centerInsideTransform1 = new Custom_Glide_Options().centerInside().autoClone();
        }
        return centerInsideTransform1;
    }

    @CheckResult
    @NonNull
    public static Custom_Glide_Options centerCropGLideTransform() {
        if (centerCropTransform2 == null) {
            centerCropTransform2 = new Custom_Glide_Options().centerCrop().autoClone();
        }
        return centerCropTransform2;
    }

    @CheckResult
    @NonNull
    public static Custom_Glide_Options circleCropGLideTransform() {
        if (circleCropTransform3 == null) {
            circleCropTransform3 = new Custom_Glide_Options().circleCrop().autoClone();
        }
        return circleCropTransform3;
    }

    @CheckResult
    @NonNull
    public static Custom_Glide_Options bitmapGLideTransform(@NonNull Transformation<Bitmap> transformation) {
        return new Custom_Glide_Options().transform((Transformation) transformation);
    }

    @CheckResult
    @NonNull
    public static Custom_Glide_Options noGLideTransformation() {
        if (noTransformation4 == null) {
            noTransformation4 = new Custom_Glide_Options().dontTransform().autoClone();
        }
        return noTransformation4;
    }

    @CheckResult
    @NonNull
    public static <T> Custom_Glide_Options GLideoption(@NonNull Option<T> option, @NonNull T t) {
        return new Custom_Glide_Options().set((Option) option, (Object) t);
    }

    @CheckResult
    @NonNull
    public static Custom_Glide_Options diskGLideCacheStrategyOf(@NonNull DiskCacheStrategy diskCacheStrategy) {
        return new Custom_Glide_Options().diskCacheStrategy(diskCacheStrategy);
    }

    @CheckResult
    @NonNull
    public static Custom_Glide_Options priorityGLideOf(@NonNull Priority priority) {
        return new Custom_Glide_Options().priority(priority);
    }

    @CheckResult
    @NonNull
    public static Custom_Glide_Options placeholderGLideOf(@Nullable Drawable drawable) {
        return new Custom_Glide_Options().placeholder(drawable);
    }

    @CheckResult
    @NonNull
    public static Custom_Glide_Options placeholderGLideOf(@DrawableRes int i) {
        return new Custom_Glide_Options().placeholder(i);
    }

    @CheckResult
    @NonNull
    public static Custom_Glide_Options errorGLideOf(@Nullable Drawable drawable) {
        return new Custom_Glide_Options().error(drawable);
    }

    @CheckResult
    @NonNull
    public static Custom_Glide_Options errorGLideOf(@DrawableRes int i) {
        return new Custom_Glide_Options().error(i);
    }

    @CheckResult
    @NonNull
    public static Custom_Glide_Options skipGLideMemoryCacheOf(boolean z) {
        return new Custom_Glide_Options().skipMemoryCache(z);
    }

    @CheckResult
    @NonNull
    public static Custom_Glide_Options overrideGLideOf(@IntRange(from = 0) int i, @IntRange(from = 0) int i2) {
        return new Custom_Glide_Options().override(i, i2);
    }

    @CheckResult
    @NonNull
    public static Custom_Glide_Options GLideoverrideOf(@IntRange(from = 0) int i) {
        return new Custom_Glide_Options().override(i);
    }

    @CheckResult
    @NonNull
    public static Custom_Glide_Options signatureGLideOf(@NonNull Key key) {
        return new Custom_Glide_Options().signature(key);
    }

    @CheckResult
    @NonNull
    public static Custom_Glide_Options decodeGLideTypeOf(@NonNull Class<?> cls) {
        return new Custom_Glide_Options().decode((Class) cls);
    }

    @CheckResult
    @NonNull
    public static Custom_Glide_Options formatGLideOf(@NonNull DecodeFormat decodeFormat) {
        return new Custom_Glide_Options().format(decodeFormat);
    }

    @CheckResult
    @NonNull
    public static Custom_Glide_Options frameGLideOf(@IntRange(from = 0) long j) {
        return new Custom_Glide_Options().frame(j);
    }

    @CheckResult
    @NonNull
    public static Custom_Glide_Options downGLidesampleOf(@NonNull DownsampleStrategy downsampleStrategy) {
        return new Custom_Glide_Options().downsample(downsampleStrategy);
    }

    @CheckResult
    @NonNull
    public static Custom_Glide_Options timeGLideoutOf(@IntRange(from = 0) int i) {
        return new Custom_Glide_Options().timeout(i);
    }

    @CheckResult
    @NonNull
    public static Custom_Glide_Options encodeGLideQualityOf(@IntRange(from = 0, to = 100) int i) {
        return new Custom_Glide_Options().encodeQuality(i);
    }

    @CheckResult
    @NonNull
    public static Custom_Glide_Options encodeGLideFormatOf(@NonNull CompressFormat compressFormat) {
        return new Custom_Glide_Options().encodeFormat(compressFormat);
    }

    @CheckResult
    @NonNull
    public static Custom_Glide_Options noGLideAnimation() {
        if (noAnimation5 == null) {
            noAnimation5 = new Custom_Glide_Options().dontAnimate().autoClone();
        }
        return noAnimation5;
    }

    @Override
    @CheckResult
    @NonNull
    public final Custom_Glide_Options placeholder(@Nullable Drawable drawable) {
        return (Custom_Glide_Options) super.placeholder(drawable);
    }

    @Override
    @CheckResult
    @NonNull
    public final Custom_Glide_Options placeholder(@DrawableRes int i) {
        return (Custom_Glide_Options) super.placeholder(i);
    }

    @Override
    @CheckResult
    @NonNull
    public final Custom_Glide_Options fallback(@Nullable Drawable drawable) {
        return (Custom_Glide_Options) super.fallback(drawable);
    }

    @Override
    @CheckResult
    @NonNull
    public final Custom_Glide_Options fallback(@DrawableRes int i) {
        return (Custom_Glide_Options) super.fallback(i);
    }


    @Override
    @CheckResult
    @NonNull
    public final Custom_Glide_Options encodeFormat(@NonNull CompressFormat compressFormat) {
        return (Custom_Glide_Options) super.encodeFormat(compressFormat);
    }

    @CheckResult
    @Override
    @NonNull
    public final Custom_Glide_Options encodeQuality(@IntRange(from = 0, to = 100) int i) {
        return (Custom_Glide_Options) super.encodeQuality(i);
    }

    @Override
    @CheckResult
    @NonNull
    public final Custom_Glide_Options frame(@IntRange(from = 0) long j) {
        return (Custom_Glide_Options) super.frame(j);
    }

    @Override
    @CheckResult
    @NonNull
    public final Custom_Glide_Options format(@NonNull DecodeFormat decodeFormat) {
        return (Custom_Glide_Options) super.format(decodeFormat);
    }

    @Override
    @CheckResult
    @NonNull
    public final Custom_Glide_Options disallowHardwareConfig() {
        return (Custom_Glide_Options) super.disallowHardwareConfig();
    }

    @Override
    @CheckResult
    @NonNull
    public final Custom_Glide_Options downsample(@NonNull DownsampleStrategy downsampleStrategy) {
        return (Custom_Glide_Options) super.downsample(downsampleStrategy);
    }

    @Override
    @CheckResult
    @NonNull
    public final Custom_Glide_Options timeout(@IntRange(from = 0) int i) {
        return (Custom_Glide_Options) super.timeout(i);
    }

    @Override
    @CheckResult
    @NonNull
    public final Custom_Glide_Options optionalCenterCrop() {
        return (Custom_Glide_Options) super.optionalCenterCrop();
    }

    @Override
    @CheckResult
    @NonNull
    public final Custom_Glide_Options centerCrop() {
        return (Custom_Glide_Options) super.centerCrop();
    }

    @Override
    @CheckResult
    @NonNull
    public final Custom_Glide_Options optionalFitCenter() {
        return (Custom_Glide_Options) super.optionalFitCenter();
    }

    @Override
    @CheckResult
    @NonNull
    public final Custom_Glide_Options fitCenter() {
        return (Custom_Glide_Options) super.fitCenter();
    }

    @Override
    @CheckResult
    @NonNull
    public final Custom_Glide_Options optionalCenterInside() {
        return (Custom_Glide_Options) super.optionalCenterInside();
    }

    @Override
    @CheckResult
    @NonNull
    public final Custom_Glide_Options centerInside() {
        return (Custom_Glide_Options) super.centerInside();
    }

    @Override
    @CheckResult
    @NonNull
    public final Custom_Glide_Options optionalCircleCrop() {
        return (Custom_Glide_Options) super.optionalCircleCrop();
    }

    @Override
    @CheckResult
    @NonNull
    public final Custom_Glide_Options circleCrop() {
        return (Custom_Glide_Options) super.circleCrop();
    }

    @Override
    @CheckResult
    @NonNull
    public final Custom_Glide_Options transform(@NonNull Transformation<Bitmap> transformation) {
        return (Custom_Glide_Options) super.transform(transformation);
    }

    @Override
    @SafeVarargs
    @CheckResult
    @NonNull
    public final Custom_Glide_Options transforms(@NonNull Transformation<Bitmap>... transformationArr) {
        return (Custom_Glide_Options) super.transforms(transformationArr);
    }

    @Override
    @CheckResult
    @NonNull
    public final Custom_Glide_Options optionalTransform(@NonNull Transformation<Bitmap> transformation) {
        return (Custom_Glide_Options) super.optionalTransform(transformation);
    }

    @Override
    @CheckResult
    @NonNull
    public final <T> Custom_Glide_Options optionalTransform(@NonNull Class<T> cls, @NonNull Transformation<T> transformation) {
        return (Custom_Glide_Options) super.optionalTransform((Class) cls, (Transformation) transformation);
    }

    @Override
    @CheckResult
    @NonNull
    public final <T> Custom_Glide_Options transform(@NonNull Class<T> cls, @NonNull Transformation<T> transformation) {
        return (Custom_Glide_Options) super.transform((Class) cls, (Transformation) transformation);
    }

    @Override
    @CheckResult
    @NonNull
    public final Custom_Glide_Options dontTransform() {
        return (Custom_Glide_Options) super.dontTransform();
    }

    @Override
    @CheckResult
    @NonNull
    public final Custom_Glide_Options error(@Nullable Drawable drawable) {
        return (Custom_Glide_Options) super.error(drawable);
    }

    @Override
    @CheckResult
    @NonNull
    public final Custom_Glide_Options error(@DrawableRes int i) {
        return (Custom_Glide_Options) super.error(i);
    }

    @Override
    @CheckResult
    @NonNull
    public final Custom_Glide_Options theme(@Nullable Theme theme) {
        return (Custom_Glide_Options) super.theme(theme);
    }

    @CheckResult
    @NonNull
    public final Custom_Glide_Options skipMemoryCache(boolean z) {
        return (Custom_Glide_Options) super.skipMemoryCache(z);
    }

    @Override
    @CheckResult
    @NonNull
    public final Custom_Glide_Options override(int i, int i2) {
        return (Custom_Glide_Options) super.override(i, i2);
    }

    @Override
    @CheckResult
    @NonNull
    public final Custom_Glide_Options override(int i) {
        return (Custom_Glide_Options) super.override(i);
    }

    @Override
    @CheckResult
    @NonNull
    public final Custom_Glide_Options signature(@NonNull Key key) {
        return (Custom_Glide_Options) super.signature(key);
    }

    @Override
    @CheckResult
    public final Custom_Glide_Options clone() {
        return (Custom_Glide_Options) super.clone();
    }

    @Override
    @CheckResult
    @NonNull
    public final <T> Custom_Glide_Options set(@NonNull Option<T> option, @NonNull T t) {
        return (Custom_Glide_Options) super.set(option, t);
    }

    @Override
    @CheckResult
    @NonNull
    public final Custom_Glide_Options decode(@NonNull Class<?> cls) {
        return (Custom_Glide_Options) super.decode(cls);
    }

    @Override
    @CheckResult
    @NonNull
    public final Custom_Glide_Options dontAnimate() {
        return (Custom_Glide_Options) super.dontAnimate();
    }

    @Override
    @CheckResult
    @NonNull
    public final Custom_Glide_Options apply(@NonNull RequestOptions requestOptions) {
        return (Custom_Glide_Options) super.apply(requestOptions);
    }

    @Override
    @NonNull
    public final Custom_Glide_Options lock() {
        return (Custom_Glide_Options) super.lock();
    }

    @Override
    @NonNull
    public final Custom_Glide_Options autoClone() {
        return (Custom_Glide_Options) super.autoClone();
    }
}
