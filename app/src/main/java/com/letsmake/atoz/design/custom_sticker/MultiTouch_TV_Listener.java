package com.letsmake.atoz.design.custom_sticker;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;

import androidx.core.view.MotionEventCompat;

import com.letsmake.atoz.design.custom_sticker.TV_Scale_Gesture_Detector.SimpleOnTVScaleGestureListener;
import com.letsmake.atoz.design.listener.Vector_2D;

public class MultiTouch_TV_Listener implements OnTouchListener {
    private static final int INVALID_POINTER_ID = -1;
    Bitmap bitmap;
    boolean bt = false;
    GestureDetector gd = null;
    public boolean isRotateEnabled = true;
    public boolean isRotationEnabled = false;
    public boolean isTranslateEnabled = true;
    private TouchCallbackListener listener = null;
    private int mActivePointerId = -1;
    private float mPrevX;
    private float mPrevY;
    private TV_Scale_Gesture_Detector mTVScaleGestureDetector = new TV_Scale_Gesture_Detector(new TVScaleGestureListener());
    public float maximumScale = 8.0f;
    public float minimumScale = 0.5f;

    private class TVScaleGestureListener extends SimpleOnTVScaleGestureListener {
        private float mPivotX;
        private float mPivotY;
        private Vector_2D mPrevSpanVector;

        private TVScaleGestureListener() {
            mPrevSpanVector = new Vector_2D();
        }

        public boolean onScaleBegin(View view, TV_Scale_Gesture_Detector TVScaleGestureDetector) {
            this.mPivotX = TVScaleGestureDetector.getTVFocusX();
            this.mPivotY = TVScaleGestureDetector.getTVFocusY();
            this.mPrevSpanVector.set(TVScaleGestureDetector.getTVCurrentSpanVector());
            return true;
        }

        public boolean onScale(View view, TV_Scale_Gesture_Detector TVScaleGestureDetector) {
            TransformInfo transformInfo = new TransformInfo();
            float f = 0.0f;
            transformInfo.deltaAngle = MultiTouch_TV_Listener.this.isRotateEnabled ? Vector_2D.getVectorAngle(this.mPrevSpanVector, TVScaleGestureDetector.getTVCurrentSpanVector()) : 0.0f;
            transformInfo.deltaX = MultiTouch_TV_Listener.this.isTranslateEnabled ? TVScaleGestureDetector.getTVFocusX() - this.mPivotX : 0.0f;
            if (MultiTouch_TV_Listener.this.isTranslateEnabled) {
                f = TVScaleGestureDetector.getTVFocusY() - this.mPivotY;
            }
            transformInfo.deltaY = f;
            transformInfo.pivotX = this.mPivotX;
            transformInfo.pivotY = this.mPivotY;
            transformInfo.minimumScale = MultiTouch_TV_Listener.this.minimumScale;
            transformInfo.maximumScale = MultiTouch_TV_Listener.this.maximumScale;
            MultiTouch_TV_Listener.this.move(view, transformInfo);
            return false;
        }
    }

    public interface TouchCallbackListener {
        void onMidX(View view);

        void onMidXY(View view);

        void onMidY(View view);

        void onTouchCallback(View view, MotionEvent motionEvent);

        void onTouchMoveCallback(View view, MotionEvent motionEvent);

        void onTouchUpCallback(View view, MotionEvent motionEvent);

        void onXY(View view);
    }

    private class TransformInfo {
        public float deltaAngle;
        public float deltaScale;
        public float deltaX;
        public float deltaY;
        public float maximumScale;
        public float minimumScale;
        public float pivotX;
        public float pivotY;

        private TransformInfo() {
        }
    }

    private static float adjustAngle(float f) {
        return f > 180.0f ? f - 360.0f : f < -180.0f ? f + 360.0f : f;
    }

    private void adjustTranslation(View view, float f, float f2) {
        float[] fArr = new float[2];
        fArr[0] = f;
        int i = 1;
        fArr[1] = f2;
        view.getMatrix().mapVectors(fArr);
        f2 = view.getTranslationY() + fArr[1];
        view.setTranslationX(view.getTranslationX() + fArr[0]);
        view.setTranslationY(f2);
        IntelligentTVAutoFit autofitTextRel = (IntelligentTVAutoFit) view;
        float mainWidth = autofitTextRel.getMainWidth();
        float mainHeight = autofitTextRel.getMainHeight();
        float width = (float) (view.getWidth() / 2);
        float height = (float) (view.getHeight() / 2);
        int y = (int) (view.getY() + height);
        float x = (float) ((int) (view.getX() + width));
        mainWidth /= 2.0f;
        float f3 = (float) ((int) (Resources.getSystem().getDisplayMetrics().density * 5.0f));
        if (x <= mainWidth - f3 || x >= mainWidth + f3) {
            i = 0;
        } else {
            view.setX(mainWidth - width);
        }
        mainWidth = (float) y;
        mainHeight /= 2.0f;
        if (mainWidth <= mainHeight - f3 || mainWidth >= f3 + mainHeight) {
            height = 0.0f;
        } else {
            view.setY(mainHeight - height);
            height = Float.MIN_VALUE;
        }
        TouchCallbackListener touchCallbackListener;
        if (i != 0 && height != 0.0f) {
            touchCallbackListener = this.listener;
            if (touchCallbackListener != null) {
                touchCallbackListener.onMidXY(view);
            }
        } else if (i != 0) {
            touchCallbackListener = this.listener;
            if (touchCallbackListener != null) {
                touchCallbackListener.onMidX(view);
            }
        } else if (height != 0.0f) {
            touchCallbackListener = this.listener;
            if (touchCallbackListener != null) {
                touchCallbackListener.onMidY(view);
            }
        } else {
            touchCallbackListener = this.listener;
            if (touchCallbackListener != null) {
                touchCallbackListener.onXY(view);
            }
        }
        f = autofitTextRel.getRotation();
        if (Math.abs(90.0f - Math.abs(f)) <= 5.0f) {
            f = f > 0.0f ? 90.0f : -90.0f;
        }
        if (Math.abs(0.0f - Math.abs(f)) <= 5.0f) {
            f = f > 0.0f ? 0.0f : -0.0f;
        }
        if (Math.abs(180.0f - Math.abs(f)) <= 5.0f) {
            f = f > 0.0f ? 180.0f : -180.0f;
        }
        view.setRotation(f);
    }

    private static void computeRenderOffset(View view, float f, float f2) {
        if (view.getPivotX() != f || view.getPivotY() != f2) {
            float[] fArr = new float[]{0.0f, 0.0f};
            view.getMatrix().mapPoints(fArr);
            view.setPivotX(f);
            view.setPivotY(f2);
            float[] fArr2 = new float[]{0.0f, 0.0f};
            view.getMatrix().mapPoints(fArr2);
            float f3 = fArr2[1] - fArr[1];
            view.setTranslationX(view.getTranslationX() - (fArr2[0] - fArr[0]));
            view.setTranslationY(view.getTranslationY() - f3);
        }
    }

    public MultiTouch_TV_Listener setGestureListener(GestureDetector gestureDetector) {
        this.gd = gestureDetector;
        return this;
    }

    public MultiTouch_TV_Listener setOnTouchCallbackListener(TouchCallbackListener touchCallbackListener) {
        this.listener = touchCallbackListener;
        return this;
    }

    public MultiTouch_TV_Listener enableRotation(boolean z) {
        this.isRotationEnabled = z;
        return this;
    }

    public MultiTouch_TV_Listener setMinScale(float f) {
        this.minimumScale = f;
        return this;
    }


    public void move(View view, TransformInfo transformInfo) {
        if (this.isRotationEnabled) {
            view.setRotation(adjustAngle(view.getRotation() + transformInfo.deltaAngle));
        }
    }

    public boolean handleTransparency(View view, MotionEvent motionEvent) {
        try {
            boolean z = true;
            if (motionEvent.getAction() == 2 && this.bt) {
                return true;
            }
            if (motionEvent.getAction() == 1 && this.bt) {
                this.bt = false;
                if (this.bitmap == null) {
                    return true;
                }
                this.bitmap.recycle();
                return true;
            }
            int[] iArr = new int[2];
            view.getLocationOnScreen(iArr);
            int rawX = (int) (motionEvent.getRawX() - ((float) iArr[0]));
            int rawY = (int) (motionEvent.getRawY() - ((float) iArr[1]));
            float rotation = view.getRotation();
            Matrix matrix = new Matrix();
            matrix.postRotate(-rotation);
            float[] fArr = new float[]{(float) rawX, (float) rawY};
            matrix.mapPoints(fArr);
            rawY = (int) fArr[0];
            int i = (int) fArr[1];
            if (motionEvent.getAction() == 0) {
                this.bt = false;
                view.setDrawingCacheEnabled(true);
                this.bitmap = Bitmap.createBitmap(view.getDrawingCache());
                rawY = (int) (((float) rawY) * (((float) this.bitmap.getWidth()) / (((float) this.bitmap.getWidth()) * view.getScaleX())));
                i = (int) (((float) i) * (((float) this.bitmap.getWidth()) / (((float) this.bitmap.getHeight()) * view.getScaleX())));
                view.setDrawingCacheEnabled(false);
            }
            if (rawY >= 0 && i >= 0 && rawY <= this.bitmap.getWidth()) {
                if (i <= this.bitmap.getHeight()) {
                    if (this.bitmap.getPixel(rawY, i) != 0) {
                        z = false;
                    }
                    if (motionEvent.getAction() != 0) {
                        return z;
                    }
                    this.bt = z;
                    return z;
                }
            }
            return false;
        } catch (Exception unused) {
            return false;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        this.mTVScaleGestureDetector.onTVTouchEvent(view, motionEvent);
        RelativeLayout relativeLayout = (RelativeLayout) view.getParent();
        GestureDetector gestureDetector = this.gd;
        if (gestureDetector != null) {
            gestureDetector.onTouchEvent(motionEvent);
        }
        if (this.isTranslateEnabled) {
            int action = motionEvent.getAction();
            int actionMasked = motionEvent.getActionMasked() & action;
            int i = 0;
            if (actionMasked != 6) {
                TouchCallbackListener touchCallbackListener;
                float rotation;
                switch (actionMasked) {
                    case 0:
                        if (relativeLayout != null) {
                            relativeLayout.requestDisallowInterceptTouchEvent(true);
                        }
                        touchCallbackListener = this.listener;
                        if (touchCallbackListener != null) {
                            touchCallbackListener.onTouchCallback(view, motionEvent);
                        }
                        view.bringToFront();
                        if (view instanceof IntelligentTVAutoFit) {
                            ((IntelligentTVAutoFit) view).applyBorderVisibility(true);
                        }
                        this.mPrevX = motionEvent.getX();
                        this.mPrevY = motionEvent.getY();
                        this.mActivePointerId = motionEvent.getPointerId(0);
                        break;
                    case 1:
                        this.mActivePointerId = -1;
                        touchCallbackListener = this.listener;
                        if (touchCallbackListener != null) {
                            touchCallbackListener.onTouchUpCallback(view, motionEvent);
                        }
                        rotation = view.getRotation();
                        if (Math.abs(90.0f - Math.abs(rotation)) <= 5.0f) {
                            rotation = rotation > 0.0f ? 90.0f : -90.0f;
                        }
                        if (Math.abs(0.0f - Math.abs(rotation)) <= 5.0f) {
                            rotation = rotation > 0.0f ? 0.0f : -0.0f;
                        }
                        if (Math.abs(180.0f - Math.abs(rotation)) <= 5.0f) {
                            rotation = rotation > 0.0f ? 180.0f : -180.0f;
                        }
                        view.setRotation(rotation);
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Final Rotation : ");
                        stringBuilder.append(rotation);
                        Log.i("testing", stringBuilder.toString());
                        break;
                    case 2:
                        if (relativeLayout != null) {
                            relativeLayout.requestDisallowInterceptTouchEvent(true);
                        }
                        touchCallbackListener = this.listener;
                        if (touchCallbackListener != null) {
                            touchCallbackListener.onTouchMoveCallback(view, motionEvent);
                        }
                        int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                        if (findPointerIndex != -1) {
                            float x = motionEvent.getX(findPointerIndex);
                            rotation = motionEvent.getY(findPointerIndex);
                            if (!this.mTVScaleGestureDetector.isInProgress()) {
                                adjustTranslation(view, x - this.mPrevX, rotation - this.mPrevY);
                                break;
                            }
                        }
                        break;
                    case 3:
                        this.mActivePointerId = -1;
                        break;
                }
            } else {
                int i2 = (MotionEventCompat.ACTION_POINTER_INDEX_MASK & action) >> 8;
                if (motionEvent.getPointerId(i2) == this.mActivePointerId) {
                    if (i2 == 0) {
                        i = 1;
                    }
                    this.mPrevX = motionEvent.getX(i);
                    this.mPrevY = motionEvent.getY(i);
                    this.mActivePointerId = motionEvent.getPointerId(i);
                }
            }
        }
        return true;
    }
}
