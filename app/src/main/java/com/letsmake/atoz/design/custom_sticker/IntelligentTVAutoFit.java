package com.letsmake.atoz.design.custom_sticker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build.VERSION;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.ScaleXSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import androidx.core.graphics.ColorUtils;

import java.io.File;

import com.letsmake.atoz.design.R;
import com.letsmake.atoz.design.custom_sticker.MultiTouch_TV_Listener.TouchCallbackListener;
import com.letsmake.atoz.design.custom_view.TextView_ReSized;

import static com.letsmake.atoz.design.app_utils.AppConfiguration.findFontDir;

public class IntelligentTVAutoFit extends RelativeLayout implements TouchCallbackListener {

    Animation scale, zoomInScale, zoomOutScale;

    private Context context;

    public boolean isMultiTouchEnabled = true;
    private ImageView rotate_iv, scale_iv;
    private boolean isBold, isFisrtAnimation = false, isUnderLine = false, isBorderVisible = false, isFromAddText = false, isItalic = false;

    private float heightMain = 0.0f, leftRightShadow = 0.0f;

    private int zRotateProg = 0, wi, width, topMargin = 0, xRotateProg = 0, yRotateProg = 0, sh = 1794, sw = 1080, tColor = -16777216, shadowProg = 0, tAlpha = 100, shadowColor = 0, shadowColorProgress = 255;

    private static final String TAG = "AutofitTextRel";
    private double angle = 0.0d;

    public ImageView background_iv;
    private int capitalFlage, baseh, basew, bgAlpha = 255, basex, basey, bgColor = 0;

    public String bgDrawable = "0";
    private ImageView border_iv;
    float cY = 0.0f, cX = 0.0f;
    double dAngle = 0.0d;
    private ImageView delete_iv;
    private int leftMargin = 0, he, height, f27s, field_one = 0, extraMargin = 35;

    private String fontName = "", field_two = "0,0", field_three = "", field_four = "";

    private GestureDetector gd = null;

    float ratio, rotation, topBottomShadow = 0.0f;
    double tAngle = 0.0d;
    private String text = "";
    private Path textPath;

    public TextView_ReSized text_iv;
    double vAngle = 0.0d;

    float widthMain = 0.0f;


    public IntelligentTVAutoFit(Context context) {
        super(context);
        initialization(context);
    }

    public IntelligentTVAutoFit(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initialization(context);
    }

    public IntelligentTVAutoFit(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initialization(context);
    }

    public TouchEventListener listener = null;

    private int margl, margin5dp = 2, margt, progress = 0;


    public void initialization(Context context) {
        this.context = context;
        Display defaultDisplay = ((Activity) this.context).getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        this.width = point.x;
        this.height = point.y;
        this.ratio = ((float) this.width) / ((float) this.height);
        this.text_iv = new TextView_ReSized(this.context);
        this.scale_iv = new ImageView(this.context);
        this.border_iv = new ImageView(this.context);
        this.background_iv = new ImageView(this.context);
        this.delete_iv = new ImageView(this.context);
        this.rotate_iv = new ImageView(this.context);
        this.f27s = (int) convertdpToPx(this.context, 30.0f);
        this.extraMargin = (int) convertdpToPx(this.context, 30.0f);
        this.margin5dp = (int) convertdpToPx(this.context, 2.5f);
        this.wi = (int) convertdpToPx(this.context, 200.0f);
        this.he = (int) convertdpToPx(this.context, 200.0f);
        this.scale_iv.setImageResource(R.drawable.sticker_scale);
        int i = 0;
        this.background_iv.setImageResource(0);
        this.rotate_iv.setImageResource(R.drawable.sticker_rotate);
        this.delete_iv.setImageResource(R.drawable.sticker_delete1);
        LayoutParams layoutParams = new LayoutParams(this.wi, this.he);
        int i2 = this.f27s;
        LayoutParams layoutParams2 = new LayoutParams(i2, i2);
        layoutParams2.addRule(12);
        layoutParams2.addRule(11);
        int i3 = this.f27s;
        LayoutParams layoutParams3 = new LayoutParams(i3, i3);
        layoutParams3.addRule(12);
        layoutParams3.addRule(9);
        LayoutParams layoutParams4 = new LayoutParams(-1, -1);
        if (VERSION.SDK_INT >= 17) {
            i = 1;
        }
        if (i != 0) {
            layoutParams4.addRule(17);
        } else {
            layoutParams4.addRule(1);
        }
        int i4 = this.f27s;
        LayoutParams layoutParams5 = new LayoutParams(i4, i4);
        layoutParams5.addRule(10);
        layoutParams5.addRule(9);
        LayoutParams layoutParams6 = new LayoutParams(-1, -1);
        LayoutParams layoutParams7 = new LayoutParams(-1, -1);
        setLayoutParams(layoutParams);
        setBackgroundResource(R.drawable.border_gray);
        addView(this.background_iv);
        this.background_iv.setLayoutParams(layoutParams7);
        this.background_iv.setScaleType(ScaleType.FIT_XY);
        addView(this.border_iv);
        this.border_iv.setLayoutParams(layoutParams6);
        this.border_iv.setTag("border_iv");
        addView(this.text_iv);
        this.text_iv.setText(this.text);
        this.text_iv.setTextColor(this.tColor);
        this.text_iv.setTextSize(800.0f);
        this.text_iv.setLayoutParams(layoutParams4);
        this.text_iv.setGravity(17);
        this.text_iv.setMinTVTextSize(10.0f);
        addView(this.delete_iv);
        this.delete_iv.setLayoutParams(layoutParams5);
        this.delete_iv.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                final ViewGroup viewGroup = (ViewGroup) IntelligentTVAutoFit.this.getParent();
                IntelligentTVAutoFit.this.zoomInScale.setAnimationListener(new AnimationListener() {
                    public void onAnimationRepeat(Animation animation) {
                    }

                    public void onAnimationStart(Animation animation) {
                    }

                    public void onAnimationEnd(Animation animation) {
                        viewGroup.removeView(IntelligentTVAutoFit.this);
                    }
                });
                IntelligentTVAutoFit.this.text_iv.startAnimation(IntelligentTVAutoFit.this.zoomInScale);
                IntelligentTVAutoFit.this.background_iv.startAnimation(IntelligentTVAutoFit.this.zoomInScale);
                IntelligentTVAutoFit autofitTextRel = IntelligentTVAutoFit.this;
                autofitTextRel.isFisrtAnimation = true;
                autofitTextRel.applyBorderVisibility(false);
                if (IntelligentTVAutoFit.this.listener != null) {
                    IntelligentTVAutoFit.this.listener.onDelete();
                }
            }
        });
        addView(this.rotate_iv);
        this.rotate_iv.setLayoutParams(layoutParams3);
        this.rotate_iv.setOnTouchListener(this.rTextViewTouchListener);
        addView(this.scale_iv);
        this.scale_iv.setLayoutParams(layoutParams2);
        this.scale_iv.setTag("scale_iv");
        this.scale_iv.setOnTouchListener(this.mTVTouchListener1);
        this.rotation = getRotation();
        this.scale = AnimationUtils.loadAnimation(getContext(), R.anim.anim_txt_scale_view);
        this.zoomOutScale = AnimationUtils.loadAnimation(getContext(), R.anim.anim_txt_scale_zoom_out);
        this.zoomInScale = AnimationUtils.loadAnimation(getContext(), R.anim.anim_txt_scale_zoom);
        initializationGD();
        this.isMultiTouchEnabled = setTvDefaultTouchListener(true);
    }


    private OnTouchListener mTVTouchListener1 = new OnTouchListener() {
        @SuppressLint({"NewApi"})
        public boolean onTouch(View view, MotionEvent motionEvent) {
            IntelligentTVAutoFit autofitTextRel = (IntelligentTVAutoFit) view.getParent();
            int rawX = (int) motionEvent.getRawX();
            int rawY = (int) motionEvent.getRawY();
            LayoutParams layoutParams = (LayoutParams) IntelligentTVAutoFit.this.getLayoutParams();
            switch (motionEvent.getAction()) {
                case 0:
                    if (autofitTextRel != null) {
                        autofitTextRel.requestDisallowInterceptTouchEvent(true);
                    }
                    if (IntelligentTVAutoFit.this.listener != null) {
                        IntelligentTVAutoFit.this.listener.onScaleDown(IntelligentTVAutoFit.this, motionEvent);
                    }
                    IntelligentTVAutoFit.this.invalidate();
                    autofitTextRel = IntelligentTVAutoFit.this;
                    autofitTextRel.basex = rawX;
                    autofitTextRel.basey = rawY;
                    autofitTextRel.basew = autofitTextRel.getWidth();
                    autofitTextRel = IntelligentTVAutoFit.this;
                    autofitTextRel.baseh = autofitTextRel.getHeight();
                    IntelligentTVAutoFit.this.getLocationOnScreen(new int[2]);
                    IntelligentTVAutoFit.this.margl = layoutParams.leftMargin;
                    IntelligentTVAutoFit.this.margt = layoutParams.topMargin;
                    break;
                case 1:
                    autofitTextRel = IntelligentTVAutoFit.this;
                    autofitTextRel.wi = autofitTextRel.getLayoutParams().width;
                    autofitTextRel = IntelligentTVAutoFit.this;
                    autofitTextRel.he = autofitTextRel.getLayoutParams().height;
                    autofitTextRel = IntelligentTVAutoFit.this;
                    autofitTextRel.leftMargin = ((LayoutParams) autofitTextRel.getLayoutParams()).leftMargin;
                    autofitTextRel = IntelligentTVAutoFit.this;
                    autofitTextRel.topMargin = ((LayoutParams) autofitTextRel.getLayoutParams()).topMargin;
                    autofitTextRel = IntelligentTVAutoFit.this;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(String.valueOf(IntelligentTVAutoFit.this.leftMargin));
                    stringBuilder.append(",");
                    stringBuilder.append(String.valueOf(IntelligentTVAutoFit.this.topMargin));
                    autofitTextRel.field_two = stringBuilder.toString();
                    if (IntelligentTVAutoFit.this.listener != null) {
                        IntelligentTVAutoFit.this.listener.onScaleUp(IntelligentTVAutoFit.this, motionEvent);
                        break;
                    }
                    break;
                case 2:
                    if (autofitTextRel != null) {
                        autofitTextRel.requestDisallowInterceptTouchEvent(true);
                    }
                    if (IntelligentTVAutoFit.this.listener != null) {
                        IntelligentTVAutoFit.this.listener.onScaleMove(IntelligentTVAutoFit.this, motionEvent);
                    }
                    float toDegrees = (float) Math.toDegrees(Math.atan2((double) (rawY - IntelligentTVAutoFit.this.basey), (double) (rawX - IntelligentTVAutoFit.this.basex)));
                    if (toDegrees < 0.0f) {
                        toDegrees += 360.0f;
                    }
                    rawX -= IntelligentTVAutoFit.this.basex;
                    rawY -= IntelligentTVAutoFit.this.basey;
                    rawY *= rawY;
                    int sqrt = (int) (Math.sqrt((double) ((rawX * rawX) + rawY)) * Math.cos(Math.toRadians((double) (toDegrees - IntelligentTVAutoFit.this.getRotation()))));
                    int sqrt2 = (int) (Math.sqrt((double) ((sqrt * sqrt) + rawY)) * Math.sin(Math.toRadians((double) (toDegrees - IntelligentTVAutoFit.this.getRotation()))));
                    rawX = (sqrt * 2) + IntelligentTVAutoFit.this.basew;
                    rawY = (sqrt2 * 2) + IntelligentTVAutoFit.this.baseh;
                    if (rawX > IntelligentTVAutoFit.this.f27s) {
                        layoutParams.width = rawX;
                        layoutParams.leftMargin = IntelligentTVAutoFit.this.margl - sqrt;
                    }
                    if (rawY > IntelligentTVAutoFit.this.f27s) {
                        layoutParams.height = rawY;
                        layoutParams.topMargin = IntelligentTVAutoFit.this.margt - sqrt2;
                    }
                    IntelligentTVAutoFit.this.setLayoutParams(layoutParams);
                    if (!IntelligentTVAutoFit.this.bgDrawable.equals("0")) {
                        autofitTextRel = IntelligentTVAutoFit.this;
                        autofitTextRel.wi = autofitTextRel.getLayoutParams().width;
                        autofitTextRel = IntelligentTVAutoFit.this;
                        autofitTextRel.he = autofitTextRel.getLayoutParams().height;
                        autofitTextRel = IntelligentTVAutoFit.this;
                        autofitTextRel.setTVBgDrawable(autofitTextRel.bgDrawable);
                        break;
                    }
                    break;
            }
            return true;
        }
    };

    class SimpleTVListner extends SimpleOnGestureListener {
        public boolean onDoubleTapEvent(MotionEvent motionEvent) {
            return true;
        }

        public boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        SimpleTVListner() {
        }

        @Override
        public boolean onDoubleTap(MotionEvent motionEvent) {
            if (IntelligentTVAutoFit.this.listener != null) {
                IntelligentTVAutoFit.this.listener.onDoubleTap();
            }
            return true;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {
            super.onLongPress(motionEvent);
        }
    }


    public void applyLetterSpacing(float f) {
        if (this.text != null) {
            StringBuilder stringBuilder = new StringBuilder();
            int i = 0;
            while (i < this.text.length()) {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("");
                stringBuilder2.append(this.text.charAt(i));
                stringBuilder.append(stringBuilder2.toString());
                i++;
                if (i < this.text.length()) {
                    stringBuilder.append(" ");
                }
            }
            SpannableString spannableString = new SpannableString(stringBuilder.toString());
            int i2 = 1;
            if (stringBuilder.toString().length() > 1) {
                while (i2 < stringBuilder.toString().length()) {
                    spannableString.setSpan(new ScaleXSpan((1.0f + f) / 10.0f), i2, i2 + 1, 33);
                    i2 += 2;
                }
            }
            this.text_iv.setText(spannableString, BufferType.SPANNABLE);
        }
    }


    public interface TouchEventListener {
        void onDelete();

        void onDoubleTap();

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

    public boolean isTVFromAddText() {
        return this.isFromAddText;
    }

    public void setTVFromAddText(boolean z) {
        this.isFromAddText = z;
    }


    public boolean isTVFisrtAnimation() {
        return this.isFisrtAnimation;
    }

    public void setTVFisrtAnimation(boolean z) {
        this.isFisrtAnimation = z;
    }

    public void setOnTV_TouchCallbackListener(TouchEventListener touchEventListener) {
        this.listener = touchEventListener;
    }

    public void setTVDrawParams() {
        invalidate();
    }


    public void applyLineSpacing(float f) {
        this.text_iv.setLineSpacing(f, 1.0f);
    }

    public void useBoldFont() {
        if (this.isBold) {
            this.isBold = false;
            this.text_iv.setTypeface(Typeface.DEFAULT);
            return;
        }
        this.isBold = true;
        this.text_iv.setTypeface(Typeface.DEFAULT_BOLD);
    }

    public void useCapitalFont() {
        TextView_ReSized textViewReSized;
        if (this.capitalFlage == 0) {
            this.capitalFlage = 1;
            textViewReSized = this.text_iv;
            textViewReSized.setText(textViewReSized.getText().toString().toUpperCase());
            return;
        }
        this.capitalFlage = 0;
        textViewReSized = this.text_iv;
        textViewReSized.setText(textViewReSized.getText().toString().toLowerCase());
    }

    public void useUnderLineFont() {
        if (this.isUnderLine) {
            this.isUnderLine = false;
            this.text_iv.setText(Html.fromHtml(this.text.replace("<u>", "").replace("</u>", "")));
            return;
        }
        this.isUnderLine = true;
        TextView_ReSized textViewReSized = this.text_iv;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<u>");
        stringBuilder.append(this.text);
        stringBuilder.append("</u>");
        textViewReSized.setText(Html.fromHtml(stringBuilder.toString()));
    }


    public void setTVTextInfo(TV_Info_Adapter textInfo, boolean z) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(textInfo.getTVPOS_X());
        stringBuilder.append(" ,");
        stringBuilder.append(textInfo.getTVPOS_Y());
        stringBuilder.append(" ,");
        stringBuilder.append(textInfo.getTVWIDTH());
        stringBuilder.append(" ,");
        stringBuilder.append(textInfo.getTVHEIGHT());
        stringBuilder.append(" ,");
        stringBuilder.append(textInfo.getFIELD_TWO());
        Log.e("set Text value", stringBuilder.toString());
        this.wi = textInfo.getTVWIDTH();
        this.he = textInfo.getTVHEIGHT();
        this.text = textInfo.getTEXT();
        this.fontName = textInfo.get_TV_FONT_NAME();
        this.tColor = textInfo.getTEXT_COLOR();
        this.tAlpha = textInfo.getTEXT_ALPHA();
        this.shadowColor = textInfo.getTV_SHADOW_COLOR();
        this.shadowProg = textInfo.getTV_SHADOW_PROG();
        this.bgColor = textInfo.getTV_BG_COLOR();
        this.bgDrawable = textInfo.getTV_BG_DRAWABLE();
        this.bgAlpha = textInfo.getTV_BG_ALPHA();
        this.rotation = textInfo.get_TV_ROTATION();
        this.field_two = textInfo.getFIELD_TWO();
        setTVText(this.text);
        setTextFont(this.fontName);
        setTVTextColor(this.tColor);
        setTVTextAlpha(this.tAlpha);
        applyTextShadowColor(this.shadowColor);
        setTVTextShadowProg(this.shadowProg);
        int i = this.bgColor;
        if (i != 0) {
            setTVBgColor(i);
        } else {
            this.background_iv.setBackgroundColor(0);
        }
        if (this.bgDrawable.equals("0")) {
            this.background_iv.setImageBitmap(null);
        } else {
            setTVBgDrawable(this.bgDrawable);
        }
        setTVBgAlpha(this.bgAlpha);
        setRotation(textInfo.get_TV_ROTATION());
        if (this.field_two.equals("")) {
            getLayoutParams().width = this.wi;
            getLayoutParams().height = this.he;
            setX(textInfo.getTVPOS_X());
            setY(textInfo.getTVPOS_Y());
            return;
        }
        try {
            String[] split = this.field_two.split(",");
            int parseInt = Integer.parseInt(split[0]);
            i = Integer.parseInt(split[1]);
            ((LayoutParams) getLayoutParams()).leftMargin = parseInt;
            ((LayoutParams) getLayoutParams()).topMargin = i;
            getLayoutParams().width = this.wi;
            getLayoutParams().height = this.he;
            setX(textInfo.getTVPOS_X() + ((float) (parseInt * -1)));
            setY(textInfo.getTVPOS_Y() + ((float) (i * -1)));
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            getLayoutParams().width = this.wi;
            getLayoutParams().height = this.he;
            setX(textInfo.getTVPOS_X());
            setY(textInfo.getTVPOS_Y());
            e.printStackTrace();
        }
    }

    public void useItalicFont() {
        if (this.isItalic) {
            this.isItalic = false;
            TextView textView = new TextView(this.context);
            textView.setText(this.text);
            if (this.isBold) {
                textView.setTypeface(textView.getTypeface(), 1);
            } else {
                textView.setTypeface(textView.getTypeface(), 0);
            }
            this.text_iv.setTypeface(textView.getTypeface());
            return;
        }
        this.isItalic = true;
        TextView textView2 = new TextView(this.context);
        textView2.setText(this.text);
        if (this.isBold) {
            textView2.setTypeface(textView2.getTypeface(), 3);
        } else {
            textView2.setTypeface(textView2.getTypeface(), 2);
        }
        this.text_iv.setTypeface(textView2.getTypeface());
    }

    public void useLeftAlignMent() {
        this.text_iv.setGravity(19);
    }

    public void useCenterAlignMent() {
        this.text_iv.setGravity(17);
    }

    public void useRightAlignMent() {
        this.text_iv.setGravity(21);
    }

    public boolean setTvDefaultTouchListener(boolean z) {
        if (z) {
            setOnTouchListener(new MultiTouch_TV_Listener().enableRotation(true).setOnTouchCallbackListener(this).setGestureListener(this.gd));
            return true;
        }
        setOnTouchListener(null);
        return false;
    }


    public boolean checkBorderVisibility() {
        return this.isBorderVisible;
    }

    public void applyBorderVisibility(boolean z) {
        this.isBorderVisible = z;
        if (!z) {
            this.border_iv.setVisibility(View.GONE);
            this.scale_iv.setVisibility(View.GONE);
            this.delete_iv.setVisibility(View.GONE);
            this.rotate_iv.setVisibility(View.GONE);
            setBackgroundResource(0);
        } else if (this.border_iv.getVisibility() != 0) {
            this.border_iv.setVisibility(View.VISIBLE);
            this.scale_iv.setVisibility(View.VISIBLE);
            this.delete_iv.setVisibility(View.VISIBLE);
            this.rotate_iv.setVisibility(View.VISIBLE);
            setBackgroundResource(R.drawable.border_gray);
            if (this.isFisrtAnimation || this.isFromAddText) {
                this.text_iv.startAnimation(this.scale);
            }
            this.isFisrtAnimation = true;
        }
    }

    public String getTVText() {
        return this.text_iv.getText().toString();
    }

    public void setTVText(String str) {
        this.text_iv.setText(str);
        this.text = str;
        if (this.isFisrtAnimation || this.isFromAddText) {
            this.text_iv.startAnimation(this.zoomOutScale);
        }
        this.isFisrtAnimation = true;
    }

    public void setTextFont(String str) {
        try {
            if (str.equals("default")) {
                this.text_iv.setTypeface(Typeface.createFromAsset(this.context.getAssets(), "font/Default.ttf"));
                this.fontName = str;
                return;
            }
            File file = new File(findFontDir(this.context), str);
            if (file.exists()) {
                try {
                    this.text_iv.setTypeface(Typeface.createFromFile(file));
                    this.fontName = str;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                AssetManager assets = this.context.getAssets();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("font/");
                stringBuilder.append(str);
                this.text_iv.setTypeface(Typeface.createFromAsset(assets, stringBuilder.toString()));
                this.fontName = str;
            }
        } catch (Exception unused) {
            Log.e(TAG, "setTextFont: ");
        }
    }

    public String getTextFontName() {
        return this.fontName;
    }

    public int getTVTextColor() {
        return this.tColor;
    }

    public void setTVTextColor(int i) {
        this.text_iv.setTextColor(i);
        this.tColor = i;
    }

    public int getTVTextAlpha() {
        return this.tAlpha;
    }

    public void setTVTextAlpha(int i) {
        this.text_iv.setAlpha(((float) i) / 100.0f);
        this.tAlpha = i;
    }

    public int checkTextShadowColor() {
        return this.shadowColor;
    }

    public void applyTextShadowColor(int i) {
        this.shadowColor = i;
        this.shadowColor = ColorUtils.setAlphaComponent(this.shadowColor, this.shadowColorProgress);
        this.text_iv.setShadowLayer((float) this.shadowProg, this.leftRightShadow, this.topBottomShadow, this.shadowColor);
    }

    public void applyTextShadowOpacity(int i) {
        this.shadowColorProgress = i;
        this.shadowColor = ColorUtils.setAlphaComponent(this.shadowColor, i);
        this.text_iv.setShadowLayer((float) this.shadowProg, this.leftRightShadow, this.topBottomShadow, this.shadowColor);
    }

    public void applyLeftRightShadow(float f) {
        this.leftRightShadow = f;
        this.text_iv.setShadowLayer((float) this.shadowProg, this.leftRightShadow, this.topBottomShadow, this.shadowColor);
    }

    public void applyTopBottomShadow(float f) {
        this.topBottomShadow = f;
        this.text_iv.setShadowLayer((float) this.shadowProg, this.leftRightShadow, this.topBottomShadow, this.shadowColor);
    }

    public int getTVTextShadowProg() {
        return this.shadowProg;
    }

    public void setTVTextShadowProg(int i) {
        this.shadowProg = i;
        this.text_iv.setShadowLayer((float) this.shadowProg, this.leftRightShadow, this.topBottomShadow, this.shadowColor);
    }

    public String getTVBgDrawable() {
        return this.bgDrawable;
    }

    public void setTVBgDrawable(String str) {
        this.bgDrawable = str;
        this.bgColor = 0;
        try {
            this.background_iv.setImageBitmap(getTVTiledBitmap(this.context, getResources().getIdentifier(str, "drawable", this.context.getPackageName()), this.wi, this.he));
        } catch (Exception e) {
//            CrashlyticsTracker.report(e, e.getPOSTERMessage());
        }
        this.background_iv.setBackgroundColor(this.bgColor);
    }

    public int getTVBgColor() {
        return this.bgColor;
    }

    public void setTVBgColor(int i) {
        this.bgDrawable = "0";
        this.bgColor = i;
        this.background_iv.setImageBitmap(null);
        this.background_iv.setBackgroundColor(i);
    }

    public int getTVBgAlpha() {
        return this.bgAlpha;
    }

    public void setTVBgAlpha(int i) {
        this.background_iv.setAlpha(((float) i) / 255.0f);
        this.bgAlpha = i;
    }


    public void optimizeTV(float f, float f2) {
        setX(getX() * f);
        setY(getY() * f2);
        getLayoutParams().width = (int) (((float) this.wi) * f);
        getLayoutParams().height = (int) (((float) this.he) * f2);
    }

    public void incrX() {
        setX(getX() + 2.0f);
    }

    public void decX() {
        setX(getX() - 2.0f);
    }


    public TV_Info_Adapter getTVTextInfo() {
        TV_Info_Adapter textInfo = new TV_Info_Adapter();
        textInfo.setTVPOS_X(getX());
        textInfo.setTVPOS_Y(getY());
        textInfo.setTVWIDTH(this.wi);
        textInfo.setTVHEIGHT(this.he);
        textInfo.setTEXT(this.text);
        textInfo.setTV_FONT_NAME(this.fontName);
        textInfo.setTEXT_COLOR(this.tColor);
        textInfo.setTEXT_ALPHA(this.tAlpha);
        textInfo.setTV_SHADOW_COLOR(this.shadowColor);
        textInfo.setTVSHADOW_PROG(this.shadowProg);
        textInfo.setTV_BG_COLOR(this.bgColor);
        textInfo.setTV_BG_DRAWABLE(this.bgDrawable);
        textInfo.setTV_BG_ALPHA(this.bgAlpha);
        textInfo.setTV_ROTATION(getRotation());
        textInfo.setTV_XRotateProg(this.xRotateProg);
        textInfo.setTV_YRotateProg(this.yRotateProg);
        textInfo.setTV_ZRotateProg(this.zRotateProg);
        textInfo.setCurveRotateProg(this.progress);
        textInfo.setFIELD_ONE(this.field_one);
        textInfo.setFIELD_TWO(this.field_two);
        textInfo.setFIELD_THREE(this.field_three);
        textInfo.setFIELD_FOUR(this.field_four);
        return textInfo;
    }

    public void incrY() {
        setY(getY() + 2.0f);
    }

    public void decY() {
        setY(getY() - 2.0f);
    }

    public float convertdpToPx(Context context, float f) {
        context.getResources();
        return (float) Math.round(f * Resources.getSystem().getDisplayMetrics().density);
    }

    private Bitmap getTVTiledBitmap(Context context, int i, int i2, int i3) {
        Rect rect = new Rect(0, 0, i2, i3);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(BitmapFactory.decodeResource(context.getResources(), i, new Options()), TileMode.REPEAT, TileMode.REPEAT));
        Bitmap createBitmap = Bitmap.createBitmap(i2, i3, Config.ARGB_8888);
        new Canvas(createBitmap).drawRect(rect, paint);
        return createBitmap;
    }

    private void initializationGD() {
        this.gd = new GestureDetector(this.context, new SimpleTVListner());
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

    public float getNewX(float f) {
        return ((float) this.width) * (f / ((float) this.sw));
    }

    public float getNewY(float f) {
        return ((float) this.height) * (f / ((float) this.sh));
    }

    public void setViewWH(float f, float f2) {
        this.widthMain = f;
        this.heightMain = f2;
    }

    public float getMainWidth() {
        return this.widthMain;
    }

    private OnTouchListener rTextViewTouchListener = new OnTouchListener() {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            IntelligentTVAutoFit autofitTextRel = (IntelligentTVAutoFit) view.getParent();
            switch (motionEvent.getAction()) {
                case 0:
                    if (autofitTextRel != null) {
                        autofitTextRel.requestDisallowInterceptTouchEvent(true);
                    }
                    if (IntelligentTVAutoFit.this.listener != null) {
                        IntelligentTVAutoFit.this.listener.onRotateDown(IntelligentTVAutoFit.this, motionEvent);
                    }
                    Rect rect = new Rect();
                    ((View) view.getParent()).getGlobalVisibleRect(rect);
                    IntelligentTVAutoFit.this.cX = rect.exactCenterX();
                    IntelligentTVAutoFit.this.cY = rect.exactCenterY();
                    IntelligentTVAutoFit.this.vAngle = (double) ((View) view.getParent()).getRotation();
                    IntelligentTVAutoFit autofitTextRel2 = IntelligentTVAutoFit.this;
                    autofitTextRel2.tAngle = (Math.atan2((double) (autofitTextRel2.cY - motionEvent.getRawY()), (double) (IntelligentTVAutoFit.this.cX - motionEvent.getRawX())) * 180.0d) / 3.141592653589793d;
                    autofitTextRel2 = IntelligentTVAutoFit.this;
                    autofitTextRel2.dAngle = autofitTextRel2.vAngle - IntelligentTVAutoFit.this.tAngle;
                    break;
                case 1:
                    if (IntelligentTVAutoFit.this.listener != null) {
                        IntelligentTVAutoFit.this.listener.onRotateUp(IntelligentTVAutoFit.this, motionEvent);
                        break;
                    }
                    break;
                case 2:
                    if (autofitTextRel != null) {
                        autofitTextRel.requestDisallowInterceptTouchEvent(true);
                    }
                    if (IntelligentTVAutoFit.this.listener != null) {
                        IntelligentTVAutoFit.this.listener.onRotateMove(IntelligentTVAutoFit.this, motionEvent);
                    }
                    autofitTextRel = IntelligentTVAutoFit.this;
                    autofitTextRel.angle = (Math.atan2((double) (autofitTextRel.cY - motionEvent.getRawY()), (double) (IntelligentTVAutoFit.this.cX - motionEvent.getRawX())) * 180.0d) / 3.141592653589793d;
                    float f = (float) (IntelligentTVAutoFit.this.angle + IntelligentTVAutoFit.this.dAngle);
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

    public float getMainHeight() {
        return this.heightMain;
    }
}
