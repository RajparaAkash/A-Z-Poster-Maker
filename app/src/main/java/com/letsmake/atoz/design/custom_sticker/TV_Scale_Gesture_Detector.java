package com.letsmake.atoz.design.custom_sticker;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.letsmake.atoz.design.listener.Vector_2D;

public class TV_Scale_Gesture_Detector {
    private static final float PRESSURE_THRESHOLD = 0.67f;
    private static final String TAG = "Gesture_Detector";
    private boolean mActive0MostRecent, mInvalidGesture, mGestureInProgress;
    private int mActiveId0, mActiveId1;
    private MotionEvent mCurrEvent;
    private float mFocusX, mFocusY, mCurrPressure, mCurrLen, mCurrFingerDiffX, mCurrFingerDiffY, mPrevFingerDiffY, mPrevLen, mPrevPressure, mScaleFactor, mPrevFingerDiffX;
    private Vector_2D mCurrSpanVector = new Vector_2D();
    private final OnTVScaleGestureListener mListener;
    private MotionEvent mPrevEvent;
    private long mTimeDelta;

    public interface OnTVScaleGestureListener {
        boolean onScale(View view, TV_Scale_Gesture_Detector TVScaleGestureDetector);

        boolean onScaleBegin(View view, TV_Scale_Gesture_Detector TVScaleGestureDetector);

        void onScaleEnd(View view, TV_Scale_Gesture_Detector TVScaleGestureDetector);
    }


    public TV_Scale_Gesture_Detector(OnTVScaleGestureListener onTVScaleGestureListener) {
        this.mListener = onTVScaleGestureListener;
    }


    public static class SimpleOnTVScaleGestureListener implements OnTVScaleGestureListener {
        public boolean onScale(View view, TV_Scale_Gesture_Detector TVScaleGestureDetector) {
            return false;
        }

        public boolean onScaleBegin(View view, TV_Scale_Gesture_Detector TVScaleGestureDetector) {
            return true;
        }

        public void onScaleEnd(View view, TV_Scale_Gesture_Detector TVScaleGestureDetector) {
        }
    }


    private int findTVNewActiveIndex(MotionEvent motionEvent, int i, int i2) {
        int pointerCount = motionEvent.getPointerCount();
        int findPointerIndex = motionEvent.findPointerIndex(i);
        i = 0;
        while (i < pointerCount) {
            if (i != i2 && i != findPointerIndex) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public boolean onTVTouchEvent(View view, MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            resetTV();
        }
        boolean z = false;
        if (this.mInvalidGesture) {
            return false;
        }
        int i;
        if (this.mGestureInProgress) {
            switch (actionMasked) {
                case 1:
                    resetTV();
                    return true;
                case 2:
                    applyTVContext(view, motionEvent);
                    if (this.mCurrPressure / this.mPrevPressure <= PRESSURE_THRESHOLD || !this.mListener.onScale(view, this)) {
                        return true;
                    }
                    this.mPrevEvent.recycle();
                    this.mPrevEvent = MotionEvent.obtain(motionEvent);
                    return true;
                case 3:
                    this.mListener.onScaleEnd(view, this);
                    resetTV();
                    return true;
                case 5:
                    this.mListener.onScaleEnd(view, this);
                    actionMasked = this.mActiveId0;
                    i = this.mActiveId1;
                    resetTV();
                    this.mPrevEvent = MotionEvent.obtain(motionEvent);
                    if (!this.mActive0MostRecent) {
                        actionMasked = i;
                    }
                    this.mActiveId0 = actionMasked;
                    this.mActiveId1 = motionEvent.getPointerId(motionEvent.getActionIndex());
                    this.mActive0MostRecent = false;
                    if (motionEvent.findPointerIndex(this.mActiveId0) < 0 || this.mActiveId0 == this.mActiveId1) {
                        this.mActiveId0 = motionEvent.getPointerId(findTVNewActiveIndex(motionEvent, this.mActiveId1, -1));
                    }
                    applyTVContext(view, motionEvent);
                    this.mGestureInProgress = this.mListener.onScaleBegin(view, this);
                    return true;
                case 6:
                    actionMasked = motionEvent.getPointerCount();
                    i = motionEvent.getActionIndex();
                    int pointerId = motionEvent.getPointerId(i);
                    if (actionMasked > 2) {
                        actionMasked = this.mActiveId0;
                        if (pointerId == actionMasked) {
                            actionMasked = findTVNewActiveIndex(motionEvent, this.mActiveId1, i);
                            if (actionMasked >= 0) {
                                this.mListener.onScaleEnd(view, this);
                                this.mActiveId0 = motionEvent.getPointerId(actionMasked);
                                this.mActive0MostRecent = true;
                                this.mPrevEvent = MotionEvent.obtain(motionEvent);
                                applyTVContext(view, motionEvent);
                                this.mGestureInProgress = this.mListener.onScaleBegin(view, this);
                            } else {
                                z = true;
                            }
                        } else if (pointerId == this.mActiveId1) {
                            actionMasked = findTVNewActiveIndex(motionEvent, actionMasked, i);
                            if (actionMasked >= 0) {
                                this.mListener.onScaleEnd(view, this);
                                this.mActiveId1 = motionEvent.getPointerId(actionMasked);
                                this.mActive0MostRecent = false;
                                this.mPrevEvent = MotionEvent.obtain(motionEvent);
                                applyTVContext(view, motionEvent);
                                this.mGestureInProgress = this.mListener.onScaleBegin(view, this);
                            } else {
                                z = true;
                            }
                        }
                        this.mPrevEvent.recycle();
                        this.mPrevEvent = MotionEvent.obtain(motionEvent);
                        applyTVContext(view, motionEvent);
                    } else {
                        z = true;
                    }
                    if (!z) {
                        return true;
                    }
                    applyTVContext(view, motionEvent);
                    actionMasked = this.mActiveId0;
                    if (pointerId == actionMasked) {
                        actionMasked = this.mActiveId1;
                    }
                    i = motionEvent.findPointerIndex(actionMasked);
                    this.mFocusX = motionEvent.getX(i);
                    this.mFocusY = motionEvent.getY(i);
                    this.mListener.onScaleEnd(view, this);
                    resetTV();
                    this.mActiveId0 = actionMasked;
                    this.mActive0MostRecent = true;
                    return true;
                default:
                    return true;
            }
        } else if (actionMasked != 5) {
            switch (actionMasked) {
                case 0:
                    this.mActiveId0 = motionEvent.getPointerId(0);
                    this.mActive0MostRecent = true;
                    return true;
                case 1:
                    resetTV();
                    return true;
                default:
                    return true;
            }
        } else {
            MotionEvent motionEvent2 = this.mPrevEvent;
            if (motionEvent2 != null) {
                motionEvent2.recycle();
            }
            this.mPrevEvent = MotionEvent.obtain(motionEvent);
            this.mTimeDelta = 0;
            actionMasked = motionEvent.getActionIndex();
            i = motionEvent.findPointerIndex(this.mActiveId0);
            this.mActiveId1 = motionEvent.getPointerId(actionMasked);
            if (i < 0 || i == actionMasked) {
                this.mActiveId0 = motionEvent.getPointerId(findTVNewActiveIndex(motionEvent, this.mActiveId1, -1));
            }
            this.mActive0MostRecent = false;
            applyTVContext(view, motionEvent);
            this.mGestureInProgress = this.mListener.onScaleBegin(view, this);
            return true;
        }
    }

    @SuppressLint("LongLogTag")
    private void applyTVContext(View view, MotionEvent motionEvent) {
        MotionEvent motionEvent2 = this.mCurrEvent;
        if (motionEvent2 != null) {
            motionEvent2.recycle();
        }
        this.mCurrEvent = MotionEvent.obtain(motionEvent);
        this.mCurrLen = -1.0f;
        this.mPrevLen = -1.0f;
        this.mScaleFactor = -1.0f;
        this.mCurrSpanVector.set(0.0f, 0.0f);
        motionEvent2 = this.mPrevEvent;
        int findPointerIndex = motionEvent2.findPointerIndex(this.mActiveId0);
        int findPointerIndex2 = motionEvent2.findPointerIndex(this.mActiveId1);
        int findPointerIndex3 = motionEvent.findPointerIndex(this.mActiveId0);
        int findPointerIndex4 = motionEvent.findPointerIndex(this.mActiveId1);
        if (findPointerIndex < 0 || findPointerIndex2 < 0 || findPointerIndex3 < 0 || findPointerIndex4 < 0) {
            this.mInvalidGesture = true;
            Log.e(TAG, "Invalid MotionEvent stream detected.", new Throwable());
            if (this.mGestureInProgress) {
                this.mListener.onScaleEnd(view, this);
                return;
            }
            return;
        }
        float x = motionEvent2.getX(findPointerIndex);
        float y = motionEvent2.getY(findPointerIndex);
        float x2 = motionEvent2.getX(findPointerIndex2);
        float y2 = motionEvent2.getY(findPointerIndex2);
        float x3 = motionEvent.getX(findPointerIndex3);
        float y3 = motionEvent.getY(findPointerIndex3);
        x2 -= x;
        y2 -= y;
        x = motionEvent.getX(findPointerIndex4) - x3;
        y = motionEvent.getY(findPointerIndex4) - y3;
        this.mCurrSpanVector.set(x, y);
        this.mPrevFingerDiffX = x2;
        this.mPrevFingerDiffY = y2;
        this.mCurrFingerDiffX = x;
        this.mCurrFingerDiffY = y;
        this.mFocusX = (x * 0.5f) + x3;
        this.mFocusY = (y * 0.5f) + y3;
        this.mTimeDelta = motionEvent.getEventTime() - motionEvent2.getEventTime();
        this.mCurrPressure = motionEvent.getPressure(findPointerIndex3) + motionEvent.getPressure(findPointerIndex4);
        this.mPrevPressure = motionEvent2.getPressure(findPointerIndex) + motionEvent2.getPressure(findPointerIndex2);
    }

    private void resetTV() {
        MotionEvent motionEvent = this.mPrevEvent;
        if (motionEvent != null) {
            motionEvent.recycle();
            this.mPrevEvent = null;
        }
        motionEvent = this.mCurrEvent;
        if (motionEvent != null) {
            motionEvent.recycle();
            this.mCurrEvent = null;
        }
        this.mGestureInProgress = false;
        this.mActiveId0 = -1;
        this.mActiveId1 = -1;
        this.mInvalidGesture = false;
    }

    public boolean isInProgress() {
        return this.mGestureInProgress;
    }

    public float getTVFocusX() {
        return this.mFocusX;
    }

    public float getTVFocusY() {
        return this.mFocusY;
    }

    public float getCurrentSpan() {
        if (this.mCurrLen == -1.0f) {
            float f = this.mCurrFingerDiffX;
            float f2 = this.mCurrFingerDiffY;
            this.mCurrLen = (float) Math.sqrt((double) ((f * f) + (f2 * f2)));
        }
        return this.mCurrLen;
    }

    public Vector_2D getTVCurrentSpanVector() {
        return this.mCurrSpanVector;
    }

    public float getTVCurrentSpanX() {
        return this.mCurrFingerDiffX;
    }

    public float getTVCurrentSpanY() {
        return this.mCurrFingerDiffY;
    }

    public float getPreviousSpan() {
        if (this.mPrevLen == -1.0f) {
            float f = this.mPrevFingerDiffX;
            float f2 = this.mPrevFingerDiffY;
            this.mPrevLen = (float) Math.sqrt((double) ((f * f) + (f2 * f2)));
        }
        return this.mPrevLen;
    }

    public float getTVPreviousSpanX() {
        return this.mPrevFingerDiffX;
    }

    public float getTVPreviousSpanY() {
        return this.mPrevFingerDiffY;
    }

    public float getTVScaleFactor() {
        if (this.mScaleFactor == -1.0f) {
            this.mScaleFactor = getCurrentSpan() / getPreviousSpan();
        }
        return this.mScaleFactor;
    }

    public long getTVTimeDelta() {
        return this.mTimeDelta;
    }

    public long getTVEventTime() {
        return this.mCurrEvent.getEventTime();
    }
}
