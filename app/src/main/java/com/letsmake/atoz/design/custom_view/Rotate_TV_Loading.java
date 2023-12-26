package com.letsmake.atoz.design.custom_view;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.letsmake.atoz.design.R;

public class Rotate_TV_Loading extends View {

    private Paint mPaint;
    private RectF shadowRectF, loadingRectF;

    private static final int DEFAULT_SHADOW_POSITION = 2;
    private static final int DEFAULT_SPEED_OF_DEGREE = 10;
    private static final int DEFAULT_WIDTH = 6;

    private float arc, speedOfArc;
    private boolean changeBigger = true;
    private int bottomDegree = 190, color;
    public boolean isStart = false;
    private int speedOfDegree, shadowPosition, topDegree = 10, width;

    private void initialization(Context context, AttributeSet attributeSet) {
        this.color = -1;
        this.width = convertDpToPx(context, 6.0f);
        this.shadowPosition = convertDpToPx(getContext(), 2.0f);
        this.speedOfDegree = 10;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.Rotate_TV_Loading);
            this.color = obtainStyledAttributes.getColor(0, -1);
            this.width = obtainStyledAttributes.getDimensionPixelSize(2, convertDpToPx(context, 6.0f));
            this.shadowPosition = obtainStyledAttributes.getInt(3, 2);
            this.speedOfDegree = obtainStyledAttributes.getInt(1, 10);
            obtainStyledAttributes.recycle();
        }
        this.speedOfArc = (float) (this.speedOfDegree / 4);
        this.mPaint = new Paint();
        this.mPaint.setColor(this.color);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Style.STROKE);
        this.mPaint.setStrokeWidth((float) this.width);
        this.mPaint.setStrokeCap(Cap.ROUND);
    }


    public Rotate_TV_Loading(Context context) {
        super(context);
        initialization(context, null);
    }

    public Rotate_TV_Loading(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initialization(context, attributeSet);
    }

    public Rotate_TV_Loading(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initialization(context, attributeSet);
    }


    @Override
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.arc = 10.0f;
        i4 = this.width;
        this.loadingRectF = new RectF((float) (i4 * 2), (float) (i4 * 2), (float) (i - (i4 * 2)), (float) (i2 - (i4 * 2)));
        i4 = this.width;
        int i5 = i4 * 2;
        int i6 = this.shadowPosition;
        this.shadowRectF = new RectF((float) (i5 + i6), (float) ((i4 * 2) + i6), (float) ((i - (i4 * 2)) + i6), (float) ((i2 - (i4 * 2)) + i6));
    }

    private void startLoaderAnimator() {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, "scaleX", new float[]{0.0f, 1.0f});
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this, "scaleY", new float[]{0.0f, 1.0f});
        ofFloat.setDuration(300);
        ofFloat.setInterpolator(new LinearInterpolator());
        ofFloat2.setDuration(300);
        ofFloat2.setInterpolator(new LinearInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{ofFloat, ofFloat2});
        animatorSet.start();
    }


    public void set_start_LoadingColor(int i) {
        this.color = i;
    }

    public int get_start_LoadingColor() {
        return this.color;
    }

    public void start_rotate() {
        startLoaderAnimator();
        this.isStart = true;
        invalidate();
    }

    public void stop_rotate() {
        stop_Loader_Animator();
        invalidate();
    }

    public boolean isStart() {
        return this.isStart;
    }


    public int convertDpToPx(Context context, float f) {
        return (int) TypedValue.applyDimension(1, f, context.getResources().getDisplayMetrics());
    }

    private void stop_Loader_Animator() {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, "scaleX", new float[]{1.0f, 0.0f});
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this, "scaleY", new float[]{1.0f, 0.0f});
        ofFloat.setDuration(300);
        ofFloat.setInterpolator(new LinearInterpolator());
        ofFloat2.setDuration(300);
        ofFloat2.setInterpolator(new LinearInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{ofFloat, ofFloat2});
        animatorSet.addListener(new AnimatorListener() {
            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
                Rotate_TV_Loading.this.isStart = false;
            }
        });
        animatorSet.start();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.isStart) {
            float f;
            this.mPaint.setColor(Color.parseColor("#1a000000"));
            Canvas canvas2 = canvas;
            canvas2.drawArc(this.shadowRectF, (float) this.topDegree, this.arc, false, this.mPaint);
            Canvas canvas3 = canvas;
            canvas3.drawArc(this.shadowRectF, (float) this.bottomDegree, this.arc, false, this.mPaint);
            this.mPaint.setColor(this.color);
            canvas2.drawArc(this.loadingRectF, (float) this.topDegree, this.arc, false, this.mPaint);
            canvas3.drawArc(this.loadingRectF, (float) this.bottomDegree, this.arc, false, this.mPaint);
            int i = this.topDegree;
            int i2 = this.speedOfDegree;
            this.topDegree = i + i2;
            this.bottomDegree += i2;
            i = this.topDegree;
            if (i > 360) {
                this.topDegree = i - 360;
            }
            i = this.bottomDegree;
            if (i > 360) {
                this.bottomDegree = i - 360;
            }
            if (this.changeBigger) {
                f = this.arc;
                if (f < 160.0f) {
                    this.arc = f + this.speedOfArc;
                    invalidate();
                }
            } else {
                f = this.arc;
                if (f > ((float) this.speedOfDegree)) {
                    this.arc = f - (this.speedOfArc * 2.0f);
                    invalidate();
                }
            }
            f = this.arc;
            if (f >= 160.0f || f <= 10.0f) {
//                this.changeBigger ^= 1;
                this.changeBigger ^= false;

                invalidate();
            }
        }
    }


}
