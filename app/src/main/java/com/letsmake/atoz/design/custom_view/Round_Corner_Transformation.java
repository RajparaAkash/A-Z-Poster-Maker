package com.letsmake.atoz.design.custom_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

import java.security.MessageDigest;

public class Round_Corner_Transformation implements Transformation<Bitmap> {

    private BitmapPool mBitmapPool;
    private int mColor, mBorder;
    private CornerType mCornerType;
    private int mMargin, mDiameter, mRadius;


    public void updateDiskCacheKey(MessageDigest messageDigest) {
    }

    public Round_Corner_Transformation(Context context, int i, int i2) {
        this(context, i, i2, CornerType.ALL);
    }

    public Round_Corner_Transformation(Context context, int i, int i2, int i3, int i4) {
        this(context, i, i2, CornerType.BORDER);
        this.mColor = i3;
        this.mBorder = i4;
    }

    public Round_Corner_Transformation(BitmapPool bitmapPool, int i, int i2) {
        this(bitmapPool, i, i2, CornerType.ALL);
    }

    public enum CornerType {
        ALL, TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT, TOP, BOTTOM, LEFT, RIGHT, OTHER_TOP_LEFT, OTHER_TOP_RIGHT, OTHER_BOTTOM_LEFT, OTHER_BOTTOM_RIGHT,
        DIAGONAL_FROM_TOP_LEFT, DIAGONAL_FROM_TOP_RIGHT, BORDER
    }

    public Round_Corner_Transformation(Context context, int i, int i2, CornerType cornerType) {
        this(Glide.get(context).getBitmapPool(), i, i2, cornerType);
    }

    public Round_Corner_Transformation(BitmapPool bitmapPool, int i, int i2, CornerType cornerType) {
        this.mBitmapPool = bitmapPool;
        this.mRadius = i;
        this.mDiameter = this.mRadius * 2;
        this.mMargin = i2;
        this.mCornerType = cornerType;
    }

    @Override
    public Resource<Bitmap> transform(Context context, Resource<Bitmap> resource, int i, int i2) {
        Bitmap bitmap = (Bitmap) resource.get();
        int width = bitmap.getWidth();
        i = bitmap.getHeight();
        Bitmap bitmap2 = this.mBitmapPool.get(width, i, Config.ARGB_8888);
        if (bitmap2 == null) {
            bitmap2 = Bitmap.createBitmap(width, i, Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(bitmap2);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP));
        drawRoundCornerRectFromPaint(canvas, paint, (float) width, (float) i);
        return BitmapResource.obtain(bitmap2, this.mBitmapPool);
    }


    private void drawRoundTopLeftRoundRect(Canvas canvas, Paint paint, float f, float f2) {
        int i = this.mMargin;
        float f3 = (float) i;
        float f4 = (float) i;
        int i2 = this.mDiameter;
        RectF rectF = new RectF(f3, f4, (float) (i + i2), (float) (i + i2));
        i = this.mRadius;
        canvas.drawRoundRect(rectF, (float) i, (float) i, paint);
        i = this.mMargin;
        f3 = (float) i;
        int i3 = this.mRadius;
        canvas.drawRect(new RectF(f3, (float) (i + i3), (float) (i + i3), f2), paint);
        i = this.mMargin;
        canvas.drawRect(new RectF((float) (this.mRadius + i), (float) i, f, f2), paint);
    }

    private void drawRoundCornerRectFromPaint(Canvas canvas, Paint paint, float f, float f2) {
        int i = this.mMargin;
        f -= (float) i;
        f2 -= (float) i;
        int i2;
        RectF rectF;
        int i3;
        switch (this.mCornerType) {
            case ALL:
                i2 = this.mMargin;
                rectF = new RectF((float) i2, (float) i2, f, f2);
                i3 = this.mRadius;
                canvas.drawRoundRect(rectF, (float) i3, (float) i3, paint);
                return;
            case TOP_LEFT:
                drawRoundTopLeftRoundRect(canvas, paint, f, f2);
                return;
            case TOP_RIGHT:
                drawTopRight_RoundRect(canvas, paint, f, f2);
                return;
            case BOTTOM_LEFT:
                drawBottomLeft_RoundRect(canvas, paint, f, f2);
                return;
            case BOTTOM_RIGHT:
                drawBottomRight_RoundRect(canvas, paint, f, f2);
                return;
            case TOP:
                drawTop_RoundRect(canvas, paint, f, f2);
                return;
            case BOTTOM:
                draw_Bottom_RoundRect(canvas, paint, f, f2);
                return;
            case LEFT:
                draw_Left_Round_Rect(canvas, paint, f, f2);
                return;
            case RIGHT:
                draw_Right_Round_Rect(canvas, paint, f, f2);
                return;
            case OTHER_TOP_LEFT:
                draw_Other_TopLeft_Round_Rect(canvas, paint, f, f2);
                return;
            case OTHER_TOP_RIGHT:
                draw_Other_TopRight_RoundRect(canvas, paint, f, f2);
                return;
            case OTHER_BOTTOM_LEFT:
                draw_Bottom_Left_Round_Rect(canvas, paint, f, f2);
                return;
            case OTHER_BOTTOM_RIGHT:
                draw_Bottom_Right_RoundRect(canvas, paint, f, f2);
                return;
            case DIAGONAL_FROM_TOP_LEFT:
                draw_Diagonal_TopLeft_RoundRect(canvas, paint, f, f2);
                return;
            case DIAGONAL_FROM_TOP_RIGHT:
                draw_DiagonalFrom_TopRight_RoundRect(canvas, paint, f, f2);
                return;
            case BORDER:
                drawRoundCornerBorder(canvas, paint, f, f2);
                return;
            default:
                i2 = this.mMargin;
                rectF = new RectF((float) i2, (float) i2, f, f2);
                i3 = this.mRadius;
                canvas.drawRoundRect(rectF, (float) i3, (float) i3, paint);
                return;
        }
    }

    private void drawTopRight_RoundRect(Canvas canvas, Paint paint, float f, float f2) {
        int i = this.mDiameter;
        float f3 = f - ((float) i);
        int i2 = this.mMargin;
        RectF rectF = new RectF(f3, (float) i2, f, (float) (i2 + i));
        i = this.mRadius;
        canvas.drawRoundRect(rectF, (float) i, (float) i, paint);
        i = this.mMargin;
        canvas.drawRect(new RectF((float) i, (float) i, f - ((float) this.mRadius), f2), paint);
        i = this.mRadius;
        canvas.drawRect(new RectF(f - ((float) i), (float) (this.mMargin + i), f, f2), paint);
    }

    private void drawBottomLeft_RoundRect(Canvas canvas, Paint paint, float f, float f2) {
        int i = this.mMargin;
        float f3 = (float) i;
        int i2 = this.mDiameter;
        RectF rectF = new RectF(f3, f2 - ((float) i2), (float) (i + i2), f2);
        i = this.mRadius;
        canvas.drawRoundRect(rectF, (float) i, (float) i, paint);
        i = this.mMargin;
        canvas.drawRect(new RectF((float) i, (float) i, (float) (i + this.mDiameter), f2 - ((float) this.mRadius)), paint);
        i = this.mMargin;
        canvas.drawRect(new RectF((float) (this.mRadius + i), (float) i, f, f2), paint);
    }

    private void drawBottomRight_RoundRect(Canvas canvas, Paint paint, float f, float f2) {
        int i = this.mDiameter;
        RectF rectF = new RectF(f - ((float) i), f2 - ((float) i), f, f2);
        i = this.mRadius;
        canvas.drawRoundRect(rectF, (float) i, (float) i, paint);
        i = this.mMargin;
        canvas.drawRect(new RectF((float) i, (float) i, f - ((float) this.mRadius), f2), paint);
        i = this.mRadius;
        canvas.drawRect(new RectF(f - ((float) i), (float) this.mMargin, f, f2 - ((float) i)), paint);
    }

    private void drawTop_RoundRect(Canvas canvas, Paint paint, float f, float f2) {
        int i = this.mMargin;
        RectF rectF = new RectF((float) i, (float) i, f, (float) (i + this.mDiameter));
        i = this.mRadius;
        canvas.drawRoundRect(rectF, (float) i, (float) i, paint);
        i = this.mMargin;
        canvas.drawRect(new RectF((float) i, (float) (i + this.mRadius), f, f2), paint);
    }

    private void draw_Bottom_RoundRect(Canvas canvas, Paint paint, float f, float f2) {
        RectF rectF = new RectF((float) this.mMargin, f2 - ((float) this.mDiameter), f, f2);
        int i = this.mRadius;
        canvas.drawRoundRect(rectF, (float) i, (float) i, paint);
        i = this.mMargin;
        canvas.drawRect(new RectF((float) i, (float) i, f, f2 - ((float) this.mRadius)), paint);
    }

    private void draw_Left_Round_Rect(Canvas canvas, Paint paint, float f, float f2) {
        int i = this.mMargin;
        RectF rectF = new RectF((float) i, (float) i, (float) (i + this.mDiameter), f2);
        i = this.mRadius;
        canvas.drawRoundRect(rectF, (float) i, (float) i, paint);
        i = this.mMargin;
        canvas.drawRect(new RectF((float) (this.mRadius + i), (float) i, f, f2), paint);
    }

    private void draw_Right_Round_Rect(Canvas canvas, Paint paint, float f, float f2) {
        RectF rectF = new RectF(f - ((float) this.mDiameter), (float) this.mMargin, f, f2);
        int i = this.mRadius;
        canvas.drawRoundRect(rectF, (float) i, (float) i, paint);
        i = this.mMargin;
        canvas.drawRect(new RectF((float) i, (float) i, f - ((float) this.mRadius), f2), paint);
    }

    private void draw_Other_TopLeft_Round_Rect(Canvas canvas, Paint paint, float f, float f2) {
        RectF rectF = new RectF((float) this.mMargin, f2 - ((float) this.mDiameter), f, f2);
        int i = this.mRadius;
        canvas.drawRoundRect(rectF, (float) i, (float) i, paint);
        rectF = new RectF(f - ((float) this.mDiameter), (float) this.mMargin, f, f2);
        i = this.mRadius;
        canvas.drawRoundRect(rectF, (float) i, (float) i, paint);
        i = this.mMargin;
        float f3 = (float) i;
        float f4 = (float) i;
        int i2 = this.mRadius;
        canvas.drawRect(new RectF(f3, f4, f - ((float) i2), f2 - ((float) i2)), paint);
    }

    private void draw_Other_TopRight_RoundRect(Canvas canvas, Paint paint, float f, float f2) {
        int i = this.mMargin;
        RectF rectF = new RectF((float) i, (float) i, (float) (i + this.mDiameter), f2);
        i = this.mRadius;
        canvas.drawRoundRect(rectF, (float) i, (float) i, paint);
        rectF = new RectF((float) this.mMargin, f2 - ((float) this.mDiameter), f, f2);
        i = this.mRadius;
        canvas.drawRoundRect(rectF, (float) i, (float) i, paint);
        i = this.mMargin;
        int i2 = this.mRadius;
        canvas.drawRect(new RectF((float) (i + i2), (float) i, f, f2 - ((float) i2)), paint);
    }

    private void draw_Bottom_Left_Round_Rect(Canvas canvas, Paint paint, float f, float f2) {
        int i = this.mMargin;
        RectF rectF = new RectF((float) i, (float) i, f, (float) (i + this.mDiameter));
        i = this.mRadius;
        canvas.drawRoundRect(rectF, (float) i, (float) i, paint);
        rectF = new RectF(f - ((float) this.mDiameter), (float) this.mMargin, f, f2);
        i = this.mRadius;
        canvas.drawRoundRect(rectF, (float) i, (float) i, paint);
        i = this.mMargin;
        float f3 = (float) i;
        int i2 = this.mRadius;
        canvas.drawRect(new RectF(f3, (float) (i + i2), f - ((float) i2), f2), paint);
    }

    private void draw_Bottom_Right_RoundRect(Canvas canvas, Paint paint, float f, float f2) {
        int i = this.mMargin;
        RectF rectF = new RectF((float) i, (float) i, f, (float) (i + this.mDiameter));
        i = this.mRadius;
        canvas.drawRoundRect(rectF, (float) i, (float) i, paint);
        i = this.mMargin;
        rectF = new RectF((float) i, (float) i, (float) (i + this.mDiameter), f2);
        i = this.mRadius;
        canvas.drawRoundRect(rectF, (float) i, (float) i, paint);
        i = this.mMargin;
        int i2 = this.mRadius;
        canvas.drawRect(new RectF((float) (i + i2), (float) (i + i2), f, f2), paint);
    }

    private void draw_Diagonal_TopLeft_RoundRect(Canvas canvas, Paint paint, float f, float f2) {
        int i = this.mMargin;
        float f3 = (float) i;
        float f4 = (float) i;
        int i2 = this.mDiameter;
        RectF rectF = new RectF(f3, f4, (float) (i + i2), (float) (i + i2));
        i = this.mRadius;
        canvas.drawRoundRect(rectF, (float) i, (float) i, paint);
        i = this.mDiameter;
        rectF = new RectF(f - ((float) i), f2 - ((float) i), f, f2);
        i = this.mRadius;
        canvas.drawRoundRect(rectF, (float) i, (float) i, paint);
        i = this.mMargin;
        canvas.drawRect(new RectF((float) i, (float) (i + this.mRadius), f - ((float) this.mDiameter), f2), paint);
        i = this.mMargin;
        canvas.drawRect(new RectF((float) (this.mDiameter + i), (float) i, f, f2 - ((float) this.mRadius)), paint);
    }

    private void draw_DiagonalFrom_TopRight_RoundRect(Canvas canvas, Paint paint, float f, float f2) {
        int i = this.mDiameter;
        float f3 = f - ((float) i);
        int i2 = this.mMargin;
        RectF rectF = new RectF(f3, (float) i2, f, (float) (i2 + i));
        i = this.mRadius;
        canvas.drawRoundRect(rectF, (float) i, (float) i, paint);
        i = this.mMargin;
        f3 = (float) i;
        i2 = this.mDiameter;
        rectF = new RectF(f3, f2 - ((float) i2), (float) (i + i2), f2);
        i = this.mRadius;
        canvas.drawRoundRect(rectF, (float) i, (float) i, paint);
        i = this.mMargin;
        f3 = (float) i;
        float f4 = (float) i;
        i2 = this.mRadius;
        canvas.drawRect(new RectF(f3, f4, f - ((float) i2), f2 - ((float) i2)), paint);
        i = this.mMargin;
        int i3 = this.mRadius;
        canvas.drawRect(new RectF((float) (i + i3), (float) (i + i3), f, f2), paint);
    }

    private void drawRoundCornerBorder(Canvas canvas, Paint paint, float f, float f2) {
        Paint paint2 = new Paint();
        paint2.setStyle(Style.STROKE);
        int i = this.mColor;
        if (i != -1) {
            paint2.setColor(i);
        } else {
            paint2.setColor(-16777216);
        }
        paint2.setStrokeWidth((float) this.mBorder);
        int i2 = this.mMargin;
        RectF rectF = new RectF((float) i2, (float) i2, f, f2);
        i2 = this.mRadius;
        canvas.drawRoundRect(rectF, (float) i2, (float) i2, paint);
        i = this.mMargin;
        RectF rectF2 = new RectF((float) i, (float) i, f, f2);
        int i3 = this.mRadius;
        canvas.drawRoundRect(rectF2, (float) i3, (float) i3, paint2);
    }

    public String getRoundCornerId() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("RoundedTransformation(radius=");
        stringBuilder.append(this.mRadius);
        stringBuilder.append(", margin=");
        stringBuilder.append(this.mMargin);
        stringBuilder.append(", diameter=");
        stringBuilder.append(this.mDiameter);
        stringBuilder.append(", cornerType=");
        stringBuilder.append(this.mCornerType.name());
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
