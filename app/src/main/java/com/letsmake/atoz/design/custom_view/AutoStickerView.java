package com.letsmake.atoz.design.custom_view;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import cz.msebera.android.httpclient.HttpStatus;
import com.letsmake.atoz.design.R;
import com.letsmake.atoz.design.app_utils.ViewElementInfo;
import com.letsmake.atoz.design.imageloader.Glide_Image_Utils;
import com.letsmake.atoz.design.listener.Multi_Tap_Listener;
import com.letsmake.atoz.design.listener.Multi_Tap_Listener.Touch_Callback_Listener;

public class AutoStickerView extends RelativeLayout implements Touch_Callback_Listener {

    private Context context;
    private ImageView border_iv, delete_iv, flip_iv;
    private Bitmap btmp = null;


    private Uri resUri = null;
    private ImageView rotate_iv;
    private float rotation;
    private int scaleRotateProg = 0;
    private ImageView scale_iv;
    public double scale_orgHeight = -1.0d;
    public double scale_orgWidth = -1.0d;
    public float scale_orgX = -1.0f;
    public float scale_orgY = -1.0f;
    int screenHeight = HttpStatus.SC_MULTIPLE_CHOICES;
    int screenWidth = HttpStatus.SC_MULTIPLE_CHOICES;
    public String stkr_path = "";
    double tAngle = 0.0d;
    public float this_orgX = -1.0f;
    public float this_orgY = -1.0f;
    public int topMargin = 0;
    double vAngle = 0.0d;
    public int wi;
    float widthMain = 0.0f;
    private int xRotateProg = 0;
    private int yRotateProg = 0;
    private float yRotation;
    private int zRotateProg = 0;
    Animation scale, zoomInScale, zoomOutScale;

    public ImageView main_iv;
    public int margin5dp = 2;
    int margt, margl;

    public static final String TAG = "AutoStickerView";
    double angle = 0.0d;
    int baseh, basew, basex, basey;

    boolean isFisrtAnimation = false;
    private boolean isFromAddText = false;
    public boolean isMultiTouchEnabled = true;
    float cX = 0.0f, cY = 0.0f, heightMain = 0.0f;
    public double centerX, centerY;
    private String colorType = "colored";
    double dAngle = 0.0d;
    private String drawableId;
    public int extraMargin = 35;
    private int f26s, field_one = 0;
    private String field_four = "", field_three = "";
    public String field_two = "0,0";
    public int he;
    private int hueProg = 1, imgAlpha = 255, imgColor = 0;
    private boolean isColorFilterEnable = false, isBorderVisible = false;
    public int leftMargin = 0;
    public TouchEventListener listener = null;


    public AutoStickerView(Context context2) {
        super(context2);
        initialization(context2);
    }

    public AutoStickerView(Context context2, AttributeSet attributeSet) {
        super(context2, attributeSet);
        initialization(context2);
    }

    public AutoStickerView(Context context2, AttributeSet attributeSet, int i) {
        super(context2, attributeSet, i);
        initialization(context2);
    }

    public AutoStickerView setOnTouchCallbackListener(TouchEventListener touchEventListener) {
        this.listener = touchEventListener;
        return this;
    }

    public void initialization(Context context2) {
        this.context = context2;
        this.main_iv = new ImageView(this.context);
        this.scale_iv = new ImageView(this.context);
        this.border_iv = new ImageView(this.context);
        this.flip_iv = new ImageView(this.context);
        this.rotate_iv = new ImageView(this.context);
        this.delete_iv = new ImageView(this.context);
        this.f26s = (int) convertdpToPx(this.context, 25.0f);
        this.extraMargin = (int) convertdpToPx(this.context, 25.0f);
        this.margin5dp = (int) convertdpToPx(this.context, 2.5f);
        this.wi = convertdpToPx(this.context, 200);
        this.he = convertdpToPx(this.context, 200);
        this.scale_iv.setImageResource(R.drawable.sticker_scale);
        this.border_iv.setImageResource(R.drawable.sticker_border_gray);
        this.flip_iv.setImageResource(R.drawable.sticker_flip);
        this.rotate_iv.setImageResource(R.drawable.sticker_rotate);
        this.delete_iv.setImageResource(R.drawable.sticker_delete1);
        LayoutParams layoutParams = new LayoutParams(this.wi, this.he);
        LayoutParams layoutParams2 = new LayoutParams(-1, -1);
        layoutParams2.setMargins(5, 5, 5, 5);
        if (VERSION.SDK_INT >= 17) {
            layoutParams2.addRule(17);
        } else {
            layoutParams2.addRule(1);
        }
        int i = this.f26s;
        LayoutParams layoutParams3 = new LayoutParams(i, i);
        layoutParams3.addRule(12);
        layoutParams3.addRule(11);
        layoutParams3.setMargins(5, 5, 5, 5);
        int i2 = this.f26s;
        LayoutParams layoutParams4 = new LayoutParams(i2, i2);
        layoutParams4.addRule(10);
        layoutParams4.addRule(11);
        layoutParams4.setMargins(5, 5, 5, 5);
        int i3 = this.f26s;
        LayoutParams layoutParams5 = new LayoutParams(i3, i3);
        layoutParams5.addRule(12);
        layoutParams5.addRule(9);
        layoutParams5.setMargins(5, 5, 5, 5);
        int i4 = this.f26s;
        LayoutParams layoutParams6 = new LayoutParams(i4, i4);
        layoutParams6.addRule(10);
        layoutParams6.addRule(9);
        layoutParams6.setMargins(5, 5, 5, 5);
        LayoutParams layoutParams7 = new LayoutParams(-1, -1);
        setLayoutParams(layoutParams);
        setBackgroundResource(R.drawable.sticker_gray1);
        addView(this.border_iv);
        this.border_iv.setLayoutParams(layoutParams7);
        this.border_iv.setScaleType(ScaleType.FIT_XY);
        this.border_iv.setTag("border_iv");
        addView(this.main_iv);
        this.main_iv.setLayoutParams(layoutParams2);
        addView(this.flip_iv);
        this.flip_iv.setLayoutParams(layoutParams4);
        this.flip_iv.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ImageView imageView = AutoStickerView.this.main_iv;
                float f = -180.0f;
                if (AutoStickerView.this.main_iv.getRotationY() == -180.0f) {
                    f = 0.0f;
                }
                imageView.setRotationY(f);
                AutoStickerView.this.main_iv.invalidate();
                AutoStickerView.this.requestLayout();
            }
        });
        addView(this.rotate_iv);
        this.rotate_iv.setLayoutParams(layoutParams5);
        this.rotate_iv.setOnTouchListener(this.rTouchListener);
        addView(this.delete_iv);
        this.delete_iv.setLayoutParams(layoutParams6);
        this.delete_iv.setOnClickListener(new DeleteOnStickerClick());
        addView(this.scale_iv);
        this.scale_iv.setLayoutParams(layoutParams3);
        this.scale_iv.setOnTouchListener(this.mTouchListener1);
        this.scale_iv.setTag("scale_iv");
        this.rotation = getRotation();
        this.scale = AnimationUtils.loadAnimation(getContext(), R.anim.anim_sticker_scale);
        this.zoomOutScale = AnimationUtils.loadAnimation(getContext(), R.anim.anim_sticker_scale_zoom_out);
        this.zoomInScale = AnimationUtils.loadAnimation(getContext(), R.anim.anim_sticker_scale_zoom_in);
        this.isMultiTouchEnabled = applyDefaultTouchListener(true);
    }



    private OnTouchListener mStickerViewTouchListener = new OnTouchListener() {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            AutoStickerView autoStickerView = (AutoStickerView) view.getParent();
            switch (motionEvent.getAction()) {
                case 0:
                    AutoStickerView autoStickerView2 = AutoStickerView.this;
                    autoStickerView2.this_orgX = autoStickerView2.getX();
                    AutoStickerView autoStickerView3 = AutoStickerView.this;
                    autoStickerView3.this_orgY = autoStickerView3.getY();
                    AutoStickerView.this.scale_orgX = motionEvent.getRawX();
                    AutoStickerView.this.scale_orgY = motionEvent.getRawY();
                    AutoStickerView autoStickerView4 = AutoStickerView.this;
                    autoStickerView4.scale_orgWidth = (double) autoStickerView4.getLayoutParams().width;
                    AutoStickerView autoStickerView5 = AutoStickerView.this;
                    autoStickerView5.scale_orgHeight = (double) autoStickerView5.getLayoutParams().height;
                    AutoStickerView autoStickerView6 = AutoStickerView.this;
                    autoStickerView6.centerX = (double) (autoStickerView6.getX() + ((View) AutoStickerView.this.getParent()).getX() + (((float) AutoStickerView.this.getWidth()) / 2.0f));
                    int i = 0;
                    int identifier = AutoStickerView.this.getResources().getIdentifier("status_bar_height", "dimen", "android");
                    if (identifier > 0) {
                        i = AutoStickerView.this.getResources().getDimensionPixelSize(identifier);
                    }
                    AutoStickerView autoStickerView7 = AutoStickerView.this;
                    double y = (double) (autoStickerView7.getY() + ((View) AutoStickerView.this.getParent()).getY());
                    double d = (double) i;
                    Double.isNaN(y);
                    Double.isNaN(d);
                    double d2 = y + d;
                    double height = (double) (((float) AutoStickerView.this.getHeight()) / 2.0f);
                    Double.isNaN(height);
                    autoStickerView7.centerY = d2 + height;
                    break;
                case 1:
                    AutoStickerView autoStickerView8 = AutoStickerView.this;
                    autoStickerView8.wi = autoStickerView8.getLayoutParams().width;
                    AutoStickerView autoStickerView9 = AutoStickerView.this;
                    autoStickerView9.he = autoStickerView9.getLayoutParams().height;
                    break;
                case 2:
                    double atan2 = Math.atan2((double) (motionEvent.getRawY() - AutoStickerView.this.scale_orgY), (double) (motionEvent.getRawX() - AutoStickerView.this.scale_orgX));
                    double access$300 = (double) AutoStickerView.this.scale_orgY;
                    double access$700 = AutoStickerView.this.centerY;
                    Double.isNaN(access$300);
                    double d3 = access$300 - access$700;
                    double access$200 = (double) AutoStickerView.this.scale_orgX;
                    double access$600 = AutoStickerView.this.centerX;
                    Double.isNaN(access$200);
                    double abs = (Math.abs(atan2 - Math.atan2(d3, access$200 - access$600)) * 180.0d) / 3.141592653589793d;
                    String str = AutoStickerView.TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("angle_diff: ");
                    sb.append(abs);
                    Log.v(str, sb.toString());
                    AutoStickerView autoStickerView10 = AutoStickerView.this;
                    double access$1000 = autoStickerView10.getSTLength(autoStickerView10.centerX, AutoStickerView.this.centerY, (double) AutoStickerView.this.scale_orgX, (double) AutoStickerView.this.scale_orgY);
                    AutoStickerView autoStickerView11 = AutoStickerView.this;
                    double access$10002 = autoStickerView11.getSTLength(autoStickerView11.centerX, AutoStickerView.this.centerY, (double) motionEvent.getRawX(), (double) motionEvent.getRawY());
                    AutoStickerView autoStickerView12 = AutoStickerView.this;
                    int dpToPx = (int) autoStickerView12.convertdpToPx(autoStickerView12.getContext(), 30.0f);
                    if (access$10002 > access$1000 && (abs < 25.0d || Math.abs(abs - 180.0d) < 25.0d)) {
                        double round = (double) Math.round(Math.max((double) Math.abs(motionEvent.getRawX() - AutoStickerView.this.scale_orgX), (double) Math.abs(motionEvent.getRawY() - AutoStickerView.this.scale_orgY)));
                        LayoutParams layoutParams = (LayoutParams) AutoStickerView.this.getLayoutParams();
                        double d4 = (double) layoutParams.width;
                        Double.isNaN(d4);
                        Double.isNaN(round);
                        layoutParams.width = (int) (d4 + round);
                        LayoutParams layoutParams2 = (LayoutParams) AutoStickerView.this.getLayoutParams();
                        double d5 = (double) layoutParams2.height;
                        Double.isNaN(d5);
                        Double.isNaN(round);
                        layoutParams2.height = (int) (d5 + round);
                    } else if (access$10002 < access$1000 && (abs < 25.0d || Math.abs(abs - 180.0d) < 25.0d)) {
                        int i2 = dpToPx / 2;
                        if (AutoStickerView.this.getLayoutParams().width > i2 && AutoStickerView.this.getLayoutParams().height > i2) {
                            double round2 = (double) Math.round(Math.max((double) Math.abs(motionEvent.getRawX() - AutoStickerView.this.scale_orgX), (double) Math.abs(motionEvent.getRawY() - AutoStickerView.this.scale_orgY)));
                            LayoutParams layoutParams3 = (LayoutParams) AutoStickerView.this.getLayoutParams();
                            double d6 = (double) layoutParams3.width;
                            Double.isNaN(d6);
                            Double.isNaN(round2);
                            layoutParams3.width = (int) (d6 - round2);
                            LayoutParams layoutParams4 = (LayoutParams) AutoStickerView.this.getLayoutParams();
                            double d7 = (double) layoutParams4.height;
                            Double.isNaN(d7);
                            Double.isNaN(round2);
                            layoutParams4.height = (int) (d7 - round2);
                        }
                    }
                    AutoStickerView.this.scale_orgX = motionEvent.getRawX();
                    AutoStickerView.this.scale_orgY = motionEvent.getRawY();
                    AutoStickerView.this.postInvalidate();
                    AutoStickerView.this.requestLayout();
                    break;
            }
            return true;
        }
    };


    class DeleteOnStickerClick implements OnClickListener {
        DeleteOnStickerClick() {
        }

        public void onClick(View view) {
            final ViewGroup viewGroup = (ViewGroup) AutoStickerView.this.getParent();
            AutoStickerView.this.zoomInScale.setAnimationListener(new AnimationListener() {
                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    viewGroup.removeView(AutoStickerView.this);
                }
            });
            AutoStickerView.this.main_iv.startAnimation(AutoStickerView.this.zoomInScale);
            AutoStickerView autoStickerView = AutoStickerView.this;
            autoStickerView.isFisrtAnimation = true;
            autoStickerView.applyBorderVisibility(false);
            if (AutoStickerView.this.listener != null) {
                AutoStickerView.this.listener.onDelete();
            }
        }
    }

    class RingStickerViewProgressClick implements OnDismissListener {
        public void onDismiss(DialogInterface dialogInterface) {
        }

        RingStickerViewProgressClick() {
        }
    }

    public interface TouchEventListener {
        void onDelete();

        void onEdit(View view, Uri uri);

        void onMidX(View view);

        void onMidXY(View view);

        void onMidY(View view);

        void onRotateDown(View view, MotionEvent motionEvent);

        void onRotateMove(View view, MotionEvent motionEvent);

        void onRotateUp(View view, MotionEvent motionEvent);

        void onScaleDown(View view, MotionEvent motionEvent);

        void onScaleMove(View view, MotionEvent motionEvent);

        void onScaleUp(View view, MotionEvent motionEvent);

        void onTouchDown(View view, MotionEvent motionEvent);

        void onTouchMove(View view, MotionEvent motionEvent);

        void onTouchUp(View view, MotionEvent motionEvent);

        void onXY(View view);
    }

    public boolean isFromAddText() {
        return this.isFromAddText;
    }

    public void setFromAddText(boolean z) {
        this.isFromAddText = z;
    }

    public boolean isFisrtAnimation() {
        return this.isFisrtAnimation;
    }

    public void setFisrtAnimation(boolean z) {
        this.isFisrtAnimation = z;
    }

    public boolean applyDefaultTouchListener(boolean z) {
        if (z) {
            setOnTouchListener(new Multi_Tap_Listener().enableRotation(true).setOnTouchCallbackListener(this));
            return true;
        }
        setOnTouchListener(null);
        return false;
    }


    public boolean checkBorderVisbilty() {
        return this.isBorderVisible;
    }

    public void getopacity_Sticker(int i) {
        try {
            this.main_iv.setAlpha(i);
            this.imgAlpha = i;
        } catch (Exception unused) {
        }
    }

    public int get_sticker_HueProg() {
        return this.hueProg;
    }

    public void set_Sticker_HueProg(int i) {
        this.hueProg = i;
        int i2 = this.hueProg;
        if (i2 == 0) {
            this.main_iv.setColorFilter(-1);
        } else if (i2 == 100) {
            this.main_iv.setColorFilter(-16777216);
        } else {
            this.main_iv.setColorFilter(ApplyColorFilter.applyHue((float) i));
        }
    }

    public String getSTColorType() {
        return this.colorType;
    }

    public int getSTAlphaProg() {
        return this.imgAlpha;
    }

    public void setSTAlphaProg(int i) {
        getopacity_Sticker(i);
    }

    public int getSTColor() {
        return this.imgColor;
    }

    public void setSTColor(int i) {
        try {
            this.main_iv.setColorFilter(i);
            this.imgColor = i;
        } catch (Exception unused) {
        }
    }

    public void setSTBgDrawable(String str) {
        Glide.with(this.context).load(Integer.valueOf(getResources().getIdentifier(str, "drawable", this.context.getPackageName()))).apply(new RequestOptions().dontAnimate().placeholder((int) R.drawable.no_image).error((int) R.drawable.no_image)).into(this.main_iv);
        this.drawableId = str;
        if (this.isFisrtAnimation || this.isFromAddText) {
            this.main_iv.startAnimation(this.zoomOutScale);
        }
        this.isFisrtAnimation = true;
    }

    public void set_ST_StrPath(String str) {
        try {
            this.btmp = Glide_Image_Utils.getResampleGlideImageBitmap(Uri.parse(str), this.context, this.screenWidth > this.screenHeight ? this.screenWidth : this.screenHeight);
            this.main_iv.setImageBitmap(this.btmp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.stkr_path = str;
        if (this.isFisrtAnimation || this.isFromAddText) {
            this.main_iv.startAnimation(this.zoomOutScale);
        }
        this.isFisrtAnimation = true;
    }

    public Uri getST_MainImageUri() {
        return this.resUri;
    }

    public void setST_MainImageUri(Uri uri) {
        this.resUri = uri;
        this.main_iv.setImageURI(this.resUri);
    }


    @Override
    public void onTouchCallback(View view, MotionEvent motionEvent) {
        TouchEventListener touchEventListener = this.listener;
        if (touchEventListener != null) {
            touchEventListener.onTouchDown(view, motionEvent);
        }
    }

    @Override
    public void onTouchUpCallback(View view, MotionEvent motionEvent) {
        TouchEventListener touchEventListener = this.listener;
        if (touchEventListener != null) {
            touchEventListener.onTouchUp(view, motionEvent);
        }
    }

    @Override
    public void onTouchMoveCallback(View view, MotionEvent motionEvent) {
        TouchEventListener touchEventListener = this.listener;
        if (touchEventListener != null) {
            touchEventListener.onTouchMove(view, motionEvent);
        }
    }

    @Override
    public void onMidX(View view) {
        TouchEventListener touchEventListener = this.listener;
        if (touchEventListener != null) {
            touchEventListener.onMidX(view);
        }
    }

    @Override
    public void onMidY(View view) {
        TouchEventListener touchEventListener = this.listener;
        if (touchEventListener != null) {
            touchEventListener.onMidY(view);
        }
    }

    @Override
    public void onMidXY(View view) {
        TouchEventListener touchEventListener = this.listener;
        if (touchEventListener != null) {
            touchEventListener.onMidXY(view);
        }
    }

    @Override
    public void onXY(View view) {
        TouchEventListener touchEventListener = this.listener;
        if (touchEventListener != null) {
            touchEventListener.onXY(view);
        }
    }
    public Bitmap getST_MainImageBitmap() {
        return this.btmp;
    }

    public void set_ST_MainImageBitmap(Bitmap bitmap) {
        this.main_iv.setImageBitmap(bitmap);
    }

    public void optimizeSTView(float f, float f2) {
        setX(getX() * f);
        setY(getY() * f2);
        getLayoutParams().width = (int) (((float) this.wi) * f);
        getLayoutParams().height = (int) (((float) this.he) * f2);
    }

    public void optimizeSTScreen(float f, float f2) {
        this.screenHeight = (int) f2;
        this.screenWidth = (int) f;

        //  Log.e("#optimizeView##Sticker_List","width : "+screenWidth +"  height : "+screenHeight );
    }

    public void set_STViewWH(float f, float f2) {
        this.widthMain = f;
        this.heightMain = f2;

        //  Log.e("#ViewWH##Sticker_List","width : "+screenWidth +"  height : "+screenHeight );

    }

    public float get_ST_MainWidth() {
        return this.widthMain;
    }

    public float get_ST_MainHeight() {
        return this.heightMain;
    }

    public void incrX() {
        setX(getX() + 2.0f);
    }

    public void decX() {
        setX(getX() - 2.0f);
    }

    public void incrY() {
        setY(getY() + 2.0f);
    }

    public void decY() {
        setY(getY() - 2.0f);
    }

    public ViewElementInfo get_ST_ComponentInfo() {
        Bitmap bitmap = this.btmp;
        if (bitmap != null) {
            this.stkr_path = save_STView_BitmapObject1(bitmap);
        }
        ViewElementInfo viewElementInfo = new ViewElementInfo();
        viewElementInfo.setPOS_X(getX());
        viewElementInfo.setPOS_Y(getY());
        viewElementInfo.setWIDTH(this.wi);
        viewElementInfo.setHEIGHT(this.he);
        viewElementInfo.setRES_ID(this.drawableId);
        viewElementInfo.setSTC_COLOR(this.imgColor);
        viewElementInfo.setRES_URI(this.resUri);
        viewElementInfo.setSTC_OPACITY(this.imgAlpha);
        viewElementInfo.setCOLORTYPE(this.colorType);
        viewElementInfo.setBITMAP(this.btmp);
        viewElementInfo.setROTATION(getRotation());
        viewElementInfo.setY_ROTATION(this.main_iv.getRotationY());
        viewElementInfo.setXRotateProg(this.xRotateProg);
        viewElementInfo.setYRotateProg(this.yRotateProg);
        viewElementInfo.setZRotateProg(this.zRotateProg);
        viewElementInfo.setScaleProg(this.scaleRotateProg);
        viewElementInfo.setSTKR_PATH(this.stkr_path);
        viewElementInfo.setSTC_HUE(this.hueProg);
        viewElementInfo.setFIELD_ONE(this.field_one);
        viewElementInfo.setFIELD_TWO(this.field_two);
        viewElementInfo.setFIELD_THREE(this.field_three);
        viewElementInfo.setFIELD_FOUR(this.field_four);
        return viewElementInfo;
    }

    public void set_STView_ComponentInfo(ViewElementInfo viewElementInfo) {
        this.wi = viewElementInfo.getWIDTH();
        this.he = viewElementInfo.getHEIGHT();
        this.drawableId = viewElementInfo.getRES_ID();
        this.resUri = viewElementInfo.getRES_URI();
        this.btmp = viewElementInfo.getBITMAP();
        this.rotation = viewElementInfo.getROTATION();
        this.imgColor = viewElementInfo.getSTC_COLOR();
        this.yRotation = viewElementInfo.getY_ROTATION();
        this.imgAlpha = viewElementInfo.getSTC_OPACITY();
        this.stkr_path = viewElementInfo.getSTKR_PATH();
        this.colorType = viewElementInfo.getCOLORTYPE();
        this.hueProg = viewElementInfo.getSTC_HUE();
        this.field_two = viewElementInfo.getFIELD_TWO();
        if (!this.stkr_path.equals("")) {
            set_ST_StrPath(this.stkr_path);
        } else if (this.drawableId.equals("")) {
            this.main_iv.setImageBitmap(this.btmp);
        } else {
            setSTBgDrawable(this.drawableId);
        }
        if (this.colorType.equals("white")) {
            setSTColor(this.imgColor);
        } else {
            set_Sticker_HueProg(this.hueProg);
        }
        setRotation(this.rotation);
        getopacity_Sticker(this.imgAlpha);
        if (this.field_two.equals("")) {
            getLayoutParams().width = this.wi;
            getLayoutParams().height = this.he;
            setX(viewElementInfo.getPOS_X());
            setY(viewElementInfo.getPOS_Y());
        } else {
            try {
                String[] split = this.field_two.split(",");
                int parseInt = Integer.parseInt(split[0]);
                int parseInt2 = Integer.parseInt(split[1]);
                ((LayoutParams) getLayoutParams()).leftMargin = parseInt;
                ((LayoutParams) getLayoutParams()).topMargin = parseInt2;
                getLayoutParams().width = this.wi;
                getLayoutParams().height = this.he;
                setX(viewElementInfo.getPOS_X() + ((float) (parseInt * -1)));
                setY(viewElementInfo.getPOS_Y() + ((float) (parseInt2 * -1)));
            } catch (ArrayIndexOutOfBoundsException e) {
                getLayoutParams().width = this.wi;
                getLayoutParams().height = this.he;
                setX(viewElementInfo.getPOS_X());
                setY(viewElementInfo.getPOS_Y());
                e.printStackTrace();
            }
        }
        if (viewElementInfo.getTYPE() == "SHAPE") {
            this.flip_iv.setVisibility(8);
        }
        if (viewElementInfo.getTYPE() == "STICKER") {
            this.flip_iv.setVisibility(0);
        }
        this.main_iv.setRotationY(this.yRotation);
    }


    public void applyBorderVisibility(boolean z) {
        this.isBorderVisible = z;
        if (!z) {
            this.border_iv.setVisibility(8);
            this.scale_iv.setVisibility(8);
            this.flip_iv.setVisibility(8);
            this.rotate_iv.setVisibility(8);
            this.delete_iv.setVisibility(8);
            setBackgroundResource(0);
            if (this.isColorFilterEnable) {
                this.main_iv.setColorFilter(Color.parseColor("#303828"));
            }
        } else if (this.border_iv.getVisibility() != 0) {
            this.border_iv.setVisibility(0);
            this.scale_iv.setVisibility(0);
            this.flip_iv.setVisibility(0);
            this.rotate_iv.setVisibility(0);
            this.delete_iv.setVisibility(0);
            setBackgroundResource(R.drawable.sticker_gray1);
            if (this.isFisrtAnimation || this.isFromAddText) {
                this.main_iv.startAnimation(this.scale);
            }
            this.isFisrtAnimation = true;
        }
    }

    private void save_STView_Bitmap(final Bitmap bitmap) {
        final ProgressDialog show = ProgressDialog.show(this.context, "", "", true);
        show.setCancelable(false);
        new Thread(new Runnable() {
            public void run() {
                try {
                    AutoStickerView.this.stkr_path = AutoStickerView.this.save_STView_BitmapObject1(bitmap);
                } catch (Exception e) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Exception ");
                    sb.append(e.getMessage());
                    Log.i("testing", sb.toString());
                    e.printStackTrace();
                } catch (Throwable unused) {
                }
                show.dismiss();
            }
        }).start();
        show.setOnDismissListener(new RingStickerViewProgressClick());
    }

    public String save_STView_BitmapObject1(Bitmap bitmap) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), ".Poster Maker Stickers/category1");
        file.mkdirs();
        StringBuilder sb = new StringBuilder();
        sb.append("raw1-");
        sb.append(System.currentTimeMillis());
        sb.append(".png");
        File file2 = new File(file, sb.toString());
        String absolutePath = file2.getAbsolutePath();
        if (file2.exists()) {
            file2.delete();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            bitmap.compress(CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();
            return absolutePath;
        } catch (Exception e) {
            e.printStackTrace();
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Exception");
            sb2.append(e.getMessage());
            Log.i("testing", sb2.toString());
            return "";
        }
    }

    public int convertdpToPx(Context context2, int i) {
        float f = (float) i;
        context2.getResources();
        return (int) (Resources.getSystem().getDisplayMetrics().density * f);
    }

    public float convertdpToPx(Context context2, float f) {
        context2.getResources();
        return (float) Math.round(f * Resources.getSystem().getDisplayMetrics().density);
    }

    /* access modifiers changed from: private */
    public double getSTLength(double d, double d2, double d3, double d4) {
        return Math.sqrt(Math.pow(d4 - d2, 2.0d) + Math.pow(d3 - d, 2.0d));
    }

    public void enable_STView_ColorFilter(boolean z) {
        this.isColorFilterEnable = z;
    }

    private OnTouchListener mTouchListener1 = new OnTouchListener() {
        @SuppressLint({"NewApi"})
        public boolean onTouch(View view, MotionEvent motionEvent) {
            AutoStickerView autoStickerView = (AutoStickerView) view.getParent();
            int rawX = (int) motionEvent.getRawX();
            int rawY = (int) motionEvent.getRawY();
            LayoutParams layoutParams = (LayoutParams) AutoStickerView.this.getLayoutParams();
            switch (motionEvent.getAction()) {
                case 0:
                    if (autoStickerView != null) {
                        autoStickerView.requestDisallowInterceptTouchEvent(true);
                    }
                    if (AutoStickerView.this.listener != null) {
                        AutoStickerView.this.listener.onScaleDown(AutoStickerView.this, motionEvent);
                    }
                    AutoStickerView.this.invalidate();
                    AutoStickerView autoStickerView2 = AutoStickerView.this;
                    autoStickerView2.basex = rawX;
                    autoStickerView2.basey = rawY;
                    autoStickerView2.basew = autoStickerView2.getWidth();
                    AutoStickerView autoStickerView3 = AutoStickerView.this;
                    autoStickerView3.baseh = autoStickerView3.getHeight();
                    AutoStickerView.this.getLocationOnScreen(new int[2]);
                    AutoStickerView.this.margl = layoutParams.leftMargin;
                    AutoStickerView.this.margt = layoutParams.topMargin;
                    break;
                case 1:
                    AutoStickerView autoStickerView4 = AutoStickerView.this;
                    autoStickerView4.wi = autoStickerView4.getLayoutParams().width;
                    AutoStickerView autoStickerView5 = AutoStickerView.this;
                    autoStickerView5.he = autoStickerView5.getLayoutParams().height;
                    AutoStickerView autoStickerView6 = AutoStickerView.this;
                    autoStickerView6.leftMargin = ((LayoutParams) autoStickerView6.getLayoutParams()).leftMargin;
                    AutoStickerView autoStickerView7 = AutoStickerView.this;
                    autoStickerView7.topMargin = ((LayoutParams) autoStickerView7.getLayoutParams()).topMargin;
                    StringBuilder sb = new StringBuilder();
                    sb.append(String.valueOf(AutoStickerView.this.leftMargin));
                    sb.append(",");
                    sb.append(String.valueOf(AutoStickerView.this.topMargin));
                    autoStickerView.field_two = sb.toString();
                    if (AutoStickerView.this.listener != null) {
                        AutoStickerView.this.listener.onScaleUp(AutoStickerView.this, motionEvent);
                        break;
                    }
                    break;
                case 2:
                    if (autoStickerView != null) {
                        autoStickerView.requestDisallowInterceptTouchEvent(true);
                    }
                    if (AutoStickerView.this.listener != null) {
                        AutoStickerView.this.listener.onScaleMove(AutoStickerView.this, motionEvent);
                    }
                    float degrees = (float) Math.toDegrees(Math.atan2((double) (rawY - AutoStickerView.this.basey), (double) (rawX - AutoStickerView.this.basex)));
                    if (degrees < 0.0f) {
                        degrees += 360.0f;
                    }
                    int i = rawX - AutoStickerView.this.basex;
                    int i2 = rawY - AutoStickerView.this.basey;
                    int i3 = i2 * i2;
                    int sqrt = (int) (Math.sqrt((double) ((i * i) + i3)) * Math.cos(Math.toRadians((double) (degrees - AutoStickerView.this.getRotation()))));
                    int sqrt2 = (int) (Math.sqrt((double) ((sqrt * sqrt) + i3)) * Math.sin(Math.toRadians((double) (degrees - AutoStickerView.this.getRotation()))));
                    int i4 = (sqrt * 2) + AutoStickerView.this.basew;
                    int i5 = (sqrt2 * 2) + AutoStickerView.this.baseh;
                    if (i4 > (AutoStickerView.this.extraMargin * 2) + AutoStickerView.this.margin5dp) {
                        layoutParams.width = i4;
                        layoutParams.leftMargin = AutoStickerView.this.margl - sqrt;
                    }
                    if (i5 > (AutoStickerView.this.extraMargin * 2) + AutoStickerView.this.margin5dp) {
                        layoutParams.height = i5;
                        layoutParams.topMargin = AutoStickerView.this.margt - sqrt2;
                    }
                    AutoStickerView.this.setLayoutParams(layoutParams);
                    AutoStickerView.this.performLongClick();
                    break;
            }
            return true;
        }
    };


    private OnTouchListener rTouchListener = new OnTouchListener() {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            AutoStickerView autoStickerView = (AutoStickerView) view.getParent();
            switch (motionEvent.getAction()) {
                case 0:
                    if (autoStickerView != null) {
                        autoStickerView.requestDisallowInterceptTouchEvent(true);
                    }
                    if (AutoStickerView.this.listener != null) {
                        AutoStickerView.this.listener.onRotateDown(AutoStickerView.this, motionEvent);
                    }
                    Rect rect = new Rect();
                    ((View) view.getParent()).getGlobalVisibleRect(rect);
                    AutoStickerView.this.cX = rect.exactCenterX();
                    AutoStickerView.this.cY = rect.exactCenterY();
                    AutoStickerView.this.vAngle = (double) ((View) view.getParent()).getRotation();
                    AutoStickerView autoStickerView2 = AutoStickerView.this;
                    autoStickerView2.tAngle = (Math.atan2((double) (autoStickerView2.cY - motionEvent.getRawY()), (double) (AutoStickerView.this.cX - motionEvent.getRawX())) * 180.0d) / 3.141592653589793d;
                    AutoStickerView autoStickerView3 = AutoStickerView.this;
                    autoStickerView3.dAngle = autoStickerView3.vAngle - AutoStickerView.this.tAngle;
                    break;
                case 1:
                    if (AutoStickerView.this.listener != null) {
                        AutoStickerView.this.listener.onRotateUp(AutoStickerView.this, motionEvent);
                        break;
                    }
                    break;
                case 2:
                    if (autoStickerView != null) {
                        autoStickerView.requestDisallowInterceptTouchEvent(true);
                    }
                    if (AutoStickerView.this.listener != null) {
                        AutoStickerView.this.listener.onRotateMove(AutoStickerView.this, motionEvent);
                    }
                    AutoStickerView autoStickerView4 = AutoStickerView.this;
                    autoStickerView4.angle = (Math.atan2((double) (autoStickerView4.cY - motionEvent.getRawY()), (double) (AutoStickerView.this.cX - motionEvent.getRawX())) * 180.0d) / 3.141592653589793d;
                    float f = (float) (AutoStickerView.this.angle + AutoStickerView.this.dAngle);
                    ((View) view.getParent()).setRotation(f);
                    ((View) view.getParent()).invalidate();
                    ((View) view.getParent()).requestLayout();
                    if (Math.abs(90.0f - Math.abs(f)) <= 5.0f) {
                        f = f > 0.0f ? 90.0f : -90.0f;
                    }
                    if (Math.abs(0.0f - Math.abs(f)) <= 5.0f) {
                        f = f > 0.0f ? 0.0f : -0.0f;
                    }
                    if (Math.abs(180.0f - Math.abs(f)) <= 5.0f) {
                        f = f > 0.0f ? 180.0f : -180.0f;
                    }
                    ((View) view.getParent()).setRotation(f);
                    break;
            }
            return true;
        }
    };

}