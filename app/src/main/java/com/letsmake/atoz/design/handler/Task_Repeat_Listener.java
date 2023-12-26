package com.letsmake.atoz.design.handler;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class Task_Repeat_Listener implements OnTouchListener {

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action != 3) {
            switch (action) {
                case 0:
                    if (this.guideline.getVisibility() == View.GONE) {
                        this.guideline.setVisibility(View.VISIBLE);
                    }
                    this.handler.removeCallbacks(this.handlerRepeatRunnable);
                    this.handler.postDelayed(this.handlerRepeatRunnable, (long) this.initialInterval);
                    this.downView = view;
                    this.downView.setPressed(true);
                    this.clickListener.onClick(view);
                    return true;
                case 1:
                    this.guideline.setVisibility(View.GONE);
                    break;
                default:
                    return false;
            }
        }
        this.handler.removeCallbacks(this.handlerRepeatRunnable);
        this.downView.setPressed(false);
        this.downView = null;
        return true;
    }


    private int initialInterval;
    public final int normalInterval;

    private final OnClickListener clickListener;

    private View downView;
    private ImageView guideline;

    public Handler handler = new Handler();

    private Runnable handlerRepeatRunnable = new Runnable() {
        public void run() {
            Task_Repeat_Listener.this.handler.postDelayed(this, (long) Task_Repeat_Listener.this.normalInterval);
            Task_Repeat_Listener.this.clickListener.onClick(Task_Repeat_Listener.this.downView);
        }
    };

    public Task_Repeat_Listener(int i, int i2, ImageView imageView, OnClickListener onClickListener) {
        if (onClickListener == null) {
            throw new IllegalArgumentException("null runnable");
        } else if (i < 0 || i2 < 0) {
            throw new IllegalArgumentException("negative interval");
        } else {
            this.initialInterval = i;
            this.normalInterval = i2;
            this.clickListener = onClickListener;
            this.guideline = imageView;
        }
    }

}
