package com.letsmake.atoz.design.listener;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;

import androidx.core.view.MotionEventCompat;

import com.letsmake.atoz.design.custom_view.AutoStickerView;
import com.letsmake.atoz.design.listener.Gesture_Scale_Detector.On_Scale_Gesture_Listener;

public class Multi_Tap_Listener implements OnTouchListener {

    private Touch_Callback_Listener listener = null;

    private Bitmap bitmap;
    private GestureDetector gd = null;
    private boolean bt = false, isTranslateEnabled = true, isRotateEnabled = true, isRotationEnabled = false;
    private int mActivePointerId = -1;
    private Gesture_Scale_Detector mGestureScaleDetector = new Gesture_Scale_Detector(new Scale_Gesture_Listener());
    private float mPrevX, mPrevY, minimumScale = 0.5f, maximumScale = 8.0f;

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        this.mGestureScaleDetector.onTouchEvent(view, motionEvent);
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
                Touch_Callback_Listener touchCallbackListener;
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
                        if (view instanceof AutoStickerView) {
                            ((AutoStickerView) view).applyBorderVisibility(true);
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
                            if (!this.mGestureScaleDetector.isProgressConnected()) {
                                adjustImageTranslation(view, x - this.mPrevX, rotation - this.mPrevY);
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

    public interface Touch_Callback_Listener {
        void onMidX(View view);

        void onMidXY(View view);

        void onMidY(View view);

        void onTouchCallback(View view, MotionEvent motionEvent);

        void onTouchMoveCallback(View view, MotionEvent motionEvent);

        void onTouchUpCallback(View view, MotionEvent motionEvent);

        void onXY(View view);
    }

    private class Scale_Gesture_Listener extends On_Scale_Gesture_Listener {

        private float mPivotY, mPivotX;
        private Vector_2D mPrevSpanVector;

        private Scale_Gesture_Listener() {
            this.mPrevSpanVector = new Vector_2D();
        }

        @Override
        public boolean onScaleBegin(View view, Gesture_Scale_Detector gestureScaleDetector) {
            mPivotX = gestureScaleDetector.get_Focus_X();
            mPivotY = gestureScaleDetector.get_Focus_Y();
            mPrevSpanVector.set(gestureScaleDetector.getCurrentSpanVector());
            return true;
        }

        @Override
        public boolean onScale(View view, Gesture_Scale_Detector gestureScaleDetector) {
            TransformDataInfo transformDataInfo = new TransformDataInfo();
            float f = 0.0f;
            transformDataInfo.deltaAngle = Multi_Tap_Listener.this.isRotateEnabled ? Vector_2D.getVectorAngle(this.mPrevSpanVector, gestureScaleDetector.getCurrentSpanVector()) : 0.0f;
            transformDataInfo.deltaX = Multi_Tap_Listener.this.isTranslateEnabled ? gestureScaleDetector.get_Focus_X() - this.mPivotX : 0.0f;
            if (Multi_Tap_Listener.this.isTranslateEnabled) {
                f = gestureScaleDetector.get_Focus_Y() - this.mPivotY;
            }
            transformDataInfo.deltaY = f;
            transformDataInfo.pivotX = this.mPivotX;
            transformDataInfo.pivotY = this.mPivotY;
            transformDataInfo.minimumScale = Multi_Tap_Listener.this.minimumScale;
            transformDataInfo.maximumScale = Multi_Tap_Listener.this.maximumScale;
            Multi_Tap_Listener.this.moveTransformableData(view, transformDataInfo);
            return false;
        }
    }

    private class TransformDataInfo {
        float pivotX, pivotY, deltaScale, deltaAngle, maximumScale, deltaX, deltaY, minimumScale;

        private TransformDataInfo() {
        }
    }


    private void adjustImageTranslation(View view, float f, float f2) {
        float[] fArr = new float[2];
        fArr[0] = f;
        int i = 1;
        fArr[1] = f2;
        view.getMatrix().mapVectors(fArr);
        f2 = view.getTranslationY() + fArr[1];
        view.setTranslationX(view.getTranslationX() + fArr[0]);
        view.setTranslationY(f2);
        AutoStickerView autoStickerView = (AutoStickerView) view;
        float mainWidth = autoStickerView.get_ST_MainWidth();
        f2 = autoStickerView.get_ST_MainHeight();
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
        f2 /= 2.0f;
        if (mainWidth <= f2 - f3 || mainWidth >= f3 + f2) {
            f2 = 0.0f;
        } else {
            view.setY(f2 - height);
            f2 = Float.MIN_VALUE;
        }
        Touch_Callback_Listener touchCallbackListener;
        if (i != 0 && f2 != 0.0f) {
            touchCallbackListener = this.listener;
            if (touchCallbackListener != null) {
                touchCallbackListener.onMidXY(view);
            }
        } else if (i != 0) {
            touchCallbackListener = this.listener;
            if (touchCallbackListener != null) {
                touchCallbackListener.onMidX(view);
            }
        } else if (f2 != 0.0f) {
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
        f = view.getRotation();
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

    private static float adjustImageAngle(float f) {
        return f > 180.0f ? f - 360.0f : f < -180.0f ? f + 360.0f : f;
    }

    private static void compute_Image_Render_Offset(View view, float f, float f2) {
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

    public Multi_Tap_Listener set_Image_GestureListener(GestureDetector gestureDetector) {
        this.gd = gestureDetector;
        return this;
    }

    public Multi_Tap_Listener setOnTouchCallbackListener(Touch_Callback_Listener touchCallbackListener) {
        this.listener = touchCallbackListener;
        return this;
    }

    public Multi_Tap_Listener enableRotation(boolean z) {
        this.isRotationEnabled = z;
        return this;
    }

    public Multi_Tap_Listener set_Min_Scale(float f) {
        this.minimumScale = f;
        return this;
    }


    public void moveTransformableData(View view, TransformDataInfo transformDataInfo) {
        if (this.isRotationEnabled) {
            view.setRotation(adjustImageAngle(view.getRotation() + transformDataInfo.deltaAngle));
        }
    }


    public boolean handleMotionEventTransparency(View view, MotionEvent motionEvent) {
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

}
