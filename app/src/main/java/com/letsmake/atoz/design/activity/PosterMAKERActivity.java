package com.letsmake.atoz.design.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.OnScanCompletedListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.billingclient.api.Purchase;
import com.androidnetworking.AndroidNetworking;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.Scopes;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import com.letsmake.atoz.design.ads.InterstitialAds;
import com.letsmake.atoz.design.ads.NativeAds;
import com.letsmake.atoz.design.billing.BillingUpdatesListener;
import com.letsmake.atoz.design.fragments.fragment_create;
import com.letsmake.atoz.design.billing.SubscriptionsUtil;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.model.AspectRatio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.annotation.Obsolete;
import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageGaussianBlurFilter;

import com.letsmake.atoz.design.R;
import com.letsmake.atoz.design.app_utils.AppPreferenceClass;
import com.letsmake.atoz.design.app_utils.ViewElementInfo;
import com.letsmake.atoz.design.custom_adapter.Fonts_Adapter;
import com.letsmake.atoz.design.custom_adapter.OverLayer_Adapter;
import com.letsmake.atoz.design.custom_adapter.Text_Bg_Adapter;
import com.letsmake.atoz.design.custom_sticker.IntelligentTVAutoFit;
import com.letsmake.atoz.design.custom_sticker.TV_Info_Adapter;
import com.letsmake.atoz.design.custom_view.AutoFitEditText;
import com.letsmake.atoz.design.custom_view.AutoStickerView;
import com.letsmake.atoz.design.custom_view.AutoStickerView.TouchEventListener;
import com.letsmake.atoz.design.custom_view.Grid_Line_Saparator;
import com.letsmake.atoz.design.custom_view.Round_Corner_Transformation;
import com.letsmake.atoz.design.editor_intelligence.AppConstants;
import com.letsmake.atoz.design.editor_intelligence.Drag_List_Fragment;
import com.letsmake.atoz.design.editor_intelligence.ExtractColorListener;
import com.letsmake.atoz.design.editor_intelligence.OnSetImageSticker;
import com.letsmake.atoz.design.editor_intelligence.View_ID_Finder;
import com.letsmake.atoz.design.fragments.Fragment_BG2;
import com.letsmake.atoz.design.fragments.Fragment_BGImg;
import com.letsmake.atoz.design.fragments.Fragment_Edit_Image;
import com.letsmake.atoz.design.fragments.Fragment_Get_Stickers;
import com.letsmake.atoz.design.fragments.Fragment_More_Sticker;
import com.letsmake.atoz.design.handler.Blur_Task_Async;
import com.letsmake.atoz.design.handler.DB_Handler;
import com.letsmake.atoz.design.handler.Task_Repeat_Listener;
import com.letsmake.atoz.design.handler.Template_InfoData;
import com.letsmake.atoz.design.imageloader.Adjust_Filter;
import com.letsmake.atoz.design.imageloader.Glide_Image_Utils;
import com.letsmake.atoz.design.listener.On_Click_Callback;
import com.letsmake.atoz.design.listener.On_Data_Snap_Listener;
import com.letsmake.atoz.design.listener.On_Snap_Listener;
import com.letsmake.atoz.design.listener.RV_Item_Click_Listener;
import com.letsmake.atoz.design.listener.RV_Item_Click_Listener.OnItemClickListener;
import com.letsmake.atoz.design.poster_builder.BG_Image;
import com.letsmake.atoz.design.poster_builder.Poster_Co;
import com.letsmake.atoz.design.poster_builder.StickerInfo;
import com.letsmake.atoz.design.poster_builder.Text_Info;
import com.letsmake.atoz.design.receiver.NetworkConnectivityReceiver;

import uz.shift.colorpicker.LineColorPicker;
import uz.shift.colorpicker.OnColorChangedListener;
import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener;

import static android.os.Build.VERSION.SDK_INT;

public class PosterMAKERActivity extends ShapeActivity implements OnClickListener, BillingUpdatesListener, OnSeekBarChangeListener, On_Data_Snap_Listener, ExtractColorListener, OnSetImageSticker, On_Snap_Listener, TouchEventListener, IntelligentTVAutoFit.TouchEventListener {

    public ArrayList<StickerInfo> stickerInfoArrayList = new ArrayList();
    public List<Template_InfoData> templateList = new ArrayList();
    private List<WeakReference<Fragment>> mFragments = new ArrayList();
    public ArrayList<Text_Info> textInfoArrayList = new ArrayList();

    private AnimationDrawable animationDrawable;

    private static final String TAG = "PosterMAKERActivity";

    private static final int SELECT_PICTURE_CAMERA = 805;
    private static final int SELECT_PICTURE_FROM_GALLERY_BACKGROUND = 809;
    private static final int SELECT_PICTURE_GALLERY = 807;

    private static final int TEXT_ACTIVITY = 808;
    private static final int TYPE_STICKER = 8072;

    public static PosterMAKERActivity activity;
    public static ImageView btnLayer;
    public static FrameLayout layerContainer;
    public static RelativeLayout stickerLayout;
    public static Bitmap withoutWatermark;

    public SeekBar alphaSeekbar, verticalSeekBar, seek, seekBlur, seekLetterSpacing, seekLineSpacing, seekShadowBlur, seekTextCurve, seekTailys, seekBarShadow, seekBar3;
    private RelativeLayout centerRelative, addSticker, addText, userImage, shapeRelative;
    public View selectedView;
    public ImageButton edit_reset, edit_video_tutorial;
    public int sizeFull = 0, shadowFlag = 0, seekValue = 90;
    private TextView txtBG, txtControlText, txtSticker, txtImage, txtText, txtTextControls, txtShadowControl, txtFontsControl, txtFontsCurve, txtFontsSpacing, txtFontsStyle, txtEffect, txtEffectText, txtFilterText, txtColorOpacity, txtColorsControl, txtBgControl;
    public int textShadowProg = 0, topBottomShadow = 0, templateId, textColor = -16777216, textShadowColor = -16777216, textAlpha = 100;
    private int textColorSet = Color.parseColor("#ffffff");
    private ImageView btnImgBackground, btnShadowTabChange, btnTakePicture, btnImgCameraSticker, btnColorBackgroundPic;
    private ImageButton btnUpDown1, btnUpDown, btn_bck1;
    private RelativeLayout selectEffect, selectBackgnd, mainRelative, rellative, layColor, layFilter, layControlStkr, layColorOpacity, layHandletails, layHue, layRemove, layStkrMain, layTextMain, layoutShadow1, layoutShadow2;
    private LinearLayout bgShow, colorShow, controlsShowStkr, controlsShow, fontsCurve, fontsShow, fontsSpacing, layColorOacity, layBackground, seekbarContainer, sadowShow, seekbarHandle, layDuplicateStkr, layEffects, laySticker, layFontsSpacing, layTextEdit, layoutFilterView, layoutEffectView;

    public boolean OneShow = true;
    private int alpha = 80;
    public Animation animSlideDown, animSlideUp;
    private int bColor = Color.parseColor("#4149b6");
    public int dsfc, backgroundAlpha = 0;
    private ImageView backgroundBlur;
    public int backgroundColor = -16777216;
    public String backgroundDrawable = "0";
    private ImageView backgroundImage;

    Fonts_Adapter adapter;
    OverLayer_Adapter adaptorOverlay;
    Text_Bg_Adapter adaptorTxtBg;

    public Bitmap bitmap;
    public boolean checkMemory, checkTouchContinue = false;
    public String colorType, filename;
    Activity context;
    private int curTileId = 0;
    public ProgressDialog dialogIs;
    public boolean dialogShow = true;
    private float distance;
    private int distanceScroll;
    public boolean editMode = false;
    private File f;
    private View focusedCopy = null;
    public String hex, font_Name = "", frameName = "", pos, title;
    private Grid_Line_Saparator guideline;
    private LineColorPicker horizontalPicker, horizontalPickerColor;
    public float hr = 1.0f;
    private SeekBar hueSeekbar;
    public Bitmap imgBtmap;
    private TextView imgOK;
    public boolean isRewarded = false, isBackground = false, isUpdated = false;
    private ImageView layEdit, layDuplicateText;
    private int leftRightShadow = 0;
    private float lineSpacing = 0.0f, letterSpacing = 0.0f;

    public Drag_List_Fragment listFragment;
    private LinearLayout llLogo;
    public Handler mHandler;
    public Runnable mStatusChecker;
    private int processs, postId, mRadius, mInterval = 50, overlayOpacty, myDesignFlag, overlayBlur, min = 0;
    private Options options = new Options();
    public String overlayName = "";
    private String[] pallete = new String[]{"#ffffff", "#cccccc", "#999999", "#666666", "#333333", "#000000", "#ffee90", "#ffd700", "#daa520", "#b8860b", "#ccff66", "#adff2f", "#00fa9a", "#00ff7f", "#00ff00", "#32cd32", "#3cb371", "#99cccc", "#66cccc", "#339999", "#669999", "#006666", "#336666", "#ffcccc", "#ff9999", "#ff6666", "#ff3333", "#ff0033", "#cc0033"};
    private LineColorPicker pickerBg;
    private String position;
    private AppPreferenceClass appPreferenceClass;
    public SharedPreferences prefs;
    public String profile, ratio;
    public float parentY, screenWidth, screenHeight, rotation = 0.0f;
    ScrollView scrollLayout;
    LinearLayout footer;

    private LineColorPicker shadowPickerColor;
    private boolean showtailsSeek = false;

    public HashMap<Integer, Object> stickerObject;
    private int stkrColorSet = Color.parseColor("#ffffff");
    public String tempPath = "", tempType = "";
    private ImageView transImgage;
    public Typeface ttf, ttfHeader;
    private FrameLayout viewAllFrame;
    public float wr = 1.0f;
    public float yAtLayoutCenter = -1.0f;

    public int getNewHeightText(float f, float f2) {
        float height = (((float) mainRelative.getHeight()) * (f2 - f)) / 100.0f;
        return (int) (((float) ((int) height)) + (height / 2.0f));
    }

    public float getXpos(float f) {
        return (((float) mainRelative.getWidth()) * f) / 100.0f;
    }

    public float getYpos(float f) {
        return (((float) mainRelative.getHeight()) * f) / 100.0f;
    }

    public int getNewWidht(float f, float f2) {
        return (int) ((((float) mainRelative.getWidth()) * (f2 - f)) / 100.0f);
    }

    private float getViewHeight(int i, int i2, float f, float f2) {
        return (((float) i2) * f) / ((float) i);
    }

    private float getViewWidth(int i, int i2, float f, float f2) {
        return (((float) i) * f2) / ((float) i2);
    }

    public void onEdit(View view, Uri uri) {
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public int getNewHeight(float f, float f2) {
        return (int) ((((float) mainRelative.getHeight()) * (f2 - f)) / 100.0f);
    }

    private View decorView;

    MediaPlayer mediaPlayer;


    boolean isActive;


    private int hideSystemBars() {
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {
            decorView.setSystemUiVisibility(hideSystemBars());
        }
    }

    @Override
    public void onCreate(Bundle bundle) {
        int intExtra = 0;
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.new_activity_create_poster);

        decorView = getWindow().getDecorView();

        this.appPreferenceClass = new AppPreferenceClass(getApplicationContext());
        this.prefs = this.appPreferenceClass.getSharedPrefernce();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.screenWidth = (float) displayMetrics.widthPixels;
        this.screenHeight = (float) (displayMetrics.heightPixels - Glide_Image_Utils.convertDpToPx(this, 105.0f));

        init();
        findViewByID();
        int i = 0;

        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0) {
                    decorView.setSystemUiVisibility(hideSystemBars());
                }
            }
        });

        if (appPreferenceClass.getInt(AppConstants.isFirstTimeINTRO, 0) == 0) {
            try {
                mediaPlayer = MediaPlayer.create(this, R.raw.lets_do_editor_screen);
                mediaPlayer.start();
            } catch (Exception e) {
                e.printStackTrace();
            }

            appPreferenceClass.putInt(AppConstants.isFirstTimeINTRO, 1);

            btnLayer.postDelayed(new Runnable() {
                public void run() {
                    ScrollLayerOneTime();
                    hideScroll();
                    releaseIVControll();
                    if (layTextMain.getVisibility() == View.VISIBLE) {
                        layTextMain.startAnimation(animSlideDown);
                        layTextMain.setVisibility(View.GONE);
                    }
                    if (layStkrMain.getVisibility() == View.VISIBLE) {
                        layStkrMain.startAnimation(animSlideDown);
                        layStkrMain.setVisibility(View.GONE);
                    }
                    if (layerContainer.getVisibility() == View.GONE) {
                        btnLayer.setVisibility(View.GONE);
                        listFragment.get_Layout_Child();
                        layerContainer.setVisibility(View.VISIBLE);
                        layerContainer.animate().translationX((float) layerContainer.getLeft()).setDuration(200).setInterpolator(new DecelerateInterpolator()).start();
                        return;
                    }
                    layerContainer.setVisibility(View.VISIBLE);
                    layerContainer.animate().translationX((float) (-layerContainer.getRight())).setDuration(200).setInterpolator(new AccelerateInterpolator()).start();
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            PosterMAKERActivity.layerContainer.setVisibility(View.GONE);
                            PosterMAKERActivity.btnLayer.setVisibility(View.VISIBLE);
                        }
                    }, 200);
                }
            }, 2000);

        } else {
            // AdHelper.showInterstitial(getApplicationContext());
        }

        AndroidNetworking.initialize(getApplicationContext());
        this.context = this;
        activity = this;
        this.options.inScaled = false;
        this.ttfHeader = AppConstants.getHeaderTypeface(this);

        Typeface custom_title = Typeface.createFromAsset(getAssets(), "font/NexaBold.otf");
        ((TextView) findViewById(R.id.txtheader)).setTypeface(custom_title);

        this.myDesignFlag = getIntent().getIntExtra("catId", 0);
        this.postId = getIntent().getIntExtra("pos_id", 0);

        if (this.myDesignFlag != 0) {
            ArrayList parcelableArrayListExtra = getIntent().getParcelableArrayListExtra("template");
            this.textInfoArrayList = getIntent().getParcelableArrayListExtra("text");
            this.stickerInfoArrayList = getIntent().getParcelableArrayListExtra("sticker");
            this.profile = getIntent().getStringExtra(Scopes.PROFILE);
            this.tempPath = ((Poster_Co) parcelableArrayListExtra.get(0)).getPOSTERBack_image();
            Poster_Co posterCo = (Poster_Co) parcelableArrayListExtra.get(0);
            this.templateId = Integer.parseInt(posterCo.getPost_id());
            this.frameName = posterCo.getPOSTERBack_image();
            this.ratio = posterCo.getPOSTERRatio();
            this.dialogIs = new ProgressDialog(this);
            this.dialogIs.setMessage(getResources().getString(R.string.plzwait));
            this.dialogIs.setCancelable(false);
            this.dialogIs.show();
            drawBGImageFromDp(this.ratio, this.position, this.profile, "created");
        } else if (getIntent().getBooleanExtra("loadUserFrame", false)) {
            bundle = getIntent().getExtras();
            if (!bundle.getString("ratio").equals("cropImg")) {
                this.ratio = bundle.getString("ratio");
                this.position = bundle.getString("position");
                this.profile = bundle.getString(Scopes.PROFILE);
                this.hex = bundle.getString("hex");
                this.pos = bundle.getString("pos");
                drawIntelligentView(this.ratio, this.position, this.profile, "nonCreated");
            } else if (bundle.getString("ratio").equals("cropImg")) {
                this.ratio = "";
                this.position = "1";
                this.profile = "Temp_Path";
                this.hex = "";
                ResizeViewLayout(Glide_Image_Utils.resizeImageBitmap(AppConstants.bitmap, (int) this.screenWidth, (int) this.screenHeight), "nonCreated");
            }
        } else {
            tempType = getIntent().getExtras().getString("Temp_Type");
            DB_Handler dbHandler = DB_Handler.getDatabaseHandler(getApplicationContext());
            templateList = dbHandler.find_Template_List_Des("USER");
            dbHandler.close();
            intExtra = getIntent().getIntExtra("position", 0);
            final int finalIntExtra = intExtra;
            this.centerRelative.post(new Runnable() {
                public void run() {
                    TemplateLordAsync templateLordAsync = new TemplateLordAsync();
                    String[] strArr = new String[1];
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("");
                    stringBuilder.append(finalIntExtra);
                    strArr[0] = stringBuilder.toString();
                    templateLordAsync.execute(strArr);
                }
            });
        }
        int[] iArr = new int[this.pallete.length];
        while (i < iArr.length) {
            iArr[i] = Color.parseColor(this.pallete[i]);
            i++;
        }
        this.horizontalPicker.setColors(iArr);
        this.horizontalPickerColor.setColors(iArr);
        this.shadowPickerColor.setColors(iArr);
        this.pickerBg.setColors(iArr);
        this.horizontalPicker.setSelectedColor(this.textColorSet);
        this.horizontalPickerColor.setSelectedColor(this.stkrColorSet);
        this.shadowPickerColor.setSelectedColor(iArr[5]);
        this.pickerBg.setSelectedColor(iArr[5]);
        intExtra = this.horizontalPicker.getColor();
        i = this.horizontalPickerColor.getColor();
        int color = this.shadowPickerColor.getColor();
        int color2 = this.pickerBg.getColor();
        updateViewColor(intExtra);
        updateViewColor(i);
        overrideShadow(color);
        replaceBgcolor(color2);
        this.horizontalPickerColor.setOnColorChangedListener(new OnColorChangedListener() {
            public void onColorChanged(int i) {
                PosterMAKERActivity.this.updateViewColor(i);
            }
        });
        this.horizontalPicker.setOnColorChangedListener(new OnColorChangedListener() {
            public void onColorChanged(int i) {
                PosterMAKERActivity.this.updateViewColor(i);
            }
        });
        this.shadowPickerColor.setOnColorChangedListener(new OnColorChangedListener() {
            public void onColorChanged(int i) {
                PosterMAKERActivity.this.overrideShadow(i);
            }
        });
        this.pickerBg.setOnColorChangedListener(new OnColorChangedListener() {
            public void onColorChanged(int i) {
                PosterMAKERActivity.this.replaceBgcolor(i);
            }
        });
        this.viewAllFrame = (FrameLayout) findViewById(R.id.viewall_layout);
        this.guideline = (Grid_Line_Saparator) findViewById(R.id.guidelines);
        this.rellative = (RelativeLayout) findViewById(R.id.rellative);
        this.scrollLayout = (ScrollView) findViewById(R.id.lay_scroll);
        this.scrollLayout.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                PosterMAKERActivity.this.onViewTouchApply();
                return true;
            }
        });
        LayoutParams layoutParams = new LayoutParams(-1, -2);
        layoutParams.addRule(13);
        this.scrollLayout.setLayoutParams(layoutParams);
        this.scrollLayout.postInvalidate();
        this.scrollLayout.requestLayout();
        ImageView imageView = (ImageView) findViewById(R.id.btnRight);
        ImageView imageView2 = (ImageView) findViewById(R.id.btnUp);
        ImageView imageView3 = (ImageView) findViewById(R.id.btnDown);
        ImageButton imageButton = (ImageButton) findViewById(R.id.btnLeftS);
        ImageButton imageButton2 = (ImageButton) findViewById(R.id.btnRightS);
        ImageButton imageButton3 = (ImageButton) findViewById(R.id.btnUpS);
        ImageButton imageButton4 = (ImageButton) findViewById(R.id.btnDownS);
        findViewById(R.id.btnLeft).setOnTouchListener(new Task_Repeat_Listener(200, 100, this.guideline, new OnClickListener() {
            public void onClick(View view) {
                PosterMAKERActivity.this.replaceStickerPosition("decX");
            }
        }));
        imageView2.setOnTouchListener(new Task_Repeat_Listener(200, 100, this.guideline, new OnClickListener() {
            public void onClick(View view) {
                PosterMAKERActivity.this.replaceStickerPosition("incrX");
            }
        }));
        imageView.setOnTouchListener(new Task_Repeat_Listener(200, 100, this.guideline, new OnClickListener() {
            public void onClick(View view) {
                PosterMAKERActivity.this.replaceStickerPosition("decY");
            }
        }));
        imageView3.setOnTouchListener(new Task_Repeat_Listener(200, 100, this.guideline, new OnClickListener() {
            public void onClick(View view) {
                PosterMAKERActivity.this.replaceStickerPosition("incrY");
            }
        }));
        imageButton.setOnTouchListener(new Task_Repeat_Listener(200, 100, this.guideline, new OnClickListener() {
            public void onClick(View view) {
                PosterMAKERActivity.this.replaceStickerPosition("decX");
            }
        }));
        imageButton2.setOnTouchListener(new Task_Repeat_Listener(200, 100, this.guideline, new OnClickListener() {
            public void onClick(View view) {
                PosterMAKERActivity.this.replaceStickerPosition("incrX");
            }
        }));
        imageButton3.setOnTouchListener(new Task_Repeat_Listener(200, 100, this.guideline, new OnClickListener() {
            public void onClick(View view) {
                PosterMAKERActivity.this.replaceStickerPosition("decY");
            }
        }));
        imageButton4.setOnTouchListener(new Task_Repeat_Listener(200, 100, this.guideline, new OnClickListener() {
            public void onClick(View view) {
                PosterMAKERActivity.this.replaceStickerPosition("incrY");
            }
        }));
        this.mHandler = new Handler();
        this.mStatusChecker = new Runnable() {
            public void run() {
                if (PosterMAKERActivity.this.scrollLayout != null) {
                    PosterMAKERActivity posterMAKERActivity = PosterMAKERActivity.this;
                    posterMAKERActivity.removePositionScrollView(posterMAKERActivity.selectedView);
                }
                PosterMAKERActivity.this.mHandler.postDelayed(this, (long) PosterMAKERActivity.this.mInterval);
            }
        };
        AdjustOneTimeLayer();
    }

    public void removePositionScrollView(View view) {
        float rotation;
        int[] iArr = new int[2];
        this.scrollLayout.getLocationOnScreen(iArr);
        float f = (float) iArr[1];
        float width = (float) view.getWidth();
        float height = (float) view.getHeight();
        float x = view.getX();
        float y = (view.getY() + f) - ((float) this.distanceScroll);
        if (view instanceof AutoStickerView) {
            rotation = view.getRotation();
        } else {
            rotation = view.getRotation();
        }
        Matrix matrix = new Matrix();
        RectF rectF = new RectF(x, y, x + width, y + height);
        matrix.postRotate(rotation, x + (width / 2.0f), (height / 2.0f) + y);
        matrix.mapRect(rectF);
        rotation = Math.min(rectF.top, rectF.bottom);
        if (f > rotation) {
            rotation = (float) ((int) (f - rotation));
            try {
                f = (float) this.scrollLayout.getScrollY();
                if (f > 0.0f) {
                    f -= (float) (((int) rotation) / 4);
                    if (f >= 0.0f) {
                        this.scrollLayout.smoothScrollTo(0, (int) f);
                        this.scrollLayout.getLayoutParams().height = (int) (((float) this.scrollLayout.getHeight()) + (y / 4.0f));
                        this.scrollLayout.postInvalidate();
                        this.scrollLayout.requestLayout();
                        return;
                    }
                    this.distanceScroll = 0;
                    this.scrollLayout.setLayoutParams(new LayoutParams(-1, -2));
                    this.scrollLayout.postInvalidate();
                    this.scrollLayout.requestLayout();
                }
            } catch (Exception e) {
                e.printStackTrace();
//                CrashlyticsTracker.report(e, "Unexpected Exception");
            }
        }
    }

    @Override
    public void onBillingClientSetupFinished() {

    }

    @Override
    public void onPurchasesUpdated(List<Purchase> purchaseList) {
        boolean isActive = SubscriptionsUtil.isSubscriptionActive(purchaseList);

        if (!isActive) {

            // Adaptive_Banner
            new NativeAds(this).Adaptive_Banner(findViewById(R.id.adaptive_banner));

        }
    }

    @Override
    public void onPurchaseVerified() {

    }


    public class TwoBlurAsync extends AsyncTask<String, Void, String> {
        ImageView background_blur;
        Bitmap btmp;
        Activity context;

        @Override
        public String doInBackground(String... strArr) {
            return "yes";
        }

        @Override
        public void onPreExecute() {
        }

        public TwoBlurAsync(PosterMAKERActivity posterMAKERActivity, Bitmap bitmap, ImageView imageView) {
            this.context = posterMAKERActivity;
            this.btmp = bitmap;
            this.background_blur = imageView;
        }

        @Override
        public void onPostExecute(String str) {
            PosterMAKERActivity.stickerLayout.removeAllViews();
            StickersAsyncTask stickersAsyncTask;
            String[] strArr;
            StringBuilder stringBuilder;
            if (PosterMAKERActivity.this.tempPath.equals("")) {
                stickersAsyncTask = new StickersAsyncTask();
                strArr = new String[1];
                stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(PosterMAKERActivity.this.templateId);
                strArr[0] = stringBuilder.toString();
                stickersAsyncTask.execute(strArr);
                return;
            }
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), ".Poster Design Stickers/category1");
            if (file.exists()) {
                if (file.listFiles().length >= 7) {
                    stickersAsyncTask = new StickersAsyncTask();
                    strArr = new String[1];
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("");
                    stringBuilder.append(PosterMAKERActivity.this.templateId);
                    strArr[0] = stringBuilder.toString();
                    stickersAsyncTask.execute(strArr);
                } else if (new File(PosterMAKERActivity.this.tempPath).exists()) {
                    stickersAsyncTask = new StickersAsyncTask();
                    strArr = new String[1];
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("");
                    stringBuilder.append(PosterMAKERActivity.this.templateId);
                    strArr[0] = stringBuilder.toString();
                    stickersAsyncTask.execute(strArr);
                } else {
                    stickersAsyncTask = new StickersAsyncTask();
                    strArr = new String[1];
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("");
                    stringBuilder.append(PosterMAKERActivity.this.templateId);
                    strArr[0] = stringBuilder.toString();
                    stickersAsyncTask.execute(strArr);
                }
            } else if (new File(PosterMAKERActivity.this.tempPath).exists()) {
                stickersAsyncTask = new StickersAsyncTask();
                strArr = new String[1];
                stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(PosterMAKERActivity.this.templateId);
                strArr[0] = stringBuilder.toString();
                stickersAsyncTask.execute(strArr);
            } else {
                stickersAsyncTask = new StickersAsyncTask();
                strArr = new String[1];
                stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(PosterMAKERActivity.this.templateId);
                strArr[0] = stringBuilder.toString();
                stickersAsyncTask.execute(strArr);
            }
        }
    }

    private class SaveAsyncStickers extends AsyncTask<String, String, Boolean> {
        Object objk;
        String stkr_path;

        public SaveAsyncStickers(Object obj) {
            this.objk = obj;
        }


        @Override
        public void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        public Boolean doInBackground(String... strArr) {
            String str = strArr[0];
            this.stkr_path = ((ViewElementInfo) this.objk).getSTKR_PATH();
            try {
                Bitmap decodeResource = BitmapFactory.decodeResource(PosterMAKERActivity.this.getResources(), PosterMAKERActivity.this.getResources().getIdentifier(str, "drawable", PosterMAKERActivity.this.getPackageName()));
                if (decodeResource != null) {
                    return Boolean.valueOf(AppConstants.exportFinalBitmapObject(PosterMAKERActivity.this, decodeResource, this.stkr_path));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Boolean.valueOf(false);
        }

        @Override
        public void onPostExecute(Boolean bool) {
            super.onPostExecute(bool);
            PosterMAKERActivity.this.sizeFull = PosterMAKERActivity.this.sizeFull + 1;
            if (PosterMAKERActivity.this.stickerObject.size() == PosterMAKERActivity.this.sizeFull) {
                PosterMAKERActivity.this.dialogShow = true;
            }
            if (bool.booleanValue()) {
                AutoStickerView autoStickerView = new AutoStickerView(PosterMAKERActivity.this);
                PosterMAKERActivity.stickerLayout.addView(autoStickerView);
                autoStickerView.optimizeSTScreen(PosterMAKERActivity.this.screenWidth, PosterMAKERActivity.this.screenHeight);
                autoStickerView.set_STViewWH((float) PosterMAKERActivity.this.mainRelative.getWidth(), (float) PosterMAKERActivity.this.mainRelative.getHeight());
                autoStickerView.set_STView_ComponentInfo((ViewElementInfo) this.objk);
                autoStickerView.setId(View_ID_Finder.generateViewId());
                autoStickerView.optimizeSTView(PosterMAKERActivity.this.wr, PosterMAKERActivity.this.hr);
                autoStickerView.setOnTouchCallbackListener(PosterMAKERActivity.this);
                autoStickerView.applyBorderVisibility(false);
            }
            if (PosterMAKERActivity.this.dialogShow) {
                PosterMAKERActivity.this.dialogIs.dismiss();
            }
        }
    }

    private void findViewByID() {
        this.btn_bck1 = findViewById(R.id.btn_bck1);
        this.btn_bck1.setOnClickListener(this);
        this.txtTextControls = findViewById(R.id.txt_text_controls);
        this.txtFontsControl = findViewById(R.id.txt_fonts_control);
        this.txtFontsStyle = findViewById(R.id.txt_fonts_Style);
        this.layFontsSpacing = findViewById(R.id.lay_fonts_Spacing);
        this.txtFontsSpacing = findViewById(R.id.txt_fonts_Spacing);
        this.txtFontsCurve = findViewById(R.id.txt_fonts_curve);
        this.txtColorsControl = findViewById(R.id.txt_colors_control);
        this.txtShadowControl = findViewById(R.id.txt_shadow_control);
        this.txtBgControl = findViewById(R.id.txt_bg_control);
        this.txtEffectText = findViewById(R.id.txtEffectText);
        this.txtFilterText = findViewById(R.id.txtFilterText);
        this.layoutEffectView = findViewById(R.id.layoutEffectView);
        this.layoutFilterView = findViewById(R.id.layoutFilterView);
        this.btnShadowTabChange = findViewById(R.id.btnShadowTabChange);
        this.layoutShadow1 = findViewById(R.id.layoutShadow1);
        this.layoutShadow2 = findViewById(R.id.layoutShadow2);
        this.txtText = findViewById(R.id.bt_text);
        this.txtSticker = findViewById(R.id.bt_sticker);
        this.txtImage = findViewById(R.id.bt_image);
        this.txtEffect = findViewById(R.id.bt_effect);
        this.txtBG = findViewById(R.id.bt_bg);
        this.btnShadowTabChange.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (PosterMAKERActivity.this.shadowFlag == 0) {
                    PosterMAKERActivity.this.shadowFlag = 1;
                    PosterMAKERActivity.this.layoutShadow2.setVisibility(View.VISIBLE);
                    PosterMAKERActivity.this.layoutShadow1.setVisibility(View.GONE);
                } else if (PosterMAKERActivity.this.shadowFlag == 1) {
                    PosterMAKERActivity.this.shadowFlag = 0;
                    PosterMAKERActivity.this.layoutShadow1.setVisibility(View.VISIBLE);
                    PosterMAKERActivity.this.layoutShadow2.setVisibility(View.GONE);
                }
            }
        });
    }

    public void drawBGImageFromDp(final String str, String str2, final String str3, final String str4) {
        this.laySticker.setVisibility(View.GONE);
        if (new File(this.tempPath).exists()) {
            RequestBuilder load = Glide.with(getApplicationContext()).asBitmap().load(this.tempPath);
            RequestOptions requestOptions = new RequestOptions();
            float f = this.screenWidth;
            float f2 = this.screenHeight;
            if (f <= f2) {
                f = f2;
            }
            load.apply(requestOptions.override((int) f).skipMemoryCache(true)).listener(new RequestListener<Bitmap>() {
                public boolean onLoadFailed(@Nullable GlideException glideException, Object obj, Target<Bitmap> target, boolean z) {
                    try {
                        PosterMAKERActivity.this.dialogIs.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    PosterMAKERActivity.this.showErrorDialog();
                    return true;
                }

                public boolean onResourceReady(Bitmap bitmap, Object obj, Target<Bitmap> target, DataSource dataSource, boolean z) {
                    PosterMAKERActivity.this.generatebitmapRatio(str, str3, bitmap, str4);
                    return false;
                }
            }).submit();
        }
    }


    public void drawIntelligentView(final String str, String str2, String str3, final String str4) {

        this.laySticker.setVisibility(View.GONE);
        if (new File(this.profile).exists()) {
            try {
                generatebitmapRatio(str, str3, Glide_Image_Utils.getResampleGlideImageBitmap(Uri.parse(this.profile), this, (int) (this.screenWidth > this.screenHeight ? this.screenWidth : this.screenHeight)), str4);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
/*
                  Glide.with(getApplicationContext()).asBitmap().load(str3).apply(new RequestOptions().skipMemoryCache(true)).listener(new RequestListener<Bitmap>() {
                    public boolean onLoadFailed(@Nullable GlideException glideException, Object obj, Target<Bitmap> target, boolean z) {
                        try {
                            PosterMAKERActivity.this.dialogIs.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        PosterMAKERActivity.this.showErrorDialog();
                        return false;
                    }

                    public boolean onResourceReady(Bitmap bitmap, Object obj, Target<Bitmap> target, DataSource dataSource, boolean z) {
                        PosterMAKERActivity posterMAKERActivity = PosterMAKERActivity.this;
                        posterMAKERActivity.imgBtmap = bitmap;
                        posterMAKERActivity.generatebitmapRatio(str, "Background", bitmap, str4);
*/
/*
                        Bitmap createBitmap = Bitmap.createBitmap(920, 1200, Config.ARGB_8888);
                        createBitmap.eraseColor(-7650690); // 7650690
                        ResizeViewLayout(createBitmap, "nonCreated");
*//*
                        return false;
                    }
                }).submit();
*/
                if (Integer.parseInt(pos) != 0) {
                    String widhight = fragment_create.bgMakers[Integer.parseInt(pos)];
                    String[] widthheight = widhight.split(":");

                    Bitmap createBitmap = Bitmap.createBitmap(Integer.parseInt(widthheight[0]), Integer.parseInt(widthheight[1]), Config.ARGB_8888);
                    createBitmap.eraseColor(-7650690); // 7650690
                    ResizeViewLayout(createBitmap, "nonCreated");
                } else {

                    Bitmap createBitmap = Bitmap.createBitmap(Integer.parseInt(fragment_create.width), Integer.parseInt(fragment_create.height), Config.ARGB_8888);
                    createBitmap.eraseColor(-7650690); // 7650690
                    ResizeViewLayout(createBitmap, "nonCreated");
                }

            } catch (NullPointerException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void generatebitmapRatio(String str, String str2, Bitmap bitmap, String str3) {
        if (!str.equals("")) {
            if (str.equals("1:1")) {
                bitmap = cropImageRatio(bitmap, 1, 1);
            } else if (str.equals("16:9")) {
                bitmap = cropImageRatio(bitmap, 16, 9);
            } else if (str.equals("9:16")) {
                bitmap = cropImageRatio(bitmap, 9, 16);
            } else if (str.equals("4:3")) {
                bitmap = cropImageRatio(bitmap, 4, 3);
            } else if (str.equals("3:4")) {
                bitmap = cropImageRatio(bitmap, 3, 4);
            } else {
                String[] split = str.split(":");
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("===");
                stringBuilder.append(split.length);
                Log.e("ratioArray", stringBuilder.toString());
                bitmap = cropImageRatio(bitmap, Integer.parseInt(split[0]), Integer.parseInt(split[1]));
            }
        }
        Bitmap resizeBitmap = Glide_Image_Utils.resizeImageBitmap(bitmap, (int) this.screenWidth, (int) this.screenHeight);
        if (str3.equals("created")) {
            if (str2.equals("Texture")) {
                ResizeViewLayout(AppConstants.extractTiledBitmap(this, this.curTileId, resizeBitmap, this.seekTailys), "created");
            } else {
                ResizeViewLayout(resizeBitmap, "created");
            }
        } else if (str2.equals("Texture")) {
            ResizeViewLayout(AppConstants.extractTiledBitmap(this, this.curTileId, resizeBitmap, this.seekTailys), "nonCreated");
        } else {
            ResizeViewLayout(resizeBitmap, "nonCreated");
        }
    }

    private void init() {
        ttf = AppConstants.getTextTypeface(this);
        layerContainer = findViewById(R.id.lay_container);

        footer = findViewById(R.id.footer);

        animationDrawable = (AnimationDrawable) footer.getBackground();

        // setting enter fade animation duration to 5 seconds
        animationDrawable.setEnterFadeDuration(1500);

        // setting exit fade animation duration to 2 seconds
        animationDrawable.setExitFadeDuration(1000);

        edit_reset = findViewById(R.id.edit_reset);
        edit_video_tutorial = findViewById(R.id.edit_video_tutorial);
        centerRelative = findViewById(R.id.center_rel);
        btnImgCameraSticker = findViewById(R.id.btnImgCameraSticker);
        btnImgBackground = findViewById(R.id.btnImgBackground);
        btnTakePicture = findViewById(R.id.btnTakePicture);
        btnColorBackgroundPic = findViewById(R.id.btnColorBackgroundPic);
        layRemove = findViewById(R.id.lay_remove);
        layTextMain = findViewById(R.id.lay_TextMain);
        layStkrMain = findViewById(R.id.lay_StkrMain);
        btnUpDown = findViewById(R.id.btn_up_down);
        btnUpDown1 = findViewById(R.id.btn_up_down1);
        mainRelative = findViewById(R.id.main_rel);
        backgroundImage = findViewById(R.id.background_img);
        this.backgroundBlur = findViewById(R.id.background_blur);
        stickerLayout = findViewById(R.id.txt_stkr_rel);
        this.userImage = findViewById(R.id.select_artwork);
        this.selectBackgnd = findViewById(R.id.select_backgnd);
        this.selectEffect = findViewById(R.id.select_effect);
        this.addSticker = findViewById(R.id.add_sticker);
        this.addText = findViewById(R.id.add_text);
        this.layEffects = findViewById(R.id.lay_effects);
        this.laySticker = findViewById(R.id.lay_sticker);
        this.layBackground = findViewById(R.id.lay_background);
        layHandletails = findViewById(R.id.lay_handletails);
        seekbarContainer = findViewById(R.id.seekbar_container);
        seekbarHandle = findViewById(R.id.seekbar_handle);
        shapeRelative = findViewById(R.id.shape_rel);
        seekTailys = findViewById(R.id.seek_tailys);
        this.alphaSeekbar = findViewById(R.id.alpha_seekBar);
        this.seekBar3 = findViewById(R.id.seekBar3);
        this.seekBarShadow = findViewById(R.id.seekBar_shadow);
        this.seekTextCurve = findViewById(R.id.seekTextCurve);
        this.hueSeekbar = findViewById(R.id.hue_seekBar);
        this.seekShadowBlur = findViewById(R.id.seekShadowBlur);
        this.transImgage = findViewById(R.id.trans_img);
        this.alphaSeekbar.setOnSeekBarChangeListener(this);
        this.seekBar3.setOnSeekBarChangeListener(this);
        this.seekBarShadow.setOnSeekBarChangeListener(this);
        this.hueSeekbar.setOnSeekBarChangeListener(this);
        this.seekTailys.setOnSeekBarChangeListener(this);
        this.seek = findViewById(R.id.seek);
        this.layFilter = findViewById(R.id.lay_filter);
        this.layDuplicateText = findViewById(R.id.lay_dupliText);
        this.layDuplicateStkr = findViewById(R.id.lay_dupliStkr);
        this.layEdit = findViewById(R.id.lay_edit);
        this.layDuplicateText.setOnClickListener(this);
        this.layDuplicateStkr.setOnClickListener(this);
        this.layEdit.setOnClickListener(this);
        this.seekBlur = findViewById(R.id.seek_blur);
        this.llLogo = findViewById(R.id.logo_ll);
        this.imgOK = findViewById(R.id.btn_done);
        btnLayer = findViewById(R.id.btn_layControls);

        YoYo.with(Techniques.Shake).duration(1200).repeat(2).playOn(btnLayer);

        this.layTextEdit = findViewById(R.id.lay_textEdit);
        this.verticalSeekBar = findViewById(R.id.seekBar2);
        this.horizontalPicker = findViewById(R.id.picker);
        this.horizontalPickerColor = findViewById(R.id.picker1);
        this.shadowPickerColor = findViewById(R.id.pickerShadow);
        this.pickerBg = findViewById(R.id.pickerBg);
        this.layColor = findViewById(R.id.lay_color);
        this.layHue = findViewById(R.id.lay_hue);
        this.txtControlText = findViewById(R.id.txtControlText);
        this.txtColorOpacity = findViewById(R.id.txtColorOpacity);
        this.seekLetterSpacing = findViewById(R.id.seekLetterSpacing);
        this.seekLineSpacing = findViewById(R.id.seekLineSpacing);
        this.hueSeekbar.setProgress(1);
        this.seek.setMax(255);
        this.seek.setProgress(80);
        this.seekBlur.setMax(255);
        this.seekBarShadow.setProgress(0);
        this.seekBar3.setProgress(255);
        this.seekBlur.setProgress(this.min);
        if (SDK_INT >= 16) {
            this.transImgage.setImageAlpha(this.alpha);
        } else {
            this.transImgage.setAlpha(this.alpha);
        }
        this.seekTailys.setMax(290);
        this.seekTailys.setProgress(90);
        this.seek.setOnSeekBarChangeListener(this);
        this.seekBlur.setOnSeekBarChangeListener(this);
        this.imgOK.setOnClickListener(this);
        btnLayer.setOnClickListener(this);
        this.userImage.setOnClickListener(this);
        this.selectBackgnd.setOnClickListener(this);
        this.selectEffect.setOnClickListener(this);
        this.addSticker.setOnClickListener(this);
        this.addText.setOnClickListener(this);
        this.layRemove.setOnClickListener(this);
        this.centerRelative.setOnClickListener(this);
        this.animSlideUp = AppConstants.getAnimUp(this);
        this.animSlideDown = AppConstants.getAnimDown(this);
        this.verticalSeekBar.setOnSeekBarChangeListener(this);
        this.btnImgCameraSticker.setOnClickListener(this);
        this.btnImgBackground.setOnClickListener(this);
        this.btnColorBackgroundPic.setOnClickListener(this);
        this.btnTakePicture.setOnClickListener(this);
        initRecyclerOverlay();
        VerticalStickerCategory();
        VerticalBackgroundCategory();
        unnecessaryClick();
        this.seekLetterSpacing.setOnSeekBarChangeListener(this);
        this.seekLineSpacing.setOnSeekBarChangeListener(this);
        this.seekTextCurve.setOnSeekBarChangeListener(this);
        this.seekShadowBlur.setOnSeekBarChangeListener(this);
        this.fontsShow = findViewById(R.id.fontsShow);
        this.fontsSpacing = findViewById(R.id.fontsSpacing);
        this.fontsCurve = findViewById(R.id.fontsCurve);
        this.colorShow = findViewById(R.id.colorShow);
        this.sadowShow = findViewById(R.id.sadowShow);
        this.bgShow = findViewById(R.id.bgShow);
        this.controlsShow = findViewById(R.id.controlsShow);
        this.adapter = new Fonts_Adapter(this, getResources().getStringArray(R.array.fonts_array));
        this.adapter.setSelected(0);
        ((GridView) findViewById(R.id.font_gridview)).setAdapter(this.adapter);
        this.adapter.setItemClickCallback(new On_Click_Callback<ArrayList<String>, Integer, String, Activity>() {
            public void onClickCallBack(ArrayList<String> arrayList, Integer num, String str, Activity activity) {
                PosterMAKERActivity.this.adjustTextFonts(str);
                PosterMAKERActivity.this.adapter.setSelected(num.intValue());
            }
        });
        this.adaptorTxtBg = new Text_Bg_Adapter(this, AppConstants.imageId);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.txtBg_recylr);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(this.adaptorTxtBg);
        recyclerView.addOnItemTouchListener(new RV_Item_Click_Listener(this, new OnItemClickListener() {
            public void onItemClick(View view, int i) {
                PosterMAKERActivity posterMAKERActivity = PosterMAKERActivity.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("btxt");
                stringBuilder.append(i);
                posterMAKERActivity.setBgtoTexture(stringBuilder.toString());
            }
        }));
        this.layColorOpacity = findViewById(R.id.lay_colorOpacity);
        this.layControlStkr = findViewById(R.id.lay_controlStkr);
        this.layColorOacity = findViewById(R.id.lay_colorOacity);
        this.controlsShowStkr = findViewById(R.id.controlsShowStkr);
        this.layColorOpacity.setOnClickListener(this);
        this.layControlStkr.setOnClickListener(this);
        showViewFragment();

        edit_reset.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                restartActivity();
                //  selectcolorPickerDialog(true);
            }
        });

        edit_video_tutorial.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=khuedRb9vLA")));
            }
        });
    }

    public Bitmap cropImageRatio(Bitmap bitmap, int i, int i2) {
        float width = (float) bitmap.getWidth();
        float height = (float) bitmap.getHeight();
        float f = getViewHeight(i, i2, width, height);
        float f2 = getViewWidth(i, i2, width, height);
        Bitmap createBitmap = (f2 > width || f2 >= width) ? null : Bitmap.createBitmap(bitmap, (int) ((width - f2) / 2.0f), 0, (int) f2, (int) height);
        Bitmap createBitmap2 = (f > height || f >= height) ? createBitmap : Bitmap.createBitmap(bitmap, 0, (int) ((height - f) / 2.0f), (int) width, (int) f);
        return (f2 == width && f == height) ? bitmap : createBitmap2;
    }

    private void ResizeViewLayout(Bitmap bitmap, String str) {
        this.mainRelative.getLayoutParams().width = bitmap.getWidth();
        this.mainRelative.getLayoutParams().height = bitmap.getHeight();
        this.mainRelative.postInvalidate();
        this.mainRelative.requestLayout();
        this.backgroundImage.setImageBitmap(bitmap);
        this.imgBtmap = bitmap;
        AdjustParent(bitmap.getWidth(), bitmap.getHeight());
        this.mainRelative.post(new Runnable() {
            public void run() {
                PosterMAKERActivity.this.scrollLayout.post(new Runnable() {
                    public void run() {
                        int[] iArr = new int[2];
                        PosterMAKERActivity.this.scrollLayout.getLocationOnScreen(iArr);
                        PosterMAKERActivity.this.parentY = (float) iArr[1];
                        PosterMAKERActivity.this.yAtLayoutCenter = PosterMAKERActivity.this.parentY;
                    }
                });
            }
        });
        if (this.min != 0) {
            this.backgroundBlur.setVisibility(View.VISIBLE);
        } else {
            this.backgroundBlur.setVisibility(View.GONE);
        }
        if (str.equals("created")) {
            new TwoBlurAsync(this, null, this.backgroundBlur).execute(new String[]{""});
        }
    }

    public void AdjustParent(int i, int i2) {
        RelativeLayout relativeLayout = stickerLayout;
        if (relativeLayout != null) {
            int childCount = relativeLayout.getChildCount();
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = stickerLayout.getChildAt(i3);
                if (childAt instanceof IntelligentTVAutoFit) {
                    ((IntelligentTVAutoFit) childAt).setViewWH((float) i, (float) i2);
                }
                if (childAt instanceof AutoStickerView) {
                    ((AutoStickerView) childAt).set_STViewWH((float) i, (float) i2);
                }
            }
        }
    }

    public void restartActivity() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Refreshing your templates!")
                .setCancelText("Yes")
                .setConfirmText("No")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        Intent mIntent = getIntent();
                        finish();
                        startActivity(mIntent);
                    }
                })
                .show();
    }

    private void VerticalStickerCategory() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();
        Fragment_Get_Stickers fragmentStickers = (Fragment_Get_Stickers) supportFragmentManager.findFragmentByTag("sticker_main");
        if (fragmentStickers != null) {
            beginTransaction.remove(fragmentStickers);
        }
        fragmentStickers = Fragment_Get_Stickers.newInstance();
        this.mFragments.add(new WeakReference(fragmentStickers));
        beginTransaction.add(R.id.frameContainerSticker, fragmentStickers, "sticker_main");
        try {
            beginTransaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void VerticalBackgroundCategory() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();
        Fragment_BGImg fragmentBGImages = (Fragment_BGImg) supportFragmentManager.findFragmentByTag("inback_category_frgm");
        if (fragmentBGImages != null) {
            beginTransaction.remove(fragmentBGImages);
        }
        fragmentBGImages = Fragment_BGImg.newInstance();
        this.mFragments.add(new WeakReference(fragmentBGImages));
        beginTransaction.add(R.id.frameContainerBackground, fragmentBGImages, "inback_category_frgm");
        try {
            beginTransaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initRecyclerOverlay() {
        this.adaptorOverlay = new OverLayer_Adapter(this, AppConstants.overlayArr);
        RecyclerView recyclerView = findViewById(R.id.overlay_recylr);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(this.adaptorOverlay);
        recyclerView.addOnItemTouchListener(new RV_Item_Click_Listener(this, new OnItemClickListener() {
            public void onItemClick(View view, int i) {
                PosterMAKERActivity posterMAKERActivity = PosterMAKERActivity.this;
                //  posterMAKERActivity.overlayName = ;
                posterMAKERActivity = PosterMAKERActivity.this;
                posterMAKERActivity.adjustBitmapOverlay(AppConstants.finaloverlayArr[i]);
            }
        }));
    }

    private void showViewFragment() {
        this.listFragment = new Drag_List_Fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.lay_container, this.listFragment, "fragment").commit();
    }

    @Override
    public void onClick(View view) {
        int i = 0;
        RelativeLayout relativeLayout;
        int childCount;
        switch (view.getId()) {
            case R.id.add_sticker /*2131296301*/:
                hideScroll();
                releaseIVControll();
                invisibleSlideBar();
                if (this.seekbarContainer.getVisibility() == View.VISIBLE) {
                    this.seekbarContainer.startAnimation(this.animSlideDown);
                    this.seekbarContainer.setVisibility(View.GONE);
                    this.laySticker.setVisibility(View.GONE);
                    this.imgOK.setVisibility(View.GONE);
                } else {
                    this.imgOK.setVisibility(View.VISIBLE);
                }
                if (this.laySticker.getVisibility() == View.GONE) {
                    this.laySticker.setVisibility(View.VISIBLE);
                    this.imgOK.setVisibility(View.GONE);
                } else {
                    this.imgOK.setVisibility(View.VISIBLE);
                }
                this.layEffects.setVisibility(View.GONE);
                this.layStkrMain.setVisibility(View.GONE);
                this.layBackground.setVisibility(View.GONE);
                this.layTextMain.setVisibility(View.GONE);
                addViewBotton();
                this.txtSticker.setTextColor(getResources().getColor(R.color.color_add_btn));
                return;
            case R.id.add_text /*2131296302*/:
                hideScroll();
                releaseIVControll();
                invisibleSlideBar();
                if (this.seekbarContainer.getVisibility() == View.VISIBLE) {
                    this.seekbarContainer.startAnimation(this.animSlideDown);
                    this.seekbarContainer.setVisibility(View.GONE);
                }
                this.layEffects.setVisibility(View.GONE);
                this.layStkrMain.setVisibility(View.GONE);
                this.layBackground.setVisibility(View.GONE);
                this.layTextMain.setVisibility(View.GONE);
                this.laySticker.setVisibility(View.GONE);
                addViewBotton();
                this.txtText.setTextColor(getResources().getColor(R.color.color_add_btn));
                addTextEditorDialog(null);
                return;
            case R.id.btnAlignMentFont /*2131296359*/:
                adjustLeftAlignMent();
                return;
            case R.id.btnBoldFont /*2131296360*/:
                appluBoldFonts();
                return;
            case R.id.btnCapitalFont /*2131296364*/:
                applyCapitalFont();
                return;
            case R.id.btnCenterFont /*2131296365*/:
                adjustCenterAlignMent();
                return;
            case R.id.btnColorBackgroundPic /*2131296366*/:
                selectcolorPickerDialog(false);
                return;
            case R.id.btnEditControlBg /*2131296371*/:
                finalBgPickerDialog(false);
                return;
            case R.id.btnEditControlColor /*2131296372*/:
                finalcolorPickerDialog(false);
                return;
            case R.id.btnEditControlShadowColor /*2131296373*/:
                intelligenceShadowPickerDialog(false);
                return;
            case R.id.btnImgBackground /*2131296376*/:
                chooseGalleryImagePermission();
                return;
            case R.id.btnImgCameraSticker /*2131296378*/:
                chooseGalleryPermission();
                return;
            case R.id.btnItalicFont /*2131296379*/:
                applyItalicFont();
                return;
            case R.id.btnLayoutEffect /*2131296380*/:
                this.layoutFilterView.setVisibility(View.GONE);
                this.layoutEffectView.setVisibility(View.VISIBLE);
                this.txtEffectText.setTextColor(getResources().getColor(R.color.tabtextcolor_selected));
                this.txtFilterText.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
                return;
            case R.id.btnLayoutFilter /*2131296381*/:
                this.layoutEffectView.setVisibility(View.GONE);
                this.layoutFilterView.setVisibility(View.VISIBLE);
                this.txtEffectText.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
                this.txtFilterText.setTextColor(getResources().getColor(R.color.tabtextcolor_selected));
                return;
            case R.id.btnRightFont /*2131296391*/:
                applyRightAlignMent();
                return;
            case R.id.btnShadowBottom /*2131296393*/:
                adjustBottomShadow();
                return;
            case R.id.btnShadowLeft /*2131296394*/:
                adjustLeftShadow();
                return;
            case R.id.btnShadowRight /*2131296395*/:
                adjustRightShadow();
                return;
            case R.id.btnShadowTop /*2131296397*/:
                adjustTopShadow();
                return;
            case R.id.btnTakePicture /*2131296407*/:
                chooseGalleryPermission();
                return;
            case R.id.btnUnderlineFont /*2131296408*/:
                applyUnderLineFont();
                return;
            case R.id.btn_bck1 /*2131296415*/:
                this.scrollLayout.smoothScrollTo(0, this.distanceScroll);
                return;
            case R.id.btn_bckprass /*2131296416*/:
                hideScroll();
                onBackPressed();
                return;
            case R.id.btn_done /*2131296418*/:

                invisibleSlideBar();
                hideScroll();
                this.layEffects.setVisibility(View.GONE);
                this.layStkrMain.setVisibility(View.GONE);
                this.layBackground.setVisibility(View.GONE);
                this.layTextMain.setVisibility(View.GONE);
                this.laySticker.setVisibility(View.GONE);
                releaseIVControll();
                if (this.seekbarContainer.getVisibility() == View.VISIBLE) {
                    this.seekbarContainer.startAnimation(this.animSlideDown);
                    this.seekbarContainer.setVisibility(View.GONE);
                }
                if (this.layTextMain.getVisibility() == View.VISIBLE) {
                    this.layTextMain.startAnimation(this.animSlideDown);
                    this.layTextMain.setVisibility(View.GONE);
                }
                if (this.layStkrMain.getVisibility() == View.VISIBLE) {
                    this.layStkrMain.startAnimation(this.animSlideDown);
                    this.layStkrMain.setVisibility(View.GONE);
                }
                this.guideline.setVisibility(View.GONE);
                this.bitmap = convertviewToBitmap(this.mainRelative);
                this.llLogo.setVisibility(View.VISIBLE);
                this.llLogo.setDrawingCacheEnabled(true);
                Bitmap createBitmap = Bitmap.createBitmap(this.llLogo.getDrawingCache());
                this.llLogo.setDrawingCacheEnabled(false);
                this.llLogo.setVisibility(View.INVISIBLE);
                withoutWatermark = this.bitmap;
                if (!(this.appPreferenceClass.getBoolean("isAdsDisabled", false) || this.appPreferenceClass.getBoolean("removeWatermark", false))) {
                    this.bitmap = Glide_Image_Utils.mergelogoWithView(this.bitmap, createBitmap);
                }

                requestInternal_ExternalStoragePermission(1);

/*
                final String[] temp = {"Original", "HD", "Full HD"};
                final String[] items = new String[temp.length];

                for (int num = 0; num < temp.length; num++) {
                    items[num] = temp[num] + " (" + (bitmap.getWidth() * (num + 1)) + " * " + (bitmap.getHeight() * (num + 1)) + ")";
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Select Image Quality")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                switch (which) {
                                    case 0:
                                        requestInternal_ExternalStoragePermission(1);
                                        break;
                                    case 1:
                                        requestInternal_ExternalStoragePermission(2);
                                        break;
                                    case 2:
                                        requestInternal_ExternalStoragePermission(3);
                                        break;
                                    default:
                                        requestInternal_ExternalStoragePermission(1);
                                        break;
                                }
                            }
                        });

                builder.setNegativeButton("CANCEL", null);
                builder.setIcon(getResources().getDrawable(R.drawable.app_logo, getTheme()));
                // https://www.journaldev.com/22153/android-custom-alert-dialog
                AlertDialog alertDialog = builder.create();

                alertDialog.show();

                Button button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                button.setBackgroundColor(Color.BLACK);
                button.setPadding(0, 0, 20, 0);
                button.setTextColor(Color.WHITE);
*/

                return;
            case R.id.btn_layControls /*2131296419*/:
                ScrollLayerOneTime();
                hideScroll();
                releaseIVControll();
                if (this.layTextMain.getVisibility() == View.VISIBLE) {
                    this.layTextMain.startAnimation(this.animSlideDown);
                    this.layTextMain.setVisibility(View.GONE);
                }
                if (this.layStkrMain.getVisibility() == View.VISIBLE) {
                    this.layStkrMain.startAnimation(this.animSlideDown);
                    this.layStkrMain.setVisibility(View.GONE);
                }
                if (layerContainer.getVisibility() == View.GONE) {
                    btnLayer.setVisibility(View.GONE);
                    this.listFragment.get_Layout_Child();
                    layerContainer.setVisibility(View.VISIBLE);
                    layerContainer.animate().translationX((float) layerContainer.getLeft()).setDuration(200).setInterpolator(new DecelerateInterpolator()).start();
                    return;
                }
                layerContainer.setVisibility(View.VISIBLE);
                layerContainer.animate().translationX((float) (-layerContainer.getRight())).setDuration(200).setInterpolator(new AccelerateInterpolator()).start();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        PosterMAKERActivity.layerContainer.setVisibility(View.GONE);
                        PosterMAKERActivity.btnLayer.setVisibility(View.VISIBLE);
                    }
                }, 200);
                return;
            case R.id.btn_up_down /*2131296423*/:
                this.focusedCopy = this.selectedView;
                hideScroll();
                this.layStkrMain.requestLayout();
                this.layStkrMain.postInvalidate();
                if (this.seekbarContainer.getVisibility() == View.VISIBLE) {
                    hideResContainer();
                    return;
                } else {
                    displayResContainer();
                    return;
                }

            case R.id.lay_fonts_Spacing /*2131296623*/:
                this.fontsSpacing.setVisibility(View.VISIBLE);
                this.fontsCurve.setVisibility(View.GONE);
                this.fontsShow.setVisibility(View.GONE);
                this.colorShow.setVisibility(View.GONE);
                this.sadowShow.setVisibility(View.GONE);
                this.bgShow.setVisibility(View.GONE);
                this.controlsShow.setVisibility(View.GONE);
                useControl4();
                return;
            case R.id.lay_fonts_control /*2131296624*/:
                this.fontsShow.setVisibility(View.VISIBLE);
                this.fontsSpacing.setVisibility(View.GONE);
                this.fontsCurve.setVisibility(View.GONE);
                this.colorShow.setVisibility(View.GONE);
                this.sadowShow.setVisibility(View.GONE);
                this.bgShow.setVisibility(View.GONE);
                this.controlsShow.setVisibility(View.GONE);
                useControl2();
                return;
            case R.id.lay_fonts_style /*2131296625*/:
                this.fontsSpacing.setVisibility(View.GONE);
                this.fontsCurve.setVisibility(View.GONE);
                this.fontsShow.setVisibility(View.GONE);
                this.colorShow.setVisibility(View.GONE);
                this.sadowShow.setVisibility(View.GONE);
                this.bgShow.setVisibility(View.GONE);
                this.controlsShow.setVisibility(View.GONE);
                useControl3();
                return;

            case R.id.select_backgnd /*2131296807*/:
                invisibleSlideBar();
                if (this.layBackground.getVisibility() != View.VISIBLE) {
                    this.layBackground.setVisibility(View.VISIBLE);
                    this.imgOK.setVisibility(View.GONE);
                    this.layBackground.startAnimation(animSlideUp);
                } else {
                    this.layBackground.setVisibility(View.GONE);
                    this.imgOK.setVisibility(View.VISIBLE);
                    this.layBackground.startAnimation(animSlideDown);
                }
                this.layEffects.setVisibility(View.GONE);
                this.layStkrMain.setVisibility(View.GONE);
                this.layTextMain.setVisibility(View.GONE);
                this.laySticker.setVisibility(View.GONE);
                addViewBotton();
                this.txtBG.setTextColor(getResources().getColor(R.color.color_add_btn));
                return;

            case R.id.btn_up_down1 /*2131296424*/:
                this.focusedCopy = this.selectedView;
                hideScroll();
                this.layTextMain.requestLayout();
                this.layTextMain.postInvalidate();
                if (this.layTextEdit.getVisibility() == View.VISIBLE) {
                    unhideTextResContainer();
                    return;
                } else {
                    displayTextResContainer();
                    return;
                }
            case R.id.center_rel /*2131296437*/:
                this.layEffects.setVisibility(View.GONE);
                this.layStkrMain.setVisibility(View.GONE);
                this.guideline.setVisibility(View.GONE);
                this.laySticker.setVisibility(View.GONE);
                this.layBackground.setVisibility(View.GONE);
                onViewTouchApply();
                return;
            case R.id.lay_backgnd_control /*2131296606*/:
                this.fontsShow.setVisibility(View.GONE);
                this.fontsSpacing.setVisibility(View.GONE);
                this.fontsCurve.setVisibility(View.GONE);
                this.colorShow.setVisibility(View.GONE);
                this.sadowShow.setVisibility(View.GONE);
                this.bgShow.setVisibility(View.VISIBLE);
                this.controlsShow.setVisibility(View.GONE);
                useControl8();
                return;
            case R.id.lay_colorOpacity /*2131296611*/:
                this.layColorOacity.setVisibility(View.VISIBLE);
                this.controlsShowStkr.setVisibility(View.GONE);
                this.txtControlText.setTextColor(getResources().getColor(R.color.titlecolorbtn));
                this.txtColorOpacity.setTextColor(getResources().getColor(R.color.crop_selected_color));
                return;
            case R.id.lay_colors_control /*2131296612*/:
                this.fontsShow.setVisibility(View.GONE);
                this.fontsSpacing.setVisibility(View.GONE);
                this.fontsCurve.setVisibility(View.GONE);
                this.colorShow.setVisibility(View.VISIBLE);
                this.sadowShow.setVisibility(View.GONE);
                this.bgShow.setVisibility(View.GONE);
                this.controlsShow.setVisibility(View.GONE);
                useControl6();
                return;
            case R.id.lay_controlStkr /*2131296614*/:
                this.layColorOacity.setVisibility(View.GONE);
                this.controlsShowStkr.setVisibility(View.VISIBLE);
                this.txtControlText.setTextColor(getResources().getColor(R.color.crop_selected_color));
                this.txtColorOpacity.setTextColor(getResources().getColor(R.color.titlecolorbtn));
                return;
            case R.id.lay_dupliText /*2131296618*/:
                relativeLayout = stickerLayout;
                if (relativeLayout != null) {
                    childCount = relativeLayout.getChildCount();
                    for (int i2 = 0; i2 < childCount; i2++) {
                        View childAt2 = stickerLayout.getChildAt(i2);
                        if (childAt2 instanceof IntelligentTVAutoFit) {
                            IntelligentTVAutoFit intelligentTVAutoFit = (IntelligentTVAutoFit) childAt2;
                            if (intelligentTVAutoFit.checkBorderVisibility()) {
                                IntelligentTVAutoFit intelligentTVAutoFit2 = new IntelligentTVAutoFit(this);
                                stickerLayout.addView(intelligentTVAutoFit2);
                                releaseIVControll();
                                intelligentTVAutoFit2.setTVTextInfo(intelligentTVAutoFit.getTVTextInfo(), false);
                                intelligentTVAutoFit2.setViewWH((float) this.mainRelative.getWidth(), (float) this.mainRelative.getHeight());
                                intelligentTVAutoFit2.setId(View_ID_Finder.generateViewId());
                                intelligentTVAutoFit2.setOnTV_TouchCallbackListener(this);
                                intelligentTVAutoFit2.applyBorderVisibility(true);
                            }
                        }
                    }
                }
                return;

            case R.id.lay_controls_control /*2131296615*/:
                this.fontsShow.setVisibility(View.GONE);
                this.fontsSpacing.setVisibility(View.GONE);
                this.fontsCurve.setVisibility(View.GONE);
                this.colorShow.setVisibility(View.GONE);
                this.sadowShow.setVisibility(View.GONE);
                this.bgShow.setVisibility(View.GONE);
                this.controlsShow.setVisibility(View.VISIBLE);
                useControl1();
                return;
            case R.id.lay_dupliStkr /*2131296617*/:
                relativeLayout = stickerLayout;
                if (relativeLayout != null) {
                    childCount = relativeLayout.getChildCount();
                    while (i < childCount) {
                        View childAt = stickerLayout.getChildAt(i);
                        if (childAt instanceof AutoStickerView) {
                            AutoStickerView autoStickerView = (AutoStickerView) childAt;
                            if (autoStickerView.checkBorderVisbilty()) {
                                AutoStickerView autoStickerView2 = new AutoStickerView(this);
                                autoStickerView2.set_STView_ComponentInfo(autoStickerView.get_ST_ComponentInfo());
                                autoStickerView2.setId(View_ID_Finder.generateViewId());
                                autoStickerView2.set_STViewWH((float) this.mainRelative.getWidth(), (float) this.mainRelative.getHeight());
                                stickerLayout.addView(autoStickerView2);
                                releaseIVControll();
                                autoStickerView2.setOnTouchCallbackListener(this);
                                autoStickerView2.applyBorderVisibility(true);
                            }
                        }
                        i++;
                    }
                }
                return;

            case R.id.lay_edit /*2131296619*/:
                TwoTabPrass();
                return;
            case R.id.lay_fonts_Curve /*2131296622*/:
                this.fontsSpacing.setVisibility(View.GONE);
                this.fontsCurve.setVisibility(View.VISIBLE);
                this.fontsShow.setVisibility(View.GONE);
                this.colorShow.setVisibility(View.GONE);
                this.sadowShow.setVisibility(View.GONE);
                this.bgShow.setVisibility(View.GONE);
                this.controlsShow.setVisibility(View.GONE);
                useControl5();
                return;
            case R.id.lay_remove /*2131296632*/:
                this.layEffects.setVisibility(View.GONE);
                this.layStkrMain.setVisibility(View.GONE);
                this.guideline.setVisibility(View.GONE);
                this.laySticker.setVisibility(View.GONE);
                this.layBackground.setVisibility(View.GONE);
                onViewTouchApply();
                return;
            case R.id.lay_shadow_control /*2131296634*/:
                this.fontsShow.setVisibility(View.GONE);
                this.fontsSpacing.setVisibility(View.GONE);
                this.fontsCurve.setVisibility(View.GONE);
                this.colorShow.setVisibility(View.GONE);
                this.sadowShow.setVisibility(View.VISIBLE);
                this.bgShow.setVisibility(View.GONE);
                this.controlsShow.setVisibility(View.GONE);
                useControl7();
                return;
            case R.id.select_artwork /*2131296806*/:
                hideScroll();
                releaseIVControll();
                invisibleSlideBar();
                this.layEffects.setVisibility(View.GONE);
                this.layStkrMain.setVisibility(View.GONE);
                this.layBackground.setVisibility(View.GONE);
                this.layTextMain.setVisibility(View.GONE);
                this.laySticker.setVisibility(View.GONE);
                displayPicImageDialog();
                addViewBotton();
                this.txtImage.setTextColor(getResources().getColor(R.color.color_add_btn));
                return;

            case R.id.select_effect /*2131296809*/:
                hideScroll();
                releaseIVControll();
                invisibleSlideBar();
                if (this.layEffects.getVisibility() != View.VISIBLE) {
                    this.layEffects.setVisibility(View.VISIBLE);
                    this.layEffects.startAnimation(this.animSlideUp);
                } else {
                    this.layEffects.setVisibility(View.GONE);
                    this.layEffects.startAnimation(this.animSlideDown);
                }
                this.layStkrMain.setVisibility(View.GONE);
                this.layBackground.setVisibility(View.GONE);
                this.layTextMain.setVisibility(View.GONE);
                this.laySticker.setVisibility(View.GONE);
                addViewBotton();
                this.txtEffect.setTextColor(getResources().getColor(R.color.color_add_btn));
                return;
            default:
                return;
        }
    }

    public boolean permission() {
/*
        if (SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        }
*/

        return false;
    }


    private void requestInternal_ExternalStoragePermission(int num) {
        /* Changed 26/05/2023 */
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            Dexter.withActivity(this).withPermissions("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE").withListener(new MultiplePermissionsListener() {
                public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                    if (multiplePermissionsReport.areAllPermissionsGranted()) {
                        exportBitmap(true, num);
                    }
                    if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                        displaySettingsDialog();
                    }
                }

                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                    permissionToken.continuePermissionRequest();
                }
            }).withErrorListener(new PermissionRequestErrorListener() {
                public void onError(DexterError dexterError) {
                    Toast.makeText(PosterMAKERActivity.this.getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                }
            }).onSameThread().check();
        } else {
            Dexter.withActivity(this).withPermissions("android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO").withListener(new MultiplePermissionsListener() {
                public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                    if (multiplePermissionsReport.areAllPermissionsGranted()) {
                        exportBitmap(true, num);
                    }
                    if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                        displaySettingsDialog();
                    }
                }

                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                    permissionToken.continuePermissionRequest();
                }
            }).withErrorListener(new PermissionRequestErrorListener() {
                public void onError(DexterError dexterError) {
                    Toast.makeText(PosterMAKERActivity.this.getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                }
            }).onSameThread().check();
        }
    }

    public void addViewBotton() {
        this.txtText.setTextColor(-1);
        this.txtSticker.setTextColor(-1);
        this.txtImage.setTextColor(-1);
        this.txtEffect.setTextColor(-1);
        this.txtBG.setTextColor(-1);
    }

    public void adjustRightShadow() {
        RelativeLayout relativeLayout = stickerLayout;
        if (relativeLayout != null) {
            this.leftRightShadow += 4;
            int childCount = relativeLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = stickerLayout.getChildAt(i);
                if (childAt instanceof IntelligentTVAutoFit) {
                    IntelligentTVAutoFit intelligentTVAutoFit = (IntelligentTVAutoFit) childAt;
                    if (intelligentTVAutoFit.checkBorderVisibility()) {
                        intelligentTVAutoFit.applyLeftRightShadow((float) this.leftRightShadow);
                    }
                }
            }
        }
    }

    public void adjustLeftShadow() {
        RelativeLayout relativeLayout = stickerLayout;
        if (relativeLayout != null) {
            this.leftRightShadow -= 4;
            int childCount = relativeLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = stickerLayout.getChildAt(i);
                if (childAt instanceof IntelligentTVAutoFit) {
                    IntelligentTVAutoFit intelligentTVAutoFit = (IntelligentTVAutoFit) childAt;
                    if (intelligentTVAutoFit.checkBorderVisibility()) {
                        intelligentTVAutoFit.applyLeftRightShadow((float) this.leftRightShadow);
                    }
                }
            }
        }
    }

    private void adjustBottomShadow() {
        RelativeLayout relativeLayout = stickerLayout;
        if (relativeLayout != null) {
            this.topBottomShadow += 4;
            int childCount = relativeLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = stickerLayout.getChildAt(i);
                if (childAt instanceof IntelligentTVAutoFit) {
                    IntelligentTVAutoFit intelligentTVAutoFit = (IntelligentTVAutoFit) childAt;
                    if (intelligentTVAutoFit.checkBorderVisibility()) {
                        intelligentTVAutoFit.applyTopBottomShadow((float) this.topBottomShadow);
                    }
                }
            }
        }
    }

    private void adjustTopShadow() {
        RelativeLayout relativeLayout = stickerLayout;
        if (relativeLayout != null) {
            this.topBottomShadow -= 4;
            int childCount = relativeLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = stickerLayout.getChildAt(i);
                if (childAt instanceof IntelligentTVAutoFit) {
                    IntelligentTVAutoFit intelligentTVAutoFit = (IntelligentTVAutoFit) childAt;
                    if (intelligentTVAutoFit.checkBorderVisibility()) {
                        intelligentTVAutoFit.applyTopBottomShadow((float) this.topBottomShadow);
                    }
                }
            }
        }
    }

    private void finalcolorPickerDialog(boolean z) {
        new AmbilWarnaDialog(this, this.bColor, z, new OnAmbilWarnaListener() {
            public void onOk(AmbilWarnaDialog ambilWarnaDialog, int i) {
                PosterMAKERActivity.this.updateViewColor(i);
            }

            public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {
                //  Log.e(PosterMAKERActivity.TAG, "onCancel: ");
            }
        }).show();
    }

    private void intelligenceShadowPickerDialog(boolean z) {
        new AmbilWarnaDialog(this, this.bColor, z, new OnAmbilWarnaListener() {
            public void onOk(AmbilWarnaDialog ambilWarnaDialog, int i) {
                PosterMAKERActivity.this.overrideShadow(i);
            }

            public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {
                Log.e(PosterMAKERActivity.TAG, "onCancel: ");
            }
        }).show();
    }

    private void finalBgPickerDialog(boolean z) {
        new AmbilWarnaDialog(this, this.bColor, z, new OnAmbilWarnaListener() {
            public void onOk(AmbilWarnaDialog ambilWarnaDialog, int i) {
                PosterMAKERActivity.this.replaceBgcolor(i);
            }

            public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {
                Log.e(PosterMAKERActivity.TAG, "onCancel: ");
            }
        }).show();
    }

    private void displayResContainer() {
        this.btnUpDown.animate().setDuration(500).start();
        this.btnUpDown.setBackgroundResource(R.drawable.text_drag_down);
        this.seekbarContainer.setVisibility(View.VISIBLE);
        this.layStkrMain.startAnimation(this.animSlideUp);
        this.layStkrMain.requestLayout();
        this.layStkrMain.postInvalidate();
        this.layStkrMain.post(new Runnable() {
            public void run() {
                PosterMAKERActivity posterMAKERActivity = PosterMAKERActivity.this;
                posterMAKERActivity.dragStickerView(posterMAKERActivity.selectedView);
            }
        });
    }

    private void hideResContainer() {
        this.btnUpDown.animate().setDuration(500).start();
        this.btnUpDown.setBackgroundResource(R.drawable.text_drag_up);
        this.seekbarContainer.setVisibility(View.GONE);
        this.layStkrMain.startAnimation(this.animSlideDown);
        this.layStkrMain.requestLayout();
        this.layStkrMain.postInvalidate();
        this.layStkrMain.post(new Runnable() {
            public void run() {
                PosterMAKERActivity posterMAKERActivity = PosterMAKERActivity.this;
                posterMAKERActivity.dragStickerView(posterMAKERActivity.selectedView);
            }
        });
    }

    private void displayTextResContainer() {
        this.btnUpDown1.animate().setDuration(500).start();
        this.btnUpDown1.setBackgroundResource(R.drawable.text_drag_down);
        this.layTextEdit.setVisibility(View.VISIBLE);
        this.layTextMain.startAnimation(this.animSlideUp);
        this.layTextMain.requestLayout();
        this.layTextMain.postInvalidate();
        this.layTextMain.post(new Runnable() {
            public void run() {
                PosterMAKERActivity posterMAKERActivity = PosterMAKERActivity.this;
                posterMAKERActivity.dragStickerView(posterMAKERActivity.selectedView);
            }
        });
    }

    private void unhideTextResContainer() {
        this.btnUpDown1.animate().setDuration(500).start();
        this.btnUpDown1.setBackgroundResource(R.drawable.text_drag_up);
        this.layTextMain.startAnimation(this.animSlideDown);
        this.layTextEdit.setVisibility(View.GONE);
        this.layTextMain.requestLayout();
        this.layTextMain.postInvalidate();
        this.layTextMain.post(new Runnable() {
            public void run() {
                PosterMAKERActivity posterMAKERActivity = PosterMAKERActivity.this;
                posterMAKERActivity.dragStickerView(posterMAKERActivity.selectedView);
            }
        });
    }


    public void adjustTextFonts(String str) {
        this.font_Name = str;
        RelativeLayout relativeLayout = stickerLayout;
        if (relativeLayout != null) {
            int childCount = relativeLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = stickerLayout.getChildAt(i);
                if (childAt instanceof IntelligentTVAutoFit) {
                    IntelligentTVAutoFit intelligentTVAutoFit = (IntelligentTVAutoFit) childAt;
                    if (intelligentTVAutoFit.checkBorderVisibility()) {
                        intelligentTVAutoFit.setTextFont(str);
                    }
                }
            }
        }
    }

    private void adustLetterApacing() {
        RelativeLayout relativeLayout = stickerLayout;
        if (relativeLayout != null) {
            int childCount = relativeLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = stickerLayout.getChildAt(i);
                if (childAt instanceof IntelligentTVAutoFit) {
                    IntelligentTVAutoFit intelligentTVAutoFit = (IntelligentTVAutoFit) childAt;
                    if (intelligentTVAutoFit.checkBorderVisibility()) {
                        intelligentTVAutoFit.applyLetterSpacing(this.letterSpacing);
                    }
                }
            }
        }
    }

    public void setBgtoTexture(String str) {
        if (stickerLayout != null) {
            getResources().getIdentifier(str, "drawable", getPackageName());
            int childCount = stickerLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = stickerLayout.getChildAt(i);
                if (childAt instanceof IntelligentTVAutoFit) {
                    IntelligentTVAutoFit intelligentTVAutoFit = (IntelligentTVAutoFit) childAt;
                    if (intelligentTVAutoFit.checkBorderVisibility()) {
                        intelligentTVAutoFit.setTVBgDrawable(str);
                        intelligentTVAutoFit.setTVBgAlpha(this.seekBar3.getProgress());
                        this.backgroundColor = 0;
                        ((IntelligentTVAutoFit) stickerLayout.getChildAt(i)).getTVTextInfo().setTV_BG_DRAWABLE(str);
                        this.backgroundDrawable = intelligentTVAutoFit.getTVBgDrawable();
                        this.backgroundAlpha = this.seekBar3.getProgress();
                    }
                }
            }
        }
    }

    private void adjustLineApacing() {
        RelativeLayout relativeLayout = stickerLayout;
        if (relativeLayout != null) {
            int childCount = relativeLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = stickerLayout.getChildAt(i);
                if (childAt instanceof IntelligentTVAutoFit) {
                    IntelligentTVAutoFit intelligentTVAutoFit = (IntelligentTVAutoFit) childAt;
                    if (intelligentTVAutoFit.checkBorderVisibility()) {
                        intelligentTVAutoFit.applyLineSpacing(lineSpacing);
                    }
                }
            }
        }
    }

    private void appluBoldFonts() {
        RelativeLayout relativeLayout = stickerLayout;
        if (relativeLayout != null) {
            int childCount = relativeLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = stickerLayout.getChildAt(i);
                if (childAt instanceof IntelligentTVAutoFit) {
                    IntelligentTVAutoFit intelligentTVAutoFit = (IntelligentTVAutoFit) childAt;
                    if (intelligentTVAutoFit.checkBorderVisibility()) {
                        intelligentTVAutoFit.useBoldFont();
                    }
                }
            }
        }
    }

    private void applyCapitalFont() {
        RelativeLayout relativeLayout = stickerLayout;
        if (relativeLayout != null) {
            int childCount = relativeLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = stickerLayout.getChildAt(i);
                if (childAt instanceof IntelligentTVAutoFit) {
                    IntelligentTVAutoFit intelligentTVAutoFit = (IntelligentTVAutoFit) childAt;
                    if (intelligentTVAutoFit.checkBorderVisibility()) {
                        intelligentTVAutoFit.useCapitalFont();
                    }
                }
            }
        }
    }

    private void applyUnderLineFont() {
        RelativeLayout relativeLayout = stickerLayout;
        if (relativeLayout != null) {
            int childCount = relativeLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = stickerLayout.getChildAt(i);
                if (childAt instanceof IntelligentTVAutoFit) {
                    IntelligentTVAutoFit intelligentTVAutoFit = (IntelligentTVAutoFit) childAt;
                    if (intelligentTVAutoFit.checkBorderVisibility()) {
                        intelligentTVAutoFit.useUnderLineFont();
                    }
                }
            }
        }
    }

    private void applyItalicFont() {
        RelativeLayout relativeLayout = stickerLayout;
        if (relativeLayout != null) {
            int childCount = relativeLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = stickerLayout.getChildAt(i);
                if (childAt instanceof IntelligentTVAutoFit) {
                    IntelligentTVAutoFit intelligentTVAutoFit = (IntelligentTVAutoFit) childAt;
                    if (intelligentTVAutoFit.checkBorderVisibility()) {
                        intelligentTVAutoFit.useItalicFont();
                    }
                }
            }
        }
    }

    private void adjustLeftAlignMent() {
        RelativeLayout relativeLayout = stickerLayout;
        if (relativeLayout != null) {
            int childCount = relativeLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = stickerLayout.getChildAt(i);
                if (childAt instanceof IntelligentTVAutoFit) {
                    IntelligentTVAutoFit intelligentTVAutoFit = (IntelligentTVAutoFit) childAt;
                    if (intelligentTVAutoFit.checkBorderVisibility()) {
                        intelligentTVAutoFit.useLeftAlignMent();
                    }
                }
            }
        }
    }

    private void adjustCenterAlignMent() {
        RelativeLayout relativeLayout = stickerLayout;
        if (relativeLayout != null) {
            int childCount = relativeLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = stickerLayout.getChildAt(i);
                if (childAt instanceof IntelligentTVAutoFit) {
                    IntelligentTVAutoFit intelligentTVAutoFit = (IntelligentTVAutoFit) childAt;
                    if (intelligentTVAutoFit.checkBorderVisibility()) {
                        intelligentTVAutoFit.useCenterAlignMent();
                    }
                }
            }
        }
    }

    private void applyRightAlignMent() {
        RelativeLayout relativeLayout = stickerLayout;
        if (relativeLayout != null) {
            int childCount = relativeLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = stickerLayout.getChildAt(i);
                if (childAt instanceof IntelligentTVAutoFit) {
                    IntelligentTVAutoFit intelligentTVAutoFit = (IntelligentTVAutoFit) childAt;
                    if (intelligentTVAutoFit.checkBorderVisibility()) {
                        intelligentTVAutoFit.useRightAlignMent();
                    }
                }
            }
        }
    }

    public void adjustBitmapOverlay(int i) {
        this.layFilter.setVisibility(View.VISIBLE);
        this.transImgage.setVisibility(View.VISIBLE);
        try {
            this.transImgage.setImageBitmap(BitmapFactory.decodeResource(getResources(), i));
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            Options options = new Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(getResources(), i, options);
            Options options2 = new Options();
            options2.inSampleSize = Glide_Image_Utils.extract_ClosestResampleSize(options.outWidth, options.outHeight, this.mainRelative.getWidth() < this.mainRelative.getHeight() ? this.mainRelative.getWidth() : this.mainRelative.getHeight());
            options.inJustDecodeBounds = false;
            this.transImgage.setImageBitmap(BitmapFactory.decodeResource(getResources(), i, options2));
        }
    }

    public void updateViewColor(int i) {
        RelativeLayout relativeLayout = stickerLayout;
        if (relativeLayout != null) {
            int childCount = relativeLayout.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = stickerLayout.getChildAt(i2);
                if (childAt instanceof IntelligentTVAutoFit) {
                    IntelligentTVAutoFit intelligentTVAutoFit = (IntelligentTVAutoFit) childAt;
                    if (intelligentTVAutoFit.checkBorderVisibility()) {
                        intelligentTVAutoFit.setTVTextColor(i);
                        this.textColor = i;
                        this.textColorSet = i;
                        this.horizontalPicker.setSelectedColor(i);
                    }
                }
                if (childAt instanceof AutoStickerView) {
                    AutoStickerView autoStickerView = (AutoStickerView) childAt;
                    if (autoStickerView.checkBorderVisbilty()) {
                        autoStickerView.setSTColor(i);
                        this.stkrColorSet = i;
                        this.horizontalPickerColor.setSelectedColor(i);
                    }
                }
            }
        }
    }


    public void overrideShadow(int i) {
        int childCount = stickerLayout.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = stickerLayout.getChildAt(i2);
            if (childAt instanceof IntelligentTVAutoFit) {
                IntelligentTVAutoFit intelligentTVAutoFit = (IntelligentTVAutoFit) childAt;
                if (intelligentTVAutoFit.checkBorderVisibility()) {
                    intelligentTVAutoFit.applyTextShadowColor(i);
                    this.textShadowColor = i;
                }
            }
        }
    }


    public void replaceBgcolor(int i) {
        int childCount = stickerLayout.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = stickerLayout.getChildAt(i2);
            if (childAt instanceof IntelligentTVAutoFit) {
                IntelligentTVAutoFit intelligentTVAutoFit = (IntelligentTVAutoFit) childAt;
                if (intelligentTVAutoFit.checkBorderVisibility()) {
                    intelligentTVAutoFit.setTVBgAlpha(this.seekBar3.getProgress());
                    intelligentTVAutoFit.setBackgroundColor(i);
                    this.backgroundColor = i;
                    this.backgroundDrawable = "0";
                }
            }
        }
    }


    public void replaceStickerPosition(String str) {
        int childCount = stickerLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = stickerLayout.getChildAt(i);
            if (childAt instanceof IntelligentTVAutoFit) {
                IntelligentTVAutoFit intelligentTVAutoFit = (IntelligentTVAutoFit) childAt;
                if (intelligentTVAutoFit.checkBorderVisibility()) {
                    if (str.equals("incrX")) {
                        intelligentTVAutoFit.incrX();
                    }
                    if (str.equals("decX")) {
                        intelligentTVAutoFit.decX();
                    }
                    if (str.equals("incrY")) {
                        intelligentTVAutoFit.incrY();
                    }
                    if (str.equals("decY")) {
                        intelligentTVAutoFit.decY();
                    }
                }
            }
            if (childAt instanceof AutoStickerView) {
                AutoStickerView autoStickerView = (AutoStickerView) childAt;
                if (autoStickerView.checkBorderVisbilty()) {
                    if (str.equals("incrX")) {
                        autoStickerView.incrX();
                    }
                    if (str.equals("decX")) {
                        autoStickerView.decX();
                    }
                    if (str.equals("incrY")) {
                        autoStickerView.incrY();
                    }
                    if (str.equals("decY")) {
                        autoStickerView.decY();
                    }
                }
            }
            float height = (float) this.mainRelative.getHeight();
            int x = (int) (childAt.getX() + ((float) (childAt.getWidth() / 2)));
            int y = (int) (childAt.getY() + ((float) (childAt.getHeight() / 2)));
            float f = (float) x;
            float width = ((float) this.mainRelative.getWidth()) / 2.0f;
            Object obj = (f <= width - 1.0f || f >= width + 1.0f) ? null : 1;
            float f2 = (float) y;
            height /= 2.0f;
            Object obj2 = (f2 <= height - 1.0f || f2 >= height + 1.0f) ? null : 1;
            if (obj != null && obj2 != null) {
                this.guideline.setCenterValues(true, true);
            } else if (obj != null) {
                this.guideline.setCenterValues(true, false);
            } else if (obj2 != null) {
                this.guideline.setCenterValues(false, true);
            } else {
                this.guideline.setCenterValues(false, false);
            }
        }
    }

    private boolean offViewAll() {
        this.viewAllFrame.removeAllViews();
        this.viewAllFrame.setVisibility(View.GONE);
        return false;
    }

    public void finalSaveComponent1(long j, DB_Handler DB_Handler) {
        int childCount = stickerLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = stickerLayout.getChildAt(i);
            if (childAt instanceof IntelligentTVAutoFit) {
                TV_Info_Adapter textViewInfoAdapter = ((IntelligentTVAutoFit) childAt).getTVTextInfo();
                textViewInfoAdapter.setTV_TEMPLATE_ID((int) j);
                textViewInfoAdapter.setTV_ORDER(i);
                textViewInfoAdapter.setTV_TYPE("TEXT");
                DB_Handler.add_Text_Row(textViewInfoAdapter);
            } else {
                finalSaveShapeSticker(j, i, TYPE_STICKER, DB_Handler);
            }
        }
    }

    public void finalSaveShapeSticker(long j, int i, int i2, DB_Handler DB_Handler) {
        ViewElementInfo componentInfo = ((AutoStickerView) stickerLayout.getChildAt(i)).get_ST_ComponentInfo();
        componentInfo.setTEMPLATE_ID((int) j);
        componentInfo.setTYPE("STICKER");
        componentInfo.setORDER(i);
        DB_Handler.add_Component_Info_Row(componentInfo);
    }

    public void addTextEditorDialog(final TV_Info_Adapter textViewInfoAdapter) {
        final Dialog dialog = new Dialog(this, R.style.ThemeWithCorners);
        dialog.setContentView(R.layout.layout_dialog_add_text);
        dialog.setCancelable(false);
        TextView textView = dialog.findViewById(R.id.txtTitle);
        final AutoFitEditText autoFitEditText = dialog.findViewById(R.id.auto_fit_edit_text);
        Button button = dialog.findViewById(R.id.btnCancelDialog);
        Button button2 = dialog.findViewById(R.id.btnAddTextSDialog);
        if (textViewInfoAdapter != null) {
            autoFitEditText.setText(textViewInfoAdapter.getTEXT());
        } else {
            autoFitEditText.setText("");
        }
        textView.setTypeface(adjustFontBold());
        autoFitEditText.setTypeface(applyFontNormal());
        button.setTypeface(applyFontNormal());
        button2.setTypeface(applyFontNormal());
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
                editMode = false;
            }
        });
        button2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    if (autoFitEditText.getText().toString().trim().length() > 0) {
                        String replace = autoFitEditText.getText().toString().replace("\n", " ");
                        if (PosterMAKERActivity.this.editMode) {

                            if (textViewInfoAdapter != null) {
                                if (replace != null && !replace.isEmpty()) {
                                    textViewInfoAdapter.setTEXT(replace);
                                }
                            }

                            try {
                                if (textViewInfoAdapter != null) {
                                    if (replace != null && !replace.isEmpty()) {
                                        textViewInfoAdapter.setTEXT(replace);
                                    }
                                    textViewInfoAdapter.setTV_FONT_NAME(textViewInfoAdapter.get_TV_FONT_NAME());
                                    textViewInfoAdapter.setTEXT_COLOR(textViewInfoAdapter.getTEXT_COLOR());
                                    textViewInfoAdapter.setTEXT_ALPHA(textViewInfoAdapter.getTEXT_ALPHA());
                                    textViewInfoAdapter.setTV_SHADOW_COLOR(textViewInfoAdapter.getTV_SHADOW_COLOR());
                                    textViewInfoAdapter.setTVSHADOW_PROG(textViewInfoAdapter.getTV_SHADOW_PROG());
                                    textViewInfoAdapter.setTV_BG_COLOR(textViewInfoAdapter.getTV_BG_COLOR());
                                    textViewInfoAdapter.setTV_BG_DRAWABLE(textViewInfoAdapter.getTV_BG_DRAWABLE());
                                    textViewInfoAdapter.setTV_BG_ALPHA(textViewInfoAdapter.getTV_BG_ALPHA());
                                    textViewInfoAdapter.setTV_ROTATION(textViewInfoAdapter.get_TV_ROTATION());
                                    textViewInfoAdapter.setFIELD_TWO("");
                                    textViewInfoAdapter.setTVPOS_X(textViewInfoAdapter.getTVPOS_X());
                                    textViewInfoAdapter.setTVPOS_Y(textViewInfoAdapter.getTVPOS_Y());
                                    textViewInfoAdapter.setTVWIDTH(textViewInfoAdapter.getTVWIDTH());
                                    textViewInfoAdapter.setTVHEIGHT(textViewInfoAdapter.getTVHEIGHT());
                                } else {
                                    if (textViewInfoAdapter != null) {
                                        textViewInfoAdapter.setTV_FONT_NAME(PosterMAKERActivity.this.font_Name);
                                    }
                                    textViewInfoAdapter.setTEXT_COLOR(-16777216);
                                    textViewInfoAdapter.setTEXT_ALPHA(100);
                                    textViewInfoAdapter.setTV_SHADOW_COLOR(-16777216);
                                    textViewInfoAdapter.setTVSHADOW_PROG(0);
                                    textViewInfoAdapter.setTV_BG_COLOR(-16777216);
                                    textViewInfoAdapter.setTV_BG_DRAWABLE("0");
                                    textViewInfoAdapter.setTV_BG_ALPHA(0);
                                    textViewInfoAdapter.setTV_ROTATION(0.0f);
                                    textViewInfoAdapter.setFIELD_TWO("");
                                    textViewInfoAdapter.setTVPOS_X((float) ((PosterMAKERActivity.stickerLayout.getWidth() / 2) - Glide_Image_Utils.convertDpToPx(PosterMAKERActivity.this, 100.0f)));
                                    textViewInfoAdapter.setTVPOS_Y((float) ((PosterMAKERActivity.stickerLayout.getHeight() / 2) - Glide_Image_Utils.convertDpToPx(PosterMAKERActivity.this, 100.0f)));
                                    textViewInfoAdapter.setTVWIDTH(Glide_Image_Utils.convertDpToPx(PosterMAKERActivity.this, 200.0f));
                                    textViewInfoAdapter.setTVHEIGHT(Glide_Image_Utils.convertDpToPx(PosterMAKERActivity.this, 200.0f));
                                }
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                                textViewInfoAdapter.setTV_FONT_NAME(PosterMAKERActivity.this.font_Name);
                                textViewInfoAdapter.setTEXT_COLOR(-16777216);
                                textViewInfoAdapter.setTEXT_ALPHA(100);
                                textViewInfoAdapter.setTV_SHADOW_COLOR(-16777216);
                                textViewInfoAdapter.setTVSHADOW_PROG(0);
                                textViewInfoAdapter.setTV_BG_COLOR(-16777216);
                                textViewInfoAdapter.setTV_BG_DRAWABLE("0");
                                textViewInfoAdapter.setTV_BG_ALPHA(0);
                                textViewInfoAdapter.setTV_ROTATION(0.0f);
                                textViewInfoAdapter.setFIELD_TWO("");
                                textViewInfoAdapter.setTVPOS_X((float) ((PosterMAKERActivity.stickerLayout.getWidth() / 2) - Glide_Image_Utils.convertDpToPx(PosterMAKERActivity.this, 100.0f)));
                                textViewInfoAdapter.setTVPOS_Y((float) ((PosterMAKERActivity.stickerLayout.getHeight() / 2) - Glide_Image_Utils.convertDpToPx(PosterMAKERActivity.this, 100.0f)));
                                textViewInfoAdapter.setTVWIDTH(Glide_Image_Utils.convertDpToPx(PosterMAKERActivity.this, 200.0f));
                                textViewInfoAdapter.setTVHEIGHT(Glide_Image_Utils.convertDpToPx(PosterMAKERActivity.this, 200.0f));
                            }
                            int childCount = PosterMAKERActivity.stickerLayout.getChildCount();
                            for (int i = 0; i < childCount; i++) {
                                View childAt = PosterMAKERActivity.stickerLayout.getChildAt(i);
                                if (childAt instanceof IntelligentTVAutoFit) {
                                    IntelligentTVAutoFit intelligentTVAutoFit = (IntelligentTVAutoFit) childAt;
                                    if (intelligentTVAutoFit.checkBorderVisibility()) {
                                        intelligentTVAutoFit.setTVTextInfo(textViewInfoAdapter, false);
                                        intelligentTVAutoFit.applyBorderVisibility(true);
                                        PosterMAKERActivity.this.editMode = false;
                                    }
                                }
                            }
                        } else {
                            TV_Info_Adapter textViewInfoAdapter = new TV_Info_Adapter();
                            if (replace != null && !replace.isEmpty()) {
                                textViewInfoAdapter.setTEXT(replace);
                            }
                            textViewInfoAdapter.setTV_FONT_NAME(PosterMAKERActivity.this.font_Name);
                            textViewInfoAdapter.setTEXT_COLOR(-16777216);
                            textViewInfoAdapter.setTEXT_ALPHA(100);
                            textViewInfoAdapter.setTV_SHADOW_COLOR(-16777216);
                            textViewInfoAdapter.setTVSHADOW_PROG(0);
                            textViewInfoAdapter.setTV_BG_COLOR(-16777216);
                            textViewInfoAdapter.setTV_BG_DRAWABLE("0");
                            textViewInfoAdapter.setTV_BG_ALPHA(0);
                            textViewInfoAdapter.setTV_ROTATION(0.0f);
                            textViewInfoAdapter.setFIELD_TWO("");
                            textViewInfoAdapter.setTVPOS_X((float) ((PosterMAKERActivity.stickerLayout.getWidth() / 2) - Glide_Image_Utils.convertDpToPx(PosterMAKERActivity.this, 100.0f)));
                            textViewInfoAdapter.setTVPOS_Y((float) ((PosterMAKERActivity.stickerLayout.getHeight() / 2) - Glide_Image_Utils.convertDpToPx(PosterMAKERActivity.this, 100.0f)));
                            textViewInfoAdapter.setTVWIDTH(Glide_Image_Utils.convertDpToPx(PosterMAKERActivity.this, 200.0f));
                            textViewInfoAdapter.setTVHEIGHT(Glide_Image_Utils.convertDpToPx(PosterMAKERActivity.this, 200.0f));
                            try {
                                PosterMAKERActivity.this.verticalSeekBar.setProgress(100);
                                PosterMAKERActivity.this.seekBarShadow.setProgress(0);
                                PosterMAKERActivity.this.seekBar3.setProgress(255);
                                IntelligentTVAutoFit intelligentTVAutoFit2 = new IntelligentTVAutoFit(PosterMAKERActivity.this);
                                PosterMAKERActivity.stickerLayout.addView(intelligentTVAutoFit2);
                                intelligentTVAutoFit2.setTVFromAddText(true);
                                intelligentTVAutoFit2.setTVTextInfo(textViewInfoAdapter, false);
                                intelligentTVAutoFit2.setViewWH((float) PosterMAKERActivity.this.mainRelative.getWidth(), (float) PosterMAKERActivity.this.mainRelative.getHeight());
                                intelligentTVAutoFit2.setId(View_ID_Finder.generateViewId() - 1);
                                intelligentTVAutoFit2.setOnTV_TouchCallbackListener(PosterMAKERActivity.this);
                                intelligentTVAutoFit2.applyBorderVisibility(true);
                            } catch (ArrayIndexOutOfBoundsException e2) {
                                e2.printStackTrace();
                            }
                        }
                        if (PosterMAKERActivity.this.layTextMain.getVisibility() == View.GONE) {
                            PosterMAKERActivity.this.layTextMain.setVisibility(View.VISIBLE);
                            PosterMAKERActivity.this.layTextMain.startAnimation(PosterMAKERActivity.this.animSlideUp);
                        }
                        dialog.dismiss();
                        PosterMAKERActivity.this.onceMOreOption();
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(PosterMAKERActivity.this, "Please enter text here.", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    public void onViewTouchApply() {
        hideScroll();
        if (this.layStkrMain.getVisibility() == View.VISIBLE) {
            this.layStkrMain.startAnimation(this.animSlideDown);
            this.layStkrMain.setVisibility(View.GONE);
        }
        if (this.layTextMain.getVisibility() == View.VISIBLE) {
            this.layTextMain.startAnimation(this.animSlideDown);
            this.layTextMain.setVisibility(View.GONE);
        }
        if (this.showtailsSeek) {
            this.layHandletails.setVisibility(View.VISIBLE);
        }
        if (this.seekbarContainer.getVisibility() == View.GONE) {
            this.seekbarContainer.clearAnimation();
            this.layTextMain.clearAnimation();
            this.seekbarContainer.setVisibility(View.VISIBLE);
            this.seekbarContainer.startAnimation(this.animSlideUp);
        }
        this.layStkrMain.clearAnimation();
        this.layTextMain.clearAnimation();
        releaseIVControll();
        invisibleSlideBar();
    }


    private void applyDrawable(String str, String str2) {
        this.colorType = str;
        if (str.equals("white")) {
            this.layColor.setVisibility(View.VISIBLE);
            this.layHue.setVisibility(View.GONE);
        } else {
            this.layColor.setVisibility(View.GONE);
            this.layHue.setVisibility(View.VISIBLE);
        }
        this.layEffects.setVisibility(View.GONE);
        this.layTextMain.setVisibility(View.GONE);
        applySticker(str2, "", null);
    }


    public void applySticker(String str, String str2, Bitmap bitmap) {
        if (this.layStkrMain.getVisibility() == View.GONE) {
            this.layStkrMain.setVisibility(View.VISIBLE);
            this.layStkrMain.startAnimation(this.animSlideUp);
        }
        if (this.colorType.equals("white")) {
            this.layColor.setVisibility(View.VISIBLE);
            this.layHue.setVisibility(View.GONE);
        } else {
            this.layColor.setVisibility(View.GONE);
            this.layHue.setVisibility(View.VISIBLE);
        }
        this.hueSeekbar.setProgress(1);
        releaseIVControll();
        ViewElementInfo viewElementInfo = new ViewElementInfo();
        viewElementInfo.setPOS_X((float) ((this.mainRelative.getWidth() / 2) - Glide_Image_Utils.convertDpToPx(this, 70.0f)));
        viewElementInfo.setPOS_Y((float) ((this.mainRelative.getHeight() / 2) - Glide_Image_Utils.convertDpToPx(this, 70.0f)));
        viewElementInfo.setWIDTH(Glide_Image_Utils.convertDpToPx(this, 140.0f));
        viewElementInfo.setHEIGHT(Glide_Image_Utils.convertDpToPx(this, 140.0f));
        viewElementInfo.setROTATION(0.0f);
        viewElementInfo.setRES_ID(str);
        viewElementInfo.setBITMAP(bitmap);
        viewElementInfo.setCOLORTYPE(this.colorType);
        viewElementInfo.setTYPE("STICKER");
        viewElementInfo.setSTC_OPACITY(255);
        viewElementInfo.setSTC_COLOR(0);
        viewElementInfo.setSTKR_PATH(str2);
        viewElementInfo.setSTC_HUE(this.hueSeekbar.getProgress());
        viewElementInfo.setFIELD_TWO("0,0");
        AutoStickerView autoStickerView = new AutoStickerView(this);
        autoStickerView.optimizeSTScreen(this.screenWidth, this.screenHeight);
        autoStickerView.set_STViewWH((float) this.mainRelative.getWidth(), (float) this.mainRelative.getHeight());
        autoStickerView.setFromAddText(true);
        autoStickerView.set_STView_ComponentInfo(viewElementInfo);
        autoStickerView.setId(View_ID_Finder.generateViewId());

        autoStickerView.isMultiTouchEnabled = autoStickerView.applyDefaultTouchListener(true);

        stickerLayout.addView(autoStickerView);
        autoStickerView.setOnTouchCallbackListener(this);
        autoStickerView.applyBorderVisibility(true);
    }

    private Bitmap convertviewToBitmap(View view) {
        try {
            Bitmap createBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Config.ARGB_8888);
            view.draw(new Canvas(createBitmap));
            return createBitmap;
        } finally {
            view.destroyDrawingCache();
        }
    }


    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (seekBar.getId() == R.id.seek_tailys) {
            if (this.min != 0) {
                this.backgroundBlur.setVisibility(View.VISIBLE);
            } else {
                this.backgroundBlur.setVisibility(View.GONE);
            }
            new Blur_Task_Async(this, this.imgBtmap, this.backgroundBlur).execute(new String[]{""});
        }
    }

    private void setTilesBG(int i) {
        if (i != 0) {
            applyImageBitmapAndResizeLayout1(AppConstants.extractTiledBitmap((Activity) this, i, this.imgBtmap, this.seekTailys));
        }
    }

    private void applyImageBitmapAndResizeLayout1(Bitmap bitmap) {
        this.mainRelative.getLayoutParams().width = bitmap.getWidth();
        this.mainRelative.getLayoutParams().height = bitmap.getHeight();
        this.mainRelative.postInvalidate();
        this.mainRelative.requestLayout();
        this.backgroundImage.setImageBitmap(bitmap);
        this.imgBtmap = bitmap;
    }

    private void ontouchDown(View view, String str) {
        this.selectedView = view;
        if (str.equals("hideboder")) {
            releaseIVControll();
        }
        this.leftRightShadow = 0;
        this.topBottomShadow = 0;
        this.selectedView = view;
        invisibleSlideBar();
        if (view instanceof AutoStickerView) {
            this.layEffects.setVisibility(View.GONE);
            this.layTextMain.setVisibility(View.GONE);
            this.layStkrMain.setVisibility(View.GONE);
            AutoStickerView autoStickerView = (AutoStickerView) view;
            this.stkrColorSet = autoStickerView.getSTColor();
            this.horizontalPickerColor.setSelectedColor(this.stkrColorSet);
            this.alphaSeekbar.setProgress(autoStickerView.getSTAlphaProg());
            this.hueSeekbar.setProgress(autoStickerView.get_sticker_HueProg());
        }
        if (view instanceof IntelligentTVAutoFit) {
            this.layEffects.setVisibility(View.GONE);
            this.layStkrMain.setVisibility(View.GONE);
            this.layTextMain.setVisibility(View.GONE);
            IntelligentTVAutoFit intelligentTVAutoFit = (IntelligentTVAutoFit) view;
            this.textColorSet = intelligentTVAutoFit.getTVTextColor();
            this.horizontalPicker.setSelectedColor(this.textColorSet);
            this.font_Name = intelligentTVAutoFit.getTextFontName();
            this.textColor = intelligentTVAutoFit.getTVTextColor();
            this.textShadowColor = intelligentTVAutoFit.checkTextShadowColor();
            this.textShadowProg = intelligentTVAutoFit.getTVTextShadowProg();
            this.textAlpha = intelligentTVAutoFit.getTVTextAlpha();
            this.backgroundDrawable = intelligentTVAutoFit.getTVBgDrawable();
            this.backgroundAlpha = intelligentTVAutoFit.getTVBgAlpha();
            this.rotation = view.getRotation();
            this.backgroundColor = intelligentTVAutoFit.getTVBgColor();
            if (this.backgroundDrawable.equals("0") || this.backgroundAlpha == 0) {
                this.adaptorTxtBg.setSelected(500);
            } else {
                this.adaptorTxtBg.setSelected(Integer.parseInt(this.backgroundDrawable.replace("btxt", "")));
            }
            this.verticalSeekBar.setProgress(this.textAlpha);
            this.seekBarShadow.setProgress(this.textShadowProg);
            this.seekBar3.setProgress(this.backgroundAlpha);
        }
        if (this.guideline.getVisibility() == View.GONE) {
            this.guideline.setVisibility(View.VISIBLE);
        }
    }

    public void ScrollLayerOneTime() {
        if (this.appPreferenceClass.getInt(AppConstants.onTimeLayerScroll, 0) == 0) {
            this.appPreferenceClass.putInt(AppConstants.onTimeLayerScroll, 1);
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    AppConstants.displayScrollLayerDialog(Drag_List_Fragment.HintView, PosterMAKERActivity.this);
                }
            }, 1000);
        }
    }


    private void invisibleSlideBar() {
        if (layerContainer.getVisibility() == View.VISIBLE) {
            layerContainer.animate().translationX((float) (-layerContainer.getRight())).setDuration(200).setInterpolator(new AccelerateInterpolator()).start();
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    PosterMAKERActivity.layerContainer.setVisibility(View.GONE);
                    PosterMAKERActivity.btnLayer.setVisibility(View.VISIBLE);
                }
            }, 200);
        }
    }


    public void onceMOreOption() {
        if (this.appPreferenceClass.getInt(AppConstants.onTimeHint, 0) == 0) {
            this.appPreferenceClass.putInt(AppConstants.onTimeHint, 1);
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    AppConstants.displayProjectHindDialog(PosterMAKERActivity.this.layFontsSpacing, PosterMAKERActivity.this);
                }
            }, 1000);
        }
    }

    public void AdjustOneTimeLayer() {
        if (this.appPreferenceClass.getInt(AppConstants.onTimeRecentHint, 0) == 0) {
            this.appPreferenceClass.putInt(AppConstants.onTimeRecentHint, 1);
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    AppConstants.showRecentProjectHindDialog(PosterMAKERActivity.btnLayer, PosterMAKERActivity.this);
                }
            }, 1000);
        }
    }


    private void dragMove(View view, MotionEvent motionEvent) {
        boolean z = view instanceof AutoStickerView;
        if (z) {
            AutoStickerView autoStickerView = (AutoStickerView) view;
            this.alphaSeekbar.setProgress(autoStickerView.getSTAlphaProg());
            this.hueSeekbar.setProgress(autoStickerView.get_sticker_HueProg());
        } else {
            this.layStkrMain.setVisibility(View.GONE);
        }
        if (z) {
            this.layEffects.setVisibility(View.GONE);
            this.layTextMain.setVisibility(View.GONE);
            this.layStkrMain.setVisibility(View.GONE);
        }
        if (view instanceof IntelligentTVAutoFit) {
            this.layEffects.setVisibility(View.GONE);
            this.layTextMain.setVisibility(View.GONE);
            this.layStkrMain.setVisibility(View.GONE);
        }
    }

    private class SaveTemplateDesignAsync extends AsyncTask<Void, Void, Integer> {
        boolean isThumbnailSaved = true;
        final ProgressDialog progressDialog = new ProgressDialog(PosterMAKERActivity.this);
        Bitmap thumbBit = null;

        public SaveTemplateDesignAsync(boolean z) {
        }

        @Override
        public void onPreExecute() {
            super.onPreExecute();
            try {
                PosterMAKERActivity.this.mainRelative.setDrawingCacheEnabled(true);
                this.thumbBit = Bitmap.createBitmap(PosterMAKERActivity.this.mainRelative.getDrawingCache());
                PosterMAKERActivity.this.mainRelative.setDrawingCacheEnabled(false);
            } catch (Throwable th) {
                th.printStackTrace();
//                CrashlyticsTracker.report(th, "Exception | Error : getDrawingCache");
            }
            if (this.thumbBit == null) {
                try {
                    this.thumbBit = Bitmap.createBitmap(PosterMAKERActivity.this.mainRelative.getWidth(), PosterMAKERActivity.this.mainRelative.getHeight(), Config.ARGB_8888);
                    PosterMAKERActivity.this.mainRelative.draw(new Canvas(this.thumbBit));
                } catch (Throwable th2) {
                    th2.printStackTrace();
//                    CrashlyticsTracker.report(th2, "Exception | Error : Canvas");
                }
            }
            ProgressDialog progressDialog = this.progressDialog;
            PosterMAKERActivity posterMAKERActivity = PosterMAKERActivity.this;
            progressDialog.setMessage(Glide_Image_Utils.getGlideSpannableString(posterMAKERActivity, posterMAKERActivity.ttf, R.string.plzwait));
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();
        }

        @Override
        public Integer doInBackground(Void... voidArr) {
            try {
                if (this.thumbBit == null) {
                    this.isThumbnailSaved = false;
                }
                int saveTemplateInDatabase = saveTemplateDb(this.thumbBit);
                if (this.thumbBit != null) {
                    this.thumbBit.recycle();
                    this.thumbBit = null;
                }
                return Integer.valueOf(saveTemplateInDatabase);
            } catch (Throwable th) {
                th.printStackTrace();
//                CrashlyticsTracker.report(th, "unexpected Exception | Error.");
                return Integer.valueOf(-1);
            }
        }

        private int saveTemplateDb(Bitmap bitmap) {
            try {
                if (PosterMAKERActivity.this.ratio.equals("")) {
                    PosterMAKERActivity.this.tempPath = AppConstants.exportBitmapObject1(PosterMAKERActivity.this.imgBtmap);
                }
                PosterMAKERActivity.this.tempPath = AppConstants.exportBitmapObject1(PosterMAKERActivity.this.imgBtmap);
                String saveBitmapObject = AppConstants.exportFinalBitmapObject(PosterMAKERActivity.activity, Glide_Image_Utils.resizeImageBitmap(bitmap, ((int) PosterMAKERActivity.this.screenWidth) / 3, ((int) PosterMAKERActivity.this.screenHeight) / 3));
                Template_InfoData templateInfoData = new Template_InfoData();
                if (bitmap != null) {
                    DB_Handler DB_Handler = null;
                    try {
                        templateInfoData.setTHUMB__INFO_URI(saveBitmapObject);
                        templateInfoData.setFRAME_INFO_NAME(PosterMAKERActivity.this.frameName);
                        templateInfoData.set_INFO_RATIO(PosterMAKERActivity.this.ratio);
                        templateInfoData.set_INFO_PROFILE_TYPE(PosterMAKERActivity.this.profile);
                        templateInfoData.set_INFO_SEEK_VALUE(String.valueOf(PosterMAKERActivity.this.seekValue));
                        templateInfoData.set_INFO_TYPE("USER");
                        templateInfoData.set_INFOTEMP_PATH(PosterMAKERActivity.this.tempPath);
                        templateInfoData.set_INFO_TEMPCOLOR(PosterMAKERActivity.this.hex);
                        templateInfoData.set_INFO_OVERLAY_NAME(PosterMAKERActivity.this.overlayName);
                        templateInfoData.set_INFO_OVERLAY_OPACITY(PosterMAKERActivity.this.seek.getProgress());
                        templateInfoData.set_INFO_OVERLAY_BLUR(PosterMAKERActivity.this.seekBlur.getProgress());
                        DB_Handler = DB_Handler.getDatabaseHandler(PosterMAKERActivity.this.getApplicationContext());
                        long insertTemplateRow = DB_Handler.insert_row_template(templateInfoData);
                        PosterMAKERActivity.this.finalSaveComponent1(insertTemplateRow, DB_Handler);
                        PosterMAKERActivity.this.isUpdated = true;
                        if (DB_Handler != null) {
                            DB_Handler.close();
                        }
                        return (int) insertTemplateRow;
                    } catch (Exception e) {
//                        CrashlyticsTracker.report(e, "Exception | Error");
                        e.printStackTrace();
                        if (DB_Handler != null) {
                            DB_Handler.close();
                        }
                        File file = new File(PosterMAKERActivity.this.tempPath);
                        if (file.exists()) {
                            file.delete();
                        }
                        if (saveBitmapObject != null) {
                            file = new File(saveBitmapObject);
                            if (file.exists()) {
                                file.delete();
                            }
                        }
                        return -1;
                    }
                }
            } catch (Exception e2) {
//                CrashlyticsTracker.report(e2, "Exception | Error");
            }
            return -1;
        }

        @Override
        public void onPostExecute(Integer num) {
            super.onPostExecute(num);
            this.progressDialog.dismiss();
            if (num.intValue() < 0) {
                new Builder(PosterMAKERActivity.this, 16974126).setMessage(AppConstants.findSpannableString(PosterMAKERActivity.this, Typeface.DEFAULT, R.string.memoryerror)).setPositiveButton(AppConstants.findSpannableString(PosterMAKERActivity.this, Typeface.DEFAULT, R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
            }
        }
    }

    public void onDelete() {
        hideScroll();
        if (this.layStkrMain.getVisibility() == View.VISIBLE) {
            this.layStkrMain.startAnimation(this.animSlideDown);
            this.layStkrMain.setVisibility(View.GONE);
        }
        if (this.layTextMain.getVisibility() == View.GONE) {
            this.layTextMain.startAnimation(this.animSlideDown);
            this.layTextMain.setVisibility(View.GONE);
        }
        this.guideline.setVisibility(View.GONE);
    }


    @Override
    public void onTouchDown(View view, MotionEvent motionEvent) {
        ontouchDown(view, "hideboder");
        if (this.checkTouchContinue) {
            this.layStkrMain.post(new Runnable() {
                public void run() {
                    PosterMAKERActivity.this.checkTouchContinue = true;
                    PosterMAKERActivity.this.mHandler.post(PosterMAKERActivity.this.mStatusChecker);
                }
            });
        }
    }

    @Override
    public void onTouchMove(View view, MotionEvent motionEvent) {
        dragMove(view, motionEvent);
    }

    @Override
    public void onTouchUp(View view, MotionEvent motionEvent) {
        this.checkTouchContinue = false;
        this.mHandler.removeCallbacks(this.mStatusChecker);
        dragUp(view, motionEvent);
    }

    @Override
    public void onMidX(View view) {
        this.guideline.setCenterValues(true, false);
    }

    @Override
    public void onMidXY(View view) {
        this.guideline.setCenterValues(true, true);
    }

    private void dragUp(final View view, MotionEvent motionEvent) {
        if (this.focusedCopy != this.selectedView) {
            this.seekbarContainer.setVisibility(View.VISIBLE);
            this.layTextEdit.setVisibility(View.VISIBLE);
        }
        if (view instanceof IntelligentTVAutoFit) {
            this.rotation = view.getRotation();
            if (this.layTextMain.getVisibility() == View.GONE) {
                this.layTextMain.setVisibility(View.VISIBLE);
                this.layTextMain.startAnimation(this.animSlideUp);
                this.layTextMain.post(new Runnable() {
                    public void run() {
                        PosterMAKERActivity.this.dragStickerView(view);
                    }
                });
            }
            int i = this.processs;
            if (i != 0) {
                this.verticalSeekBar.setProgress(i);
            }
        }
        if ((view instanceof AutoStickerView) && this.layStkrMain.getVisibility() == View.GONE) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(((AutoStickerView) view).getSTColorType());
            if (stringBuilder.toString().equals("white")) {
                this.layColor.setVisibility(View.VISIBLE);
                this.layHue.setVisibility(View.GONE);
            } else {
                this.layColor.setVisibility(View.GONE);
                this.layHue.setVisibility(View.VISIBLE);
            }
            this.layStkrMain.setVisibility(View.VISIBLE);
            this.layStkrMain.startAnimation(this.animSlideUp);
            this.layStkrMain.post(new Runnable() {
                public void run() {
                    PosterMAKERActivity.this.dragStickerView(view);
                }
            });
        }
        if (this.guideline.getVisibility() == View.VISIBLE) {
            this.guideline.setVisibility(View.GONE);
        }
        if (this.seekbarContainer.getVisibility() == View.GONE) {
            this.seekbarContainer.startAnimation(this.animSlideDown);
            this.seekbarContainer.setVisibility(View.GONE);
        }
    }

    @Override
    public void onMidY(View view) {
        this.guideline.setCenterValues(false, true);
    }

    @Override
    public void onXY(View view) {
        this.guideline.setCenterValues(false, false);
    }

    @Override
    public void onDoubleTap() {
        TwoTabPrass();
    }

    private void TwoTabPrass() {
        this.editMode = true;
        RelativeLayout relativeLayout = stickerLayout;
        if (relativeLayout != null && (relativeLayout.getChildAt(relativeLayout.getChildCount() - 1) instanceof IntelligentTVAutoFit)) {
            relativeLayout = stickerLayout;
            TV_Info_Adapter textViewInfoAdapter = ((IntelligentTVAutoFit) relativeLayout.getChildAt(relativeLayout.getChildCount() - 1)).getTVTextInfo();
            this.layEffects.setVisibility(View.GONE);
            this.layStkrMain.setVisibility(View.GONE);
            this.layTextMain.setVisibility(View.GONE);

            if (textViewInfoAdapter != null)
                addTextEditorDialog(textViewInfoAdapter);
            else {
                addTextEditorDialog(null);
            }
        }
    }

    private void hideScroll() {
        int[] iArr = new int[2];
        this.scrollLayout.getLocationOnScreen(iArr);
        final float f = (float) iArr[1];
        final float dpToPx = (float) Glide_Image_Utils.convertDpToPx(this, 50.0f);
        if (f != dpToPx) {
            this.scrollLayout.setY(this.yAtLayoutCenter - ((float) Glide_Image_Utils.convertDpToPx(this, 50.0f)));
        }
        LayoutParams layoutParams = new LayoutParams(-1, -2);
        layoutParams.addRule(13);
        this.scrollLayout.setLayoutParams(layoutParams);
        this.scrollLayout.postInvalidate();
        this.scrollLayout.requestLayout();
        this.scrollLayout.post(new Runnable() {
            public void run() {
                if (f != dpToPx) {
                    PosterMAKERActivity.this.scrollLayout.setY(PosterMAKERActivity.this.yAtLayoutCenter - ((float) Glide_Image_Utils.convertDpToPx(PosterMAKERActivity.this, 50.0f)));
                }
            }
        });
    }

    public void chooseGalleryPermission() {
        /* Changed 26/05/2023 */
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            Dexter.withActivity(this).withPermissions("android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE").withListener(new MultiplePermissionsListener() {
                public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                    if (multiplePermissionsReport.areAllPermissionsGranted()) {
                        PosterMAKERActivity.this.onClickGalleryButton();
                    }
                    if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                        PosterMAKERActivity.this.displaySettingsDialog();
                    }
                }

                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                    permissionToken.continuePermissionRequest();
                }
            }).withErrorListener(new PermissionRequestErrorListener() {
                public void onError(DexterError dexterError) {
                    Toast.makeText(PosterMAKERActivity.this.getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                }
            }).onSameThread().check();
        } else {
            Dexter.withActivity(this).withPermissions("android.permission.CAMERA", "android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO").withListener(new MultiplePermissionsListener() {
                public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                    if (multiplePermissionsReport.areAllPermissionsGranted()) {
                        PosterMAKERActivity.this.onClickGalleryButton();
                    }
                    if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                        PosterMAKERActivity.this.displaySettingsDialog();
                    }
                }

                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                    permissionToken.continuePermissionRequest();
                }
            }).withErrorListener(new PermissionRequestErrorListener() {
                public void onError(DexterError dexterError) {
                    Toast.makeText(PosterMAKERActivity.this.getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                }
            }).onSameThread().check();
        }
    }

    public void releaseIVControll() {
        this.guideline.setVisibility(View.GONE);
        RelativeLayout relativeLayout = stickerLayout;
        if (relativeLayout != null) {
            int childCount = relativeLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = stickerLayout.getChildAt(i);
                if (childAt instanceof IntelligentTVAutoFit) {
                    ((IntelligentTVAutoFit) childAt).applyBorderVisibility(false);
                }
                if (childAt instanceof AutoStickerView) {
                    ((AutoStickerView) childAt).applyBorderVisibility(false);
                }
            }
        }
    }

    @Override
    public void onRotateDown(View view, MotionEvent motionEvent) {
        ontouchDown(view, "viewboder");
    }

    @Override
    public void onRotateMove(View view, MotionEvent motionEvent) {
        dragMove(view, motionEvent);
    }

    @Override
    public void onRotateUp(View view, MotionEvent motionEvent) {
        dragUp(view, motionEvent);
    }

    @Override
    public void onScaleDown(View view, MotionEvent motionEvent) {
        ontouchDown(view, "viewboder");
    }

    @Override
    public void onScaleMove(View view, MotionEvent motionEvent) {
        dragMove(view, motionEvent);
    }

    @Override
    public void onScaleUp(View view, MotionEvent motionEvent) {
        dragUp(view, motionEvent);
    }

    public void chooseCameraPermission() {
        /* Changed 26/05/2023 */
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            Dexter.withActivity(this).withPermissions("android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE").withListener(new MultiplePermissionsListener() {
                public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                    if (multiplePermissionsReport.areAllPermissionsGranted()) {
                        PosterMAKERActivity.this.onClickCameraButton();
                    }
                    if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                        PosterMAKERActivity.this.displaySettingsDialog();
                    }
                }

                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                    permissionToken.continuePermissionRequest();
                }
            }).withErrorListener(new PermissionRequestErrorListener() {
                public void onError(DexterError dexterError) {
                    Toast.makeText(PosterMAKERActivity.this.getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                }
            }).onSameThread().check();
        } else {
            Dexter.withActivity(this).withPermissions("android.permission.CAMERA", "android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO").withListener(new MultiplePermissionsListener() {
                public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                    if (multiplePermissionsReport.areAllPermissionsGranted()) {
                        PosterMAKERActivity.this.onClickCameraButton();
                    }
                    if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                        PosterMAKERActivity.this.displaySettingsDialog();
                    }
                }

                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                    permissionToken.continuePermissionRequest();
                }
            }).withErrorListener(new PermissionRequestErrorListener() {
                public void onError(DexterError dexterError) {
                    Toast.makeText(PosterMAKERActivity.this.getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                }
            }).onSameThread().check();
        }
    }


    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        int i3 = i;
        int i4 = i2;
        Intent intent2 = intent;
        super.onActivityResult(i, i2, intent);

        if (i4 == -1) {
            this.layStkrMain.setVisibility(View.GONE);
            if (intent2 != null || i3 == SELECT_PICTURE_CAMERA || i3 == 4 || i3 == TEXT_ACTIVITY) {
                StringBuilder stringBuilder;
                Uri fromFile;
                int i5;
                Bundle bundle = null;
                if (i3 == TEXT_ACTIVITY) {
                    bundle = intent.getExtras();
                    TV_Info_Adapter textViewInfoAdapter = new TV_Info_Adapter();
                    textViewInfoAdapter.setTVPOS_X(bundle.getFloat("X", 0.0f));
                    textViewInfoAdapter.setTVPOS_Y(bundle.getFloat("Y", 0.0f));
                    textViewInfoAdapter.setTVWIDTH(bundle.getInt("wi", Glide_Image_Utils.convertDpToPx(this, 200.0f)));
                    textViewInfoAdapter.setTVHEIGHT(bundle.getInt("he", Glide_Image_Utils.convertDpToPx(this, 200.0f)));
                    textViewInfoAdapter.setTEXT(bundle.getString("text", ""));
                    textViewInfoAdapter.setTV_FONT_NAME(bundle.getString("font_Name", ""));
                    textViewInfoAdapter.setTEXT_COLOR(bundle.getInt("textColor", Color.parseColor("#4149b6")));
                    textViewInfoAdapter.setTEXT_ALPHA(bundle.getInt("textAlpha", 100));
                    textViewInfoAdapter.setTV_SHADOW_COLOR(bundle.getInt("textShadowColor", Color.parseColor("#7641b6")));
                    textViewInfoAdapter.setTVSHADOW_PROG(bundle.getInt("textShadowProg", 5));
                    textViewInfoAdapter.setTV_BG_COLOR(bundle.getInt("backgroundColor", 0));
                    textViewInfoAdapter.setTV_BG_DRAWABLE(bundle.getString("backgroundDrawable", "0"));
                    textViewInfoAdapter.setTV_BG_ALPHA(bundle.getInt("backgroundAlpha", 255));
                    textViewInfoAdapter.setTV_ROTATION(bundle.getFloat("anim_rotation", 0.0f));
                    textViewInfoAdapter.setFIELD_TWO(bundle.getString("field_two", ""));
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("");
                    stringBuilder2.append(bundle.getFloat("X", 0.0f));
                    stringBuilder2.append(" ,");
                    stringBuilder2.append(bundle.getFloat("Y", 0.0f));
                    Log.e("double tab 22", stringBuilder2.toString());
                    this.font_Name = bundle.getString("font_Name", "");
                    this.textColor = bundle.getInt("textColor", Color.parseColor("#4149b6"));
                    this.textShadowColor = bundle.getInt("textShadowColor", Color.parseColor("#7641b6"));
                    this.textShadowProg = bundle.getInt("textShadowProg", 0);
                    this.textAlpha = bundle.getInt("textAlpha", 100);
                    this.backgroundDrawable = bundle.getString("backgroundDrawable", "0");
                    this.backgroundAlpha = bundle.getInt("backgroundAlpha", 255);
                    this.rotation = bundle.getFloat("anim_rotation", 0.0f);
                    this.backgroundColor = bundle.getInt("backgroundColor", 0);
                    if (this.editMode) {
                        RelativeLayout relativeLayout = stickerLayout;
                        ((IntelligentTVAutoFit) relativeLayout.getChildAt(relativeLayout.getChildCount() - 1)).setTVTextInfo(textViewInfoAdapter, false);
                        RelativeLayout relativeLayout2 = stickerLayout;
                        ((IntelligentTVAutoFit) relativeLayout2.getChildAt(relativeLayout2.getChildCount() - 1)).applyBorderVisibility(true);
                        this.editMode = false;
                    } else {
                        this.verticalSeekBar.setProgress(100);
                        this.seekBarShadow.setProgress(0);
                        this.seekBar3.setProgress(255);
                        IntelligentTVAutoFit intelligentTVAutoFit = new IntelligentTVAutoFit(this);
                        stickerLayout.addView(intelligentTVAutoFit);
                        intelligentTVAutoFit.setTVFromAddText(true);
                        intelligentTVAutoFit.setTVTextInfo(textViewInfoAdapter, false);
                        intelligentTVAutoFit.setViewWH((float) this.mainRelative.getWidth(), (float) this.mainRelative.getHeight());
                        intelligentTVAutoFit.setId(View_ID_Finder.generateViewId());
                        intelligentTVAutoFit.setOnTV_TouchCallbackListener(this);
                        intelligentTVAutoFit.applyBorderVisibility(true);
                    }
                    if (this.layTextMain.getVisibility() == View.GONE) {
                        this.layTextMain.setVisibility(View.VISIBLE);
                        this.layTextMain.startAnimation(this.animSlideUp);
                    }
                }
                if (i3 == SELECT_PICTURE_GALLERY) {
                    try {
                        this.imgOK.setVisibility(View.VISIBLE);
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("SampleCropImage");
                        stringBuilder.append(System.currentTimeMillis());
                        stringBuilder.append(".png");
                        fromFile = Uri.fromFile(new File(getCacheDir(), stringBuilder.toString()));
                        StringBuilder stringBuilder3 = new StringBuilder();
                        stringBuilder3.append("====");
                        stringBuilder3.append(intent.getData().getPath());
                        Log.e("downaload", stringBuilder3.toString());
                        stringBuilder3 = new StringBuilder();
                        stringBuilder3.append("====");
                        stringBuilder3.append(fromFile.getPath());
                        Log.e("downaload", stringBuilder3.toString());
                        UCrop.Options options = new UCrop.Options();
                        options.setToolbarColor(getResources().getColor(R.color.colorPrimary));
                        options.setAspectRatioOptions(0, new AspectRatio("1:1", 1.0f, 1.0f), new AspectRatio("3:2", 3.0f, 2.0f), new AspectRatio("2:3", 2.0f, 3.0f), new AspectRatio("4:3", 4.0f, 3.0f), new AspectRatio("3:4", 3.0f, 4.0f), new AspectRatio("16:9", 16.0f, 9.0f), new AspectRatio("5:4", 5.0f, 4.0f), new AspectRatio("4:5", 4.0f, 5.0f));
                        options.setFreeStyleCropEnabled(true);
                        options.setCompressionFormat(CompressFormat.PNG);
                        UCrop.of(intent.getData(), fromFile).withOptions(options).start(this);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (i3 == SELECT_PICTURE_CAMERA) {
                    try {
                        this.imgOK.setVisibility(View.VISIBLE);
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("SampleCropImage");
                        stringBuilder.append(System.currentTimeMillis());
                        stringBuilder.append(".png");
                        fromFile = Uri.fromFile(new File(getCacheDir(), stringBuilder.toString()));
                        StringBuilder stringBuilder4 = new StringBuilder();
                        stringBuilder4.append("====");
                        stringBuilder4.append(fromFile.getPath());
                        Log.e("downaload", stringBuilder4.toString());
                        UCrop.Options options2 = new UCrop.Options();
                        options2.setToolbarColor(getResources().getColor(R.color.colorPrimary));
                        options2.setAspectRatioOptions(0, new AspectRatio("1:1", 1.0f, 1.0f), new AspectRatio("3:2", 3.0f, 2.0f), new AspectRatio("2:3", 2.0f, 3.0f), new AspectRatio("4:3", 4.0f, 3.0f), new AspectRatio("3:4", 3.0f, 4.0f), new AspectRatio("16:9", 16.0f, 9.0f), new AspectRatio("5:4", 5.0f, 4.0f), new AspectRatio("4:5", 4.0f, 5.0f));
                        options2.setFreeStyleCropEnabled(true);
                        options2.setCompressionFormat(CompressFormat.PNG);
                        StringBuilder stringBuilder5 = new StringBuilder();
                        stringBuilder5.append(getApplicationContext().getPackageName());
                        stringBuilder5.append(".provider");
                        UCrop.of(FileProvider.getUriForFile(this, stringBuilder5.toString(), this.f), fromFile).withOptions(options2).start(this);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                if (i3 == SELECT_PICTURE_FROM_GALLERY_BACKGROUND) {
                    try {
                        if (this.layBackground.getVisibility() == View.VISIBLE) {
                            this.layBackground.startAnimation(this.animSlideDown);
                            this.layBackground.setVisibility(View.GONE);
                        }
                        this.imgOK.setVisibility(View.VISIBLE);
                        this.screenWidth = (float) this.backgroundImage.getWidth();
                        this.screenHeight = (float) this.backgroundImage.getHeight();
                        AppConstants.bitmap = Glide_Image_Utils.scaleGlideCenterCrop(AppConstants.getFinalBitmapFromUri(this, intent.getData(), this.screenWidth, this.screenHeight), (int) this.screenHeight, (int) this.screenWidth);
                        this.showtailsSeek = false;
                        this.position = "1";
                        this.profile = "Temp_Path";
                        this.hex = "";
                        ResizeViewLayout(AppConstants.bitmap, "nonCreated");
                        i5 = -1;
                    } catch (Exception e22) {
                        e22.printStackTrace();
                        i5 = -1;
                    }
                } else {
                    i5 = -1;
                }
                if (i4 == i5 && i3 == 69) {
                    enhanceCropResult(intent2);
                } else if (i4 == 96) {
                    UCrop.getError(intent);
                }
                if (i3 == 4) {
                    startCustomActivity(bundle, intent2);
                    return;
                }
                return;
            }
            new Builder(this, 16974126).setMessage(AppConstants.findSpannableString(this, Typeface.DEFAULT, R.string.picUpImg)).setPositiveButton(AppConstants.findSpannableString(this, Typeface.DEFAULT, R.string.ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            }).create().show();
        } else if (i3 == TEXT_ACTIVITY) {
            this.editMode = false;
        }
    }

    private void chooseGalleryImagePermission() {
        /* Changed 26/05/2023 */
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            Dexter.withActivity(this).withPermissions("android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE").withListener(new MultiplePermissionsListener() {
                public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                    if (multiplePermissionsReport.areAllPermissionsGranted()) {
                        PosterMAKERActivity.this.chooseGalleryBackground();
                    }
                    if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                        PosterMAKERActivity.this.displaySettingsDialog();
                    }
                }

                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                    permissionToken.continuePermissionRequest();
                }
            }).withErrorListener(new PermissionRequestErrorListener() {
                public void onError(DexterError dexterError) {
                    Toast.makeText(PosterMAKERActivity.this.getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                }
            }).onSameThread().check();
        } else {
            Dexter.withActivity(this).withPermissions("android.permission.CAMERA", "android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO").withListener(new MultiplePermissionsListener() {
                public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                    if (multiplePermissionsReport.areAllPermissionsGranted()) {
                        PosterMAKERActivity.this.chooseGalleryBackground();
                    }
                    if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                        PosterMAKERActivity.this.displaySettingsDialog();
                    }
                }

                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                    permissionToken.continuePermissionRequest();
                }
            }).withErrorListener(new PermissionRequestErrorListener() {
                public void onError(DexterError dexterError) {
                    Toast.makeText(PosterMAKERActivity.this.getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                }
            }).onSameThread().check();
        }
    }

    public void displaySettingsDialog() {
        Builder builder = new Builder(this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                PosterMAKERActivity.this.startSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    @Override
    public void onSnapFilter(int i, int i2, String str) {
        this.laySticker.setVisibility(View.GONE);
        btnLayer.setVisibility(View.VISIBLE);
        if (this.layTextMain.getVisibility() == View.VISIBLE) {
            this.layTextMain.startAnimation(this.animSlideDown);
            this.layTextMain.setVisibility(View.GONE);
        }
        StringBuilder stringBuilder;
        if (!str.equals("")) {
            this.colorType = "colored";
            applySticker("", str, null);
        } else if (i2 == 33) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("sticker_offer_");
            stringBuilder.append(i + 1);
            applyDrawable("colored", stringBuilder.toString());
        } else if (i2 == 34) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("sticker_sale_");
            stringBuilder.append(i + 1);
            applyDrawable("colored", stringBuilder.toString());
        } else if (i2 == 35) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("sticker_banner_");
            stringBuilder.append(i + 1);
            applyDrawable("colored", stringBuilder.toString());
        } else if (i2 == 36) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("sticker_ribbon_");
            stringBuilder.append(i + 1);
            applyDrawable("colored", stringBuilder.toString());
        } else if (i2 == 37) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("sticker_sport_");
            stringBuilder.append(i + 1);
            applyDrawable("colored", stringBuilder.toString());
        } else if (i2 == 38) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("sticker_birthday_");
            stringBuilder.append(i + 1);
            applyDrawable("colored", stringBuilder.toString());
        } else if (i2 == 39) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("sticker_decoration_");
            stringBuilder.append(i + 1);
            applyDrawable("colored", stringBuilder.toString());
        } else if (i2 == 40) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("sticker_party_");
            stringBuilder.append(i + 1);
            applyDrawable("colored", stringBuilder.toString());
        } else if (i2 == 41) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("sticker_love_");
            stringBuilder.append(i + 1);
            applyDrawable("colored", stringBuilder.toString());
        } else if (i2 == 42) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("sticker_music_");
            stringBuilder.append(i + 1);
            applyDrawable("colored", stringBuilder.toString());
        } else if (i2 == 43) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("sticker_festival_");
            stringBuilder.append(i + 1);
            applyDrawable("colored", stringBuilder.toString());
        } else if (i2 == 44) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("sticker_nature_");
            stringBuilder.append(i + 1);
            applyDrawable("colored", stringBuilder.toString());
        } else if (i2 == 45) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("sticker_car_");
            stringBuilder.append(i + 1);
            applyDrawable("colored", stringBuilder.toString());
        } else if (i2 == 46) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("sticker_emoji_");
            stringBuilder.append(i + 1);
            applyDrawable("colored", stringBuilder.toString());
        } else if (i2 == 47) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("sticker_college_");
            stringBuilder.append(i + 1);
            applyDrawable("colored", stringBuilder.toString());
        } else if (i2 == 48) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("sticker_coffe_");
            stringBuilder.append(i + 1);
            applyDrawable("colored", stringBuilder.toString());
        } else if (i2 == 49) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("sticker_halloween_");
            stringBuilder.append(i + 1);
            applyDrawable("colored", stringBuilder.toString());
        } else if (i2 == 50) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("shap");
            stringBuilder.append(i + 1);
            applyDrawable("white", stringBuilder.toString());
        } else if (i2 == 51) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("sticker_animal_");
            stringBuilder.append(i + 1);
            applyDrawable("colored", stringBuilder.toString());
        } else if (i2 == 52) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("sticker_cartoon_");
            stringBuilder.append(i + 1);
            applyDrawable("colored", stringBuilder.toString());
        } else {
            this.colorType = "colored";
        }
    }

    public void startSettings() {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", getPackageName(), null));
        startActivityForResult(intent, 101);
    }

    private void enhanceCropResult(@NonNull Intent intent) {
        this.layBackground.setVisibility(View.GONE);
        this.imgOK.setVisibility(View.VISIBLE);
        if (getSupportFragmentManager().getBackStackEntryCount() >= 1) {
            getSupportFragmentManager().popBackStack();
        }
        Uri output = UCrop.getOutput(intent);
        if (output != null) {
            if (this.isBackground) {
                this.profile = "no";
                if (this.profile.equals("no")) {
                    this.showtailsSeek = false;
                    this.position = "1";
                    this.profile = "Temp_Path";
                    this.hex = "";
                    try {
                        if (this.seekbarContainer.getVisibility() == View.GONE) {
                            this.seekbarContainer.setVisibility(View.VISIBLE);
                            this.seekbarContainer.startAnimation(this.animSlideUp);
                        }
                        generatebitmapRatio(this.ratio, this.profile, Glide_Image_Utils.getResampleGlideImageBitmap(output, this, (int) (this.screenWidth > this.screenHeight ? this.screenWidth : this.screenHeight)), "nonCreated");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                FragmentManager supportFragmentManager = getSupportFragmentManager();
                FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();
                Fragment_Edit_Image fragmentEditImage = (Fragment_Edit_Image) supportFragmentManager.findFragmentByTag("editing_frgm");
                if (fragmentEditImage != null) {
                    beginTransaction.remove(fragmentEditImage);
                }
                Fragment_Edit_Image newInstance = Fragment_Edit_Image.newInstance(output);
                this.mFragments.add(new WeakReference(newInstance));
                beginTransaction.add(R.id.frameContainerEditing, newInstance, "editing_frgm");
                beginTransaction.addToBackStack("editing_frgm");
                try {
                    beginTransaction.commitAllowingStateLoss();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            this.isBackground = false;
        }
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, true);
        //  bm.recycle();
        return resizedBitmap;
    }

    public void exportBitmap(final boolean z, int mul) {

        final SweetAlertDialog progressDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        progressDialog.getProgressHelper().setBarColor(Color.parseColor("#D81B60"));
        progressDialog.setTitleText(getResources().getString(R.string.plzwait));
        progressDialog.setCancelable(false);
        progressDialog.show();

        new Thread(new Runnable() {
            @SuppressLint({"WrongConstant"})
            public void run() {
                try {
                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "/Poster Design");
                    if (!file.exists()) {
                        if (!file.mkdirs()) {
                            Log.d("", "Can't create directory to save image.");
                            Toast.makeText(PosterMAKERActivity.this.getApplicationContext(), PosterMAKERActivity.this.getResources().getString(R.string.create_dir_err), 1).show();
                            return;
                        }
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Photo_");
                    stringBuilder.append(System.currentTimeMillis());
                    String stringBuilder2 = stringBuilder.toString();
                    StringBuilder stringBuilder3;
                    if (z) {
                        stringBuilder3 = new StringBuilder();
                        stringBuilder3.append(stringBuilder2);
                        stringBuilder3.append(".png");
                        stringBuilder2 = stringBuilder3.toString();
                    } else {
                        stringBuilder3 = new StringBuilder();
                        stringBuilder3.append(stringBuilder2);
                        stringBuilder3.append(".jpg");
                        stringBuilder2 = stringBuilder3.toString();
                    }
                    PosterMAKERActivity posterMAKERActivity = PosterMAKERActivity.this;
                    StringBuilder stringBuilder4 = new StringBuilder();
                    stringBuilder4.append(file.getPath());
                    stringBuilder4.append(File.separator);
                    stringBuilder4.append(stringBuilder2);
                    posterMAKERActivity.filename = stringBuilder4.toString();
                    file = new File(PosterMAKERActivity.this.filename);
                    try {
                        if (!file.exists()) {
                            file.createNewFile();
                        }
                        FileOutputStream fileOutputStream = new FileOutputStream(file);

                        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());

                        // Bitmap createBitmap = getResizedBitmap(bitmap, bitmap.getWidth() * mul, bitmap.getHeight() * mul);
                        //  Bitmap createBitmap = resizeBitmapByScale(bitmap, mul, false);

                        Canvas canvas = new Canvas(createBitmap);
                        canvas.drawColor(-1);
                        canvas.drawBitmap(bitmap, 0.0f, 0.0f, null);
                        PosterMAKERActivity.this.checkMemory = createBitmap.compress(CompressFormat.PNG, 100, fileOutputStream);
                        createBitmap.recycle();
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        PosterMAKERActivity.this.isUpdated = true;
                        MediaScannerConnection.scanFile(PosterMAKERActivity.this, new String[]{file.getAbsolutePath()}, null, new OnScanCompletedListener() {
                            public void onScanCompleted(String str, Uri uri) {
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("Scanned ");
                                stringBuilder.append(str);
                                stringBuilder.append(":");
                                Log.i("ExternalStorage", stringBuilder.toString());
                                StringBuilder stringBuilder2 = new StringBuilder();
                                stringBuilder2.append("-> uri=");
                                stringBuilder2.append(uri);
                                Log.i("ExternalStorage", stringBuilder2.toString());
                            }
                        });
                        PosterMAKERActivity posterMAKERActivity2 = PosterMAKERActivity.this;
                        PosterMAKERActivity posterMAKERActivity3 = PosterMAKERActivity.this;
                        StringBuilder stringBuilder5 = new StringBuilder();
                        stringBuilder5.append(PosterMAKERActivity.this.getApplicationContext().getPackageName());
                        stringBuilder5.append(".provider");
                        posterMAKERActivity2.sendBroadcast(new Intent("androR.id.intent.action.MEDIA_SCANNER_SCAN_FILE", FileProvider.getUriForFile(posterMAKERActivity3, stringBuilder5.toString(), file)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Thread.sleep(1000);
                    progressDialog.dismiss();
                } catch (Exception unused) {
                }
            }
        }).start();
        progressDialog.setOnDismissListener(new OnDismissListener() {
            public void onDismiss(DialogInterface dialogInterface) {
                if (checkMemory) {
                    mainRelative.setDrawingCacheEnabled(true);
                    Bitmap createBitmap = Bitmap.createBitmap(mainRelative.getDrawingCache());
                    mainRelative.setDrawingCacheEnabled(false);
                    DB_Handler DB_Handler = null;
                    try {
                        if (ratio.equals("")) {
                            tempPath = AppConstants.exportBitmapObject1(PosterMAKERActivity.this.imgBtmap);
                        }
                        tempPath = AppConstants.exportBitmapObject1(PosterMAKERActivity.this.imgBtmap);
                        String saveBitmapObject = AppConstants.exportFinalBitmapObject(PosterMAKERActivity.activity, Glide_Image_Utils.resizeImageBitmap(createBitmap, ((int) PosterMAKERActivity.this.screenWidth) / 3, ((int) PosterMAKERActivity.this.screenHeight) / 3));
                        if (saveBitmapObject != null) {
                            Template_InfoData templateInfoData = new Template_InfoData();
                            templateInfoData.setTHUMB__INFO_URI(saveBitmapObject);
                            templateInfoData.setFRAME_INFO_NAME(PosterMAKERActivity.this.frameName);
                            templateInfoData.set_INFO_RATIO(PosterMAKERActivity.this.ratio);
                            templateInfoData.set_INFO_PROFILE_TYPE(PosterMAKERActivity.this.profile);
                            templateInfoData.set_INFO_SEEK_VALUE(String.valueOf(PosterMAKERActivity.this.seekValue));
                            templateInfoData.set_INFO_TYPE("USER");
                            templateInfoData.set_INFOTEMP_PATH(PosterMAKERActivity.this.tempPath);
                            templateInfoData.set_INFO_TEMPCOLOR(PosterMAKERActivity.this.hex);
                            templateInfoData.set_INFO_OVERLAY_NAME(PosterMAKERActivity.this.overlayName);
                            templateInfoData.set_INFO_OVERLAY_OPACITY(PosterMAKERActivity.this.seek.getProgress());
                            templateInfoData.set_INFO_OVERLAY_BLUR(PosterMAKERActivity.this.seekBlur.getProgress());
                            DB_Handler = DB_Handler.getDatabaseHandler(PosterMAKERActivity.this.getApplicationContext());
                            PosterMAKERActivity.this.finalSaveComponent1(DB_Handler.insert_row_template(templateInfoData), DB_Handler);
                            PosterMAKERActivity.this.isUpdated = true;
                        }
                        if (DB_Handler != null) {
                            DB_Handler.close();
                        }
                    } catch (Exception e) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Exception ");
                        stringBuilder.append(e.getMessage());
                        Log.i("testing", stringBuilder.toString());
                        e.printStackTrace();
                        if (DB_Handler != null) {
                            DB_Handler.close();
                        }
                    } catch (Throwable unused) {
                        if (DB_Handler != null) {
                            DB_Handler.close();
                        }
                    }
                    if (PosterMAKERActivity.this.isRewarded) {
                        PosterMAKERActivity.this.isRewarded = false;
                    }

                    if (!isActive) {
                        new InterstitialAds().Show_Ads(PosterMAKERActivity.this, new InterstitialAds.AdCloseListener() {
                            @Override
                            public void onAdClosed() {
                                Intent intent = new Intent(PosterMAKERActivity.this, PreviewActivity.class);
                                intent.putExtra("uri", PosterMAKERActivity.this.filename);
                                intent.putExtra("way", "Poster");
                                startActivity(intent);
                            }
                        });
                    } else {
                        Intent intent = new Intent(PosterMAKERActivity.this, PreviewActivity.class);
                        intent.putExtra("uri", PosterMAKERActivity.this.filename);
                        intent.putExtra("way", "Poster");
                        startActivity(intent);
                    }

                    return;
                }
                new Builder(PosterMAKERActivity.this, 16974126).setMessage(AppConstants.findSpannableString(PosterMAKERActivity.this, Typeface.DEFAULT, R.string.memoryerror)).setPositiveButton(AppConstants.findSpannableString(PosterMAKERActivity.this, Typeface.DEFAULT, R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
            }
        });
    }

    private static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight, boolean isNecessaryToKeepOrig) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        if (!isNecessaryToKeepOrig) {
            bm.recycle();
        }
        return resizedBitmap;
    }

    private static Bitmap.Config getConfig(Bitmap bitmap) {
        Bitmap.Config config = bitmap.getConfig();
        if (config == null) {
            config = Bitmap.Config.ARGB_8888;
        }
        return config;
    }

    private Bitmap scale(Bitmap bitmap, int maxWidth, int maxHeight) {
        // Determine the constrained dimension, which determines both dimensions.
        int width;
        int height;
        float widthRatio = (float) bitmap.getWidth() / maxWidth;
        float heightRatio = (float) bitmap.getHeight() / maxHeight;
        // Width constrained.
        if (widthRatio >= heightRatio) {
            width = maxWidth;
            height = (int) (((float) width / bitmap.getWidth()) * bitmap.getHeight());
        }
        // Height constrained.
        else {
            height = maxHeight;
            width = (int) (((float) height / bitmap.getHeight()) * bitmap.getWidth());
        }
        Bitmap scaledBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        float ratioX = (float) width / bitmap.getWidth();
        float ratioY = (float) height / bitmap.getHeight();
        float middleX = width / 2.0f;
        float middleY = height / 2.0f;
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, middleX - bitmap.getWidth() / 2, middleY - bitmap.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));
        return scaledBitmap;
    }


    private int gcd(int i, int i2) {
        return i2 == 0 ? i : gcd(i2, i % i2);
    }

    public void chooseGalleryBackground() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.GET_CONTENT");
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture)), SELECT_PICTURE_FROM_GALLERY_BACKGROUND);
    }

    private void selectcolorPickerDialog(boolean z) {
        new AmbilWarnaDialog(this, this.bColor, z, new OnAmbilWarnaListener() {
            public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {
            }

            public void onOk(AmbilWarnaDialog ambilWarnaDialog, int i) {
                PosterMAKERActivity.this.applyBackgroundColor(i);
            }
        }).show();
    }


    public void applyBackgroundColor(int i) {
        if (this.layBackground.getVisibility() == View.VISIBLE) {
            this.layBackground.startAnimation(this.animSlideDown);
            this.layBackground.setVisibility(View.GONE);
        }
        this.imgOK.setVisibility(View.VISIBLE);
        Bitmap createBitmap = Bitmap.createBitmap(720, 1280, Config.ARGB_8888);
        createBitmap.eraseColor(i); // 7650690
        Log.e(TAG, "updateViewColor: ");
        try {
            this.screenWidth = (float) this.backgroundImage.getWidth();
            this.screenHeight = (float) this.backgroundImage.getHeight();
            AppConstants.bitmap = Glide_Image_Utils.scaleGlideCenterCrop(createBitmap, (int) this.screenHeight, (int) this.screenWidth);
            this.showtailsSeek = false;
            this.ratio = "";
            this.position = "1";
            this.profile = "Temp_Path";
            this.hex = "";
            ResizeViewLayout(AppConstants.bitmap, "nonCreated");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (animationDrawable != null && !animationDrawable.isRunning()) {
            animationDrawable.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (animationDrawable != null && animationDrawable.isRunning()) {
            // stop the animation
            animationDrawable.stop();
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() >= 1) {
            getSupportFragmentManager().popBackStack();
        } else if (this.layTextMain.getVisibility() == View.VISIBLE) {
            if (this.layTextEdit.getVisibility() == View.VISIBLE) {
                unhideTextResContainer();
                hideScroll();
                return;
            }
            showActivityBackDialog();
        } else if (this.layStkrMain.getVisibility() == View.VISIBLE) {
            if (this.seekbarContainer.getVisibility() == View.VISIBLE) {
                hideResContainer();
                hideScroll();
                return;
            }
            showActivityBackDialog();
        } else if (this.laySticker.getVisibility() == View.VISIBLE) {
            this.laySticker.setVisibility(View.GONE);
            this.imgOK.setVisibility(View.VISIBLE);
            this.addSticker.setBackgroundResource(R.drawable.bg_trans);
            btnLayer.setVisibility(View.VISIBLE);
        } else if (this.seekbarContainer.getVisibility() == View.VISIBLE) {
            Log.e("onBackPressed", "==1==");
            this.seekbarContainer.startAnimation(this.animSlideDown);
            this.seekbarContainer.setVisibility(View.GONE);
        } else if (this.layEffects.getVisibility() == View.VISIBLE) {
            Log.e("onBackPressed", "==2==");
            this.layEffects.startAnimation(this.animSlideDown);
            this.layEffects.setVisibility(View.GONE);
        } else if (this.layBackground.getVisibility() == View.VISIBLE) {
            Log.e("onBackPressed", "==3==");
            this.imgOK.setVisibility(View.VISIBLE);
            this.layBackground.startAnimation(this.animSlideDown);
            this.layBackground.setVisibility(View.GONE);
        } else if (layerContainer.getVisibility() == View.VISIBLE) {
            this.imgOK.setVisibility(View.VISIBLE);
            layerContainer.animate().translationX((float) (-layerContainer.getRight())).setDuration(200).setInterpolator(new AccelerateInterpolator()).start();
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    PosterMAKERActivity.layerContainer.setVisibility(View.GONE);
                    PosterMAKERActivity.btnLayer.setVisibility(View.VISIBLE);
                }
            }, 200);
        } else {
            showActivityBackDialog();
        }
    }

    public void showErrorDialog() {
        final Dialog dialog = new Dialog(this, R.style.ThemeWithCorners);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.layout_error_dialog);
        TextView textView = dialog.findViewById(R.id.txt_free);
        Button button = dialog.findViewById(R.id.btn_yes);
        ((TextView) dialog.findViewById(R.id.txterorr)).setTypeface(adjustFontBold());
        textView.setTypeface(adjustFontBold());
        button.setTypeface(adjustFontBold());
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                finish();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showActivityBackDialog() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("We notice that your work not saved! Please Tap \"SAVE\" button to save your creation")
                .setCancelText("Exit")
                .setConfirmText("Save")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        finish();
                        sDialog.dismissWithAnimation();
                    }
                }).setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                        invisibleSlideBar();
                        hideScroll();

                        layEffects.setVisibility(View.GONE);
                        layStkrMain.setVisibility(View.GONE);
                        layBackground.setVisibility(View.GONE);
                        layTextMain.setVisibility(View.GONE);
                        laySticker.setVisibility(View.GONE);

                        releaseIVControll();

                        if (seekbarContainer.getVisibility() == View.VISIBLE) {
                            seekbarContainer.startAnimation(animSlideDown);
                            seekbarContainer.setVisibility(View.GONE);
                        }
                        if (layTextMain.getVisibility() == View.VISIBLE) {
                            layTextMain.startAnimation(animSlideDown);
                            layTextMain.setVisibility(View.GONE);
                        }
                        if (layStkrMain.getVisibility() == View.VISIBLE) {
                            layStkrMain.startAnimation(animSlideDown);
                            layStkrMain.setVisibility(View.GONE);
                        }

                        guideline.setVisibility(View.GONE);
                        bitmap = convertviewToBitmap(mainRelative);
                        llLogo.setVisibility(View.VISIBLE);
                        llLogo.setDrawingCacheEnabled(true);
                        Bitmap createBitmap = Bitmap.createBitmap(llLogo.getDrawingCache());
                        llLogo.setDrawingCacheEnabled(false);
                        llLogo.setVisibility(View.INVISIBLE);
                        withoutWatermark = bitmap;

                        if (!(appPreferenceClass.getBoolean("isAdsDisabled", false) || appPreferenceClass.getBoolean("removeWatermark", false))) {
                            bitmap = Glide_Image_Utils.mergelogoWithView(bitmap, createBitmap);
                        }

                        requestInternal_ExternalStoragePermission(1);

                        sweetAlertDialog.dismissWithAnimation();
                    }
                })
                .show();
    }

    public void dragStickerView(View view) {
        if (view != null) {
            float rotation;
            int[] iArr = new int[2];
            view.getLocationOnScreen(iArr);
            float width = (float) view.getWidth();
            float height = (float) view.getHeight();
            boolean z = view instanceof AutoStickerView;
            if (z) {
                rotation = view.getRotation();
            } else {
                rotation = view.getRotation();
            }
            int[] iArr2 = new int[2];
            this.scrollLayout.getLocationOnScreen(iArr2);
            this.parentY = (float) iArr2[1];
            float x = view.getX();
            float y = view.getY();
            float f = this.parentY;
            y += f;
            this.distance = f - ((float) Glide_Image_Utils.convertDpToPx(this, 50.0f));
            Matrix matrix = new Matrix();
            RectF rectF = new RectF(x, y, x + width, y + height);
            matrix.postRotate(rotation, x + (width / 2.0f), y + (height / 2.0f));
            matrix.mapRect(rectF);
            int i = iArr[1];
            y = Math.max(rectF.top, rectF.bottom);
            float scrollY = (float) this.scrollLayout.getScrollY();
            if (scrollY > 0.0f) {
                y -= scrollY;
            }
            int[] iArr3 = new int[2];
            if (z) {
                this.seekbarContainer.getLocationOnScreen(iArr3);
            } else {
                this.layTextEdit.getLocationOnScreen(iArr3);
            }
            float f2 = (float) iArr3[1];
            if (this.parentY + ((float) this.scrollLayout.getHeight()) < y) {
                y = this.parentY + ((float) this.scrollLayout.getHeight());
            }
            if (y > f2) {
                this.distanceScroll = (int) (y - f2);
                int i2 = this.distanceScroll;
                this.dsfc = i2;
                if (((float) i2) < this.distance) {
                    this.scrollLayout.setY((this.parentY - ((float) Glide_Image_Utils.convertDpToPx(this, 50.0f))) - ((float) this.distanceScroll));
                } else {
                    i2 = this.scrollLayout.getScrollY();
                    this.scrollLayout.setLayoutParams(new LayoutParams(-1, -2));
                    this.scrollLayout.postInvalidate();
                    this.scrollLayout.requestLayout();
                    i = (int) ((y - this.distance) - f2);
                    int height2 = this.scrollLayout.getHeight() - i;
                    this.distanceScroll = i2 + i;
                    this.scrollLayout.getLayoutParams().height = height2;
                    this.scrollLayout.postInvalidate();
                    this.scrollLayout.requestLayout();
                }
                this.scrollLayout.post(new Runnable() {
                    public void run() {
                        if (PosterMAKERActivity.this.scrollLayout.getY() < 0.0f) {
                            PosterMAKERActivity.this.scrollLayout.setY(0.0f);
                        }
                        PosterMAKERActivity.this.btn_bck1.performClick();
                    }
                });
            }
        }
    }

    private void displayPicImageDialog() {
        final Dialog dialog = new Dialog(this, R.style.ThemeWithCorners);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_pic_selection);
        TextView textView = dialog.findViewById(R.id.txtTitle);
        ImageView imageView = dialog.findViewById(R.id.iv_gallery);
        ImageView imageView2 = dialog.findViewById(R.id.iv_camera);
        ((TextView) dialog.findViewById(R.id.permission_des)).setTypeface(applyFontNormal());
        textView.setTypeface(adjustFontBold());
        RelativeLayout relativeLayout = dialog.findViewById(R.id.rv_lay);
        ((TextView) dialog.findViewById(R.id.tv_loading)).setTypeface(adjustFontBold());
        if (!NetworkConnectivityReceiver.isConnected() || this.prefs.getBoolean("isAdsDisabled", false)) {
            relativeLayout.setVisibility(View.GONE);
        }
        imageView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                PosterMAKERActivity.this.chooseGalleryPermission();
                dialog.dismiss();
            }
        });
        imageView2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                PosterMAKERActivity.this.chooseCameraPermission();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void ongetSticker() {
        this.colorType = "colored";
        applySticker("", "", AppConstants.bitmap);
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        int id = seekBar.getId();
        int i2 = 0;
        int childCount;
        View childAt;
        AutoStickerView autoStickerView;
        if (id == R.id.alpha_seekBar) {
            childCount = stickerLayout.getChildCount();
            while (i2 < childCount) {
                childAt = stickerLayout.getChildAt(i2);
                if (childAt instanceof AutoStickerView) {
                    autoStickerView = (AutoStickerView) childAt;
                    if (autoStickerView.checkBorderVisbilty()) {
                        autoStickerView.setSTAlphaProg(i);
                    }
                }
                i2++;
            }
        } else if (id != R.id.hue_seekBar) {
            IntelligentTVAutoFit intelligentTVAutoFit;
            switch (id) {
                case R.id.seek:
                    this.alpha = i;
                    if (SDK_INT >= 16) {
                        this.transImgage.setImageAlpha(this.alpha);
                    } else {
                        this.transImgage.setAlpha(this.alpha);
                    }
                    return;
                case R.id.seekBar2:
                    this.processs = i;
                    childCount = stickerLayout.getChildCount();
                    while (i2 < childCount) {
                        childAt = stickerLayout.getChildAt(i2);
                        if (childAt instanceof IntelligentTVAutoFit) {
                            intelligentTVAutoFit = (IntelligentTVAutoFit) childAt;
                            if (intelligentTVAutoFit.checkBorderVisibility()) {
                                intelligentTVAutoFit.setTVTextAlpha(i);
                            }
                        }
                        i2++;
                    }
                    return;
                case R.id.seekBar3:
                    childCount = stickerLayout.getChildCount();
                    while (i2 < childCount) {
                        childAt = stickerLayout.getChildAt(i2);
                        if (childAt instanceof IntelligentTVAutoFit) {
                            intelligentTVAutoFit = (IntelligentTVAutoFit) childAt;
                            if (intelligentTVAutoFit.checkBorderVisibility()) {
                                intelligentTVAutoFit.setTVBgAlpha(i);
                                this.backgroundAlpha = i;
                            }
                        }
                        i2++;
                    }
                    return;
                default:
                    switch (id) {
                        case R.id.seekBar_shadow:
                            childCount = stickerLayout.getChildCount();
                            while (i2 < childCount) {
                                childAt = stickerLayout.getChildAt(i2);
                                if (childAt instanceof IntelligentTVAutoFit) {
                                    intelligentTVAutoFit = (IntelligentTVAutoFit) childAt;
                                    if (intelligentTVAutoFit.checkBorderVisibility()) {
                                        intelligentTVAutoFit.setTVTextShadowProg(i);
                                        this.textShadowProg = i;
                                    }
                                }
                                i2++;
                            }
                            return;
                        case R.id.seekLetterSpacing:
                            this.letterSpacing = (float) (i / 3);
                            adustLetterApacing();
                            return;
                        case R.id.seekLineSpacing:
                            this.lineSpacing = (float) (i / 2);
                            adjustLineApacing();
                            return;
                        default:
                            switch (id) {
                                case R.id.seekShadowBlur:
                                    childCount = stickerLayout.getChildCount();
                                    while (i2 < childCount) {
                                        childAt = stickerLayout.getChildAt(i2);
                                        if (childAt instanceof IntelligentTVAutoFit) {
                                            intelligentTVAutoFit = (IntelligentTVAutoFit) childAt;
                                            if (intelligentTVAutoFit.checkBorderVisibility()) {
                                                intelligentTVAutoFit.applyTextShadowOpacity(i);
                                            }
                                        }
                                        i2++;
                                    }
                                    return;
                                case R.id.seekTextCurve:
                                    this.mRadius = seekBar.getProgress() - 360;
                                    childCount = this.mRadius;
                                    if (childCount <= 0 && childCount >= -8) {
                                        this.mRadius = -8;
                                    }
                                    childCount = stickerLayout.getChildCount();
                                    while (i2 < childCount) {
                                        View childAt2 = stickerLayout.getChildAt(i2);
                                        if (childAt2 instanceof IntelligentTVAutoFit) {
                                            IntelligentTVAutoFit intelligentTVAutoFit2 = (IntelligentTVAutoFit) childAt2;
                                            if (intelligentTVAutoFit2.checkBorderVisibility()) {
                                                intelligentTVAutoFit2.setTVDrawParams();
                                            }
                                        }
                                        i2++;
                                    }
                                    return;
                                case R.id.seek_blur:
                                    if (i != 0) {
                                        this.backgroundBlur.setVisibility(View.VISIBLE);
                                        this.min = i;
                                        if (SDK_INT >= 16) {
                                            this.backgroundBlur.setImageAlpha(i);
                                        } else {
                                            this.backgroundBlur.setAlpha(i);
                                        }
                                        return;
                                    }
                                    this.backgroundBlur.setVisibility(View.GONE);
                                    return;
                                case R.id.seek_tailys:
                                    this.backgroundBlur.setVisibility(View.GONE);
                                    this.seekValue = i;
                                    setTilesBG(this.curTileId);
                                    return;
                                default:
                                    return;
                            }
                    }
            }
        } else {
            childCount = stickerLayout.getChildCount();
            while (i2 < childCount) {
                childAt = stickerLayout.getChildAt(i2);
                if (childAt instanceof AutoStickerView) {
                    autoStickerView = (AutoStickerView) childAt;
                    if (autoStickerView.checkBorderVisbilty()) {
                        autoStickerView.set_Sticker_HueProg(i);
                    }
                }
                i2++;
            }
        }
    }

    private class TemplateLordAsync extends AsyncTask<String, String, Boolean> {

        int indx;
        String postion;

        private TemplateLordAsync() {
            this.indx = 0;
            this.postion = "1";
        }

        @Override
        public void onPreExecute() {
            super.onPreExecute();
            PosterMAKERActivity posterMAKERActivity = PosterMAKERActivity.this;
            posterMAKERActivity.dialogIs = new ProgressDialog(posterMAKERActivity);
            PosterMAKERActivity.this.dialogIs.setMessage(PosterMAKERActivity.this.getResources().getString(R.string.plzwait));
            PosterMAKERActivity.this.dialogIs.setCancelable(false);
            PosterMAKERActivity.this.dialogIs.show();
        }

        @Override
        public Boolean doInBackground(String... strArr) {
            if (PosterMAKERActivity.this.myDesignFlag == 0) {
                this.indx = Integer.parseInt(strArr[0]);
            } else {
                this.indx = 0;
            }
            Template_InfoData templateInfoData = (Template_InfoData) PosterMAKERActivity.this.templateList.get(this.indx);
            PosterMAKERActivity.this.templateId = templateInfoData.getTEMPLATE_INFO_ID();
            PosterMAKERActivity.this.frameName = templateInfoData.getFRAME__INFO_NAME();
            PosterMAKERActivity.this.tempPath = templateInfoData.get_INFO_TEMP_PATH();
            PosterMAKERActivity.this.ratio = templateInfoData.get_INFO_RATIO();
            PosterMAKERActivity.this.profile = templateInfoData.get_INFO_PROFILE_TYPE();
            String seek_value = templateInfoData.get_INFO_SEEK_VALUE();
            PosterMAKERActivity.this.hex = templateInfoData.get__INFO_TEMPCOLOR();
            PosterMAKERActivity.this.overlayName = templateInfoData.get_INFO_OVERLAY_NAME();
            PosterMAKERActivity.this.overlayOpacty = templateInfoData.get_INFO_OVERLAY_OPACITY();
            PosterMAKERActivity.this.overlayBlur = templateInfoData.get_INFO_OVERLAY_BLUR();
            PosterMAKERActivity.this.seekValue = Integer.parseInt(seek_value);
            return Boolean.valueOf(true);
        }

        @Obsolete
        public void onPostExecute(Boolean bool) {
            super.onPostExecute(bool);
            String valueOf = String.valueOf(Integer.parseInt(this.postion));
            if (!(PosterMAKERActivity.this.templateList.get(this.indx)).get__INFO_TYPE().equals("USER")) {
                return;
            }
            PosterMAKERActivity posterMAKERActivity;
            if (PosterMAKERActivity.this.myDesignFlag != 0) {
                posterMAKERActivity = PosterMAKERActivity.this;
                posterMAKERActivity.drawIntelligentView(posterMAKERActivity.ratio, valueOf, PosterMAKERActivity.this.frameName, "created");
                return;
            }
            posterMAKERActivity = PosterMAKERActivity.this;
            posterMAKERActivity.drawBGImageFromDp(posterMAKERActivity.ratio, valueOf, PosterMAKERActivity.this.tempPath, "created");
        }
    }

    public void startCustomActivity(Bundle bundle, Intent intent) {
        bundle = intent.getExtras();
        this.profile = "no";
        if (this.profile.equals("no")) {
            this.showtailsSeek = false;
            this.ratio = "";
            this.position = "1";
            this.profile = "Temp_Path";
            this.hex = "";
            ResizeViewLayout(Glide_Image_Utils.resizeImageBitmap(AppConstants.bitmap, (int) this.screenWidth, (int) this.screenHeight), "nonCreated");
            return;
        }
        if (this.profile.equals("Texture")) {
            this.showtailsSeek = true;
            this.layHandletails.setVisibility(View.VISIBLE);
        } else {
            this.showtailsSeek = false;
            this.layHandletails.setVisibility(View.GONE);
        }
        this.ratio = bundle.getString("ratio");
        String string = bundle.getString("position");
        this.hex = bundle.getString("color");
        drawBGImageFromDp(this.ratio, string, this.profile, "nonCreated");
    }

    public void onClickGalleryButton() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.PICK");
        startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.select_picture)), SELECT_PICTURE_GALLERY);
    }

    public void onClickCameraButton() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        this.f = new File(Environment.getExternalStorageDirectory(), ".temp.jpg");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getApplicationContext().getPackageName());
        stringBuilder.append(".provider");
        intent.putExtra("output", FileProvider.getUriForFile(this, stringBuilder.toString(), this.f));
        startActivityForResult(intent, SELECT_PICTURE_CAMERA);
    }


    public void errorTempInfoDialog() {
        final Dialog dialog = new Dialog(this, R.style.ThemeWithCorners);
        dialog.requestWindowFeature(1);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.error_dialog);
        ((TextView) dialog.findViewById(R.id.txterorr)).setTypeface(this.ttfHeader);
        ((TextView) dialog.findViewById(R.id.txt)).setTypeface(this.ttf);
        Button button = (Button) dialog.findViewById(R.id.btn_ok_e);
        button.setTypeface(this.ttf);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                PosterMAKERActivity.this.finish();
            }
        });
        button = (Button) dialog.findViewById(R.id.btn_conti);
        button.setTypeface(this.ttf);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void useControl2() {
        this.txtTextControls.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsControl.setTextColor(getResources().getColor(R.color.tabtextcolor_selected));
        this.txtFontsStyle.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsSpacing.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsCurve.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtColorsControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtShadowControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtBgControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
    }

    public void useControl3() {
        this.txtTextControls.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsStyle.setTextColor(getResources().getColor(R.color.tabtextcolor_selected));
        this.txtFontsSpacing.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsCurve.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtColorsControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtShadowControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtBgControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
    }

    public void useControl4() {
        this.txtTextControls.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsStyle.setTextColor(getResources().getColor(R.color.tabtextcolor_selected));
        this.txtFontsSpacing.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsCurve.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtColorsControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtShadowControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtBgControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
    }


    private Bitmap gaussinBlur(Activity activity, Bitmap bitmap) {
        try {
            GPUImage gPUImage = new GPUImage(activity);
            GPUImageGaussianBlurFilter gPUImageGaussianBlurFilter = new GPUImageGaussianBlurFilter();
            gPUImage.setFilter(gPUImageGaussianBlurFilter);
            new Adjust_Filter(gPUImageGaussianBlurFilter).adjust(150);
            gPUImage.requestRender();
            return gPUImage.getBitmapWithFilterApplied(bitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onDestroy() {


        super.onDestroy();

        try {

            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }

            this.adapter = null;
            this.userImage = null;
            this.selectBackgnd = null;
            this.selectEffect = null;
            this.addSticker = null;
            this.llLogo = null;
            this.addText = null;
            this.layoutEffectView = null;
            this.layoutFilterView = null;
            this.centerRelative = null;
            this.layRemove = null;
            this.txtSticker = null;
            this.rellative = null;
            this.layEffects = null;
            this.laySticker = null;
            this.layHandletails = null;
            this.layTextMain = null;
            this.layStkrMain = null;
            this.animSlideDown = null;
            this.animSlideUp = null;
            this.alphaSeekbar = null;
            this.hueSeekbar = null;
            this.seekBar3 = null;
            this.seekLetterSpacing = null;
            this.seekLineSpacing = null;
            this.seekShadowBlur = null;
            this.seekBarShadow = null;
            this.seekTextCurve = null;
            this.layTextEdit = null;
            this.shapeRelative = null;
            this.seekbarContainer = null;
            this.seekBlur = null;
            this.layBackground = null;
            this.transImgage = null;
            this.guideline = null;
            this.ttf = null;
            this.ttfHeader = null;
            this.selectedView = null;
            this.focusedCopy = null;
            this.pallete = null;
            this.imgOK = null;
            this.viewAllFrame = null;
            this.scrollLayout = null;
            if (this.stickerObject != null) {
                this.stickerObject.clear();
            }
            if (this.textInfoArrayList != null) {
                this.textInfoArrayList.clear();
            }
            if (this.stickerInfoArrayList != null) {
                this.stickerInfoArrayList.clear();
            }
            if (this.templateList != null) {
                this.templateList.clear();
            }
            this.fontsShow = null;
            this.colorShow = null;
            this.sadowShow = null;
            this.bgShow = null;
            this.controlsShow = null;
            this.layColor = null;
            this.controlsShowStkr = null;
            this.layColorOacity = null;
            this.layControlStkr = null;
            this.layDuplicateStkr = null;
            this.layEdit = null;
            this.layDuplicateText = null;
            this.adapter = null;
            this.btn_bck1 = null;
            this.listFragment = null;
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Glide.get(PosterMAKERActivity.this).clearDiskCache();
                        Thread.sleep(100);
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }
            }).start();
            Glide.get(this).clearMemory();
            releaseMemory();
        } catch (Throwable th) {
            th.printStackTrace();
        }
        releaseMemory();
    }

    public void releaseMemory() {
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
    }

    private void unnecessaryClick() {
        this.layEffects.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
            }
        });
        this.layTextEdit.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
            }
        });
        this.seekbarContainer.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
            }
        });
        this.seekbarHandle.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
            }
        });
        this.seekLetterSpacing.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
            }
        });
        this.seekLineSpacing.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
            }
        });
        this.verticalSeekBar.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
            }
        });
        this.seekBarShadow.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
            }
        });
        this.seekShadowBlur.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
            }
        });
        this.seekBar3.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
            }
        });
        this.seekBlur.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
            }
        });
        this.seek.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
            }
        });
    }

    @Override
    public void onGetColor(int i, String str, int i2) {
        if (i != 0) {
            int childCount = stickerLayout.getChildCount();
            int i3 = 0;
            View childAt;
            IntelligentTVAutoFit intelligentTVAutoFit;
            if (str.equals("txtShadow")) {
                while (i3 < childCount) {
                    childAt = stickerLayout.getChildAt(i3);
                    if (childAt instanceof IntelligentTVAutoFit) {
                        ((IntelligentTVAutoFit) stickerLayout.getChildAt(i2)).applyBorderVisibility(true);
                        intelligentTVAutoFit = (IntelligentTVAutoFit) childAt;
                        if (intelligentTVAutoFit.checkBorderVisibility()) {
                            this.textShadowColor = i;
                            intelligentTVAutoFit.applyTextShadowColor(i);
                        }
                    }
                    i3++;
                }
            } else if (str.equals("txtBg")) {
                while (i3 < childCount) {
                    childAt = stickerLayout.getChildAt(i3);
                    if (childAt instanceof IntelligentTVAutoFit) {
                        ((IntelligentTVAutoFit) stickerLayout.getChildAt(i2)).applyBorderVisibility(true);
                        intelligentTVAutoFit = (IntelligentTVAutoFit) childAt;
                        if (intelligentTVAutoFit.checkBorderVisibility()) {
                            this.backgroundColor = i;
                            this.backgroundDrawable = "0";
                            intelligentTVAutoFit.setBackgroundColor(i);
                            intelligentTVAutoFit.setTVBgAlpha(this.seekBar3.getProgress());
                        }
                    }
                    i3++;
                }
            } else {
                childAt = stickerLayout.getChildAt(i2);
                if (childAt instanceof IntelligentTVAutoFit) {
                    ((IntelligentTVAutoFit) stickerLayout.getChildAt(i2)).applyBorderVisibility(true);
                    IntelligentTVAutoFit intelligentTVAutoFit2 = (IntelligentTVAutoFit) childAt;
                    if (intelligentTVAutoFit2.checkBorderVisibility()) {
                        this.textColor = i;
                        this.textColorSet = i;
                        intelligentTVAutoFit2.setTVTextColor(i);
                    }
                }
                if (childAt instanceof AutoStickerView) {
                    ((AutoStickerView) stickerLayout.getChildAt(i2)).applyBorderVisibility(true);
                    AutoStickerView autoStickerView = (AutoStickerView) childAt;
                    if (autoStickerView.checkBorderVisbilty()) {
                        this.stkrColorSet = i;
                        autoStickerView.setSTColor(i);
                    }
                }
            }
        } else {
            hideScroll();
            if (this.layTextMain.getVisibility() == View.VISIBLE) {
                this.layTextMain.startAnimation(this.animSlideDown);
                this.layTextMain.setVisibility(View.GONE);
            }
            if (this.layStkrMain.getVisibility() == View.VISIBLE) {
                this.layStkrMain.startAnimation(this.animSlideDown);
                this.layStkrMain.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onSnapFilter(int i, int i2, String str, String str2) {
        this.laySticker.setVisibility(View.GONE);
        btnLayer.setVisibility(View.VISIBLE);
        if (this.layTextMain.getVisibility() == View.VISIBLE) {
            this.layTextMain.startAnimation(this.animSlideDown);
            this.layTextMain.setVisibility(View.GONE);
        }
        if (i2 == 104) {
            if (str != null) {
                this.isBackground = true;
                Uri fromFile = Uri.fromFile(new File(str));
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("SampleCropImage");
                stringBuilder.append(System.currentTimeMillis());
                stringBuilder.append(".png");
                Uri fromFile2 = Uri.fromFile(new File(getCacheDir(), stringBuilder.toString()));
                String[] split = this.ratio.split(":");
                int parseInt = Integer.parseInt(split[0]);
                int parseInt2 = Integer.parseInt(split[1]);
                UCrop.Options options = new UCrop.Options();
                options.setCompressionFormat(CompressFormat.PNG);
                options.setToolbarColor(getResources().getColor(R.color.colorPrimary));
                options.setActiveWidgetColor(getResources().getColor(R.color.colorPrimary));
                options.setFreeStyleCropEnabled(false);
                UCrop.of(fromFile, fromFile2).withOptions(options).withAspectRatio((float) parseInt, (float) parseInt2).start(this);
            }
            return;
        }
        StringBuilder stringBuilder2;
        if (!str.equals("")) {
            this.imgOK.setVisibility(View.VISIBLE);
            if (getSupportFragmentManager().getBackStackEntryCount() >= 1) {
                getSupportFragmentManager().popBackStack();
            }
            this.colorType = str2;
            applySticker("", str, null);
        } else if (i2 == 33) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("sticker_offer_");
            stringBuilder2.append(i + 1);
            applyDrawable("colored", stringBuilder2.toString());
        } else if (i2 == 34) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("sticker_sale_");
            stringBuilder2.append(i + 1);
            applyDrawable("colored", stringBuilder2.toString());
        } else if (i2 == 35) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("sticker_banner_");
            stringBuilder2.append(i + 1);
            applyDrawable("colored", stringBuilder2.toString());
        } else if (i2 == 36) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("sticker_ribbon_");
            stringBuilder2.append(i + 1);
            applyDrawable("colored", stringBuilder2.toString());
        } else if (i2 == 37) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("sticker_sport_");
            stringBuilder2.append(i + 1);
            applyDrawable("colored", stringBuilder2.toString());
        } else if (i2 == 38) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("sticker_birthday_");
            stringBuilder2.append(i + 1);
            applyDrawable("colored", stringBuilder2.toString());
        } else if (i2 == 39) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("sticker_decoration_");
            stringBuilder2.append(i + 1);
            applyDrawable("colored", stringBuilder2.toString());
        } else if (i2 == 40) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("sticker_party_");
            stringBuilder2.append(i + 1);
            applyDrawable("colored", stringBuilder2.toString());
        } else if (i2 == 41) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("sticker_love_");
            stringBuilder2.append(i + 1);
            applyDrawable("colored", stringBuilder2.toString());
        } else if (i2 == 42) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("sticker_music_");
            stringBuilder2.append(i + 1);
            applyDrawable("colored", stringBuilder2.toString());
        } else if (i2 == 43) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("sticker_festival_");
            stringBuilder2.append(i + 1);
            applyDrawable("colored", stringBuilder2.toString());
        } else if (i2 == 44) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("sticker_nature_");
            stringBuilder2.append(i + 1);
            applyDrawable("colored", stringBuilder2.toString());
        } else if (i2 == 45) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("sticker_car_");
            stringBuilder2.append(i + 1);
            applyDrawable("colored", stringBuilder2.toString());
        } else if (i2 == 46) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("sticker_emoji_");
            stringBuilder2.append(i + 1);
            applyDrawable("colored", stringBuilder2.toString());
        } else if (i2 == 47) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("sticker_college_");
            stringBuilder2.append(i + 1);
            applyDrawable("colored", stringBuilder2.toString());
        } else if (i2 == 48) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("sticker_coffe_");
            stringBuilder2.append(i + 1);
            applyDrawable("colored", stringBuilder2.toString());
        } else if (i2 == 49) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("sticker_halloween_");
            stringBuilder2.append(i + 1);
            applyDrawable("colored", stringBuilder2.toString());
        } else if (i2 == 50) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("shap");
            stringBuilder2.append(i + 1);
            applyDrawable("white", stringBuilder2.toString());
        } else if (i2 == 51) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("sticker_animal_");
            stringBuilder2.append(i + 1);
            applyDrawable("colored", stringBuilder2.toString());
        } else if (i2 == 52) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("sticker_cartoon_");
            stringBuilder2.append(i + 1);
            applyDrawable("colored", stringBuilder2.toString());
        } else {
            this.colorType = "colored";
        }
    }


    public void useControl1() {
        this.txtTextControls.setTextColor(getResources().getColor(R.color.tabtextcolor_selected));
        this.txtFontsControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsStyle.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsCurve.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtColorsControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtShadowControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtBgControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
    }

    public void useControl5() {
        this.txtTextControls.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsStyle.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsSpacing.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsCurve.setTextColor(getResources().getColor(R.color.tabtextcolor_selected));
        this.txtColorsControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtShadowControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtBgControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
    }

    public void useControl6() {
        this.txtTextControls.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsStyle.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsSpacing.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsCurve.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtColorsControl.setTextColor(getResources().getColor(R.color.tabtextcolor_selected));
        this.txtShadowControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtBgControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
    }

    public void useControl7() {
        this.txtTextControls.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsStyle.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtColorsControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsCurve.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsSpacing.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtShadowControl.setTextColor(getResources().getColor(R.color.tabtextcolor_selected));
        this.txtBgControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
    }

    public void useControl8() {
        this.txtTextControls.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsStyle.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtColorsControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsCurve.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtFontsSpacing.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtShadowControl.setTextColor(getResources().getColor(R.color.tabtextcolor_normal));
        this.txtBgControl.setTextColor(getResources().getColor(R.color.tabtextcolor_selected));
    }


    @Override
    public void onSnapFilter(ArrayList<BG_Image> arrayList, int i, String str) {
        if (i == 0) {
            viewMoreSticker(arrayList, str);
        } else {
            viewMore(arrayList);
        }
    }

    @Override
    public void onSnapFilter(Uri uri, int i, int i2, int i3, int i4, boolean z) {
        if (getSupportFragmentManager().getBackStackEntryCount() >= 1) {
            getSupportFragmentManager().popBackStack();
        }
        if (z) {
            this.colorType = "colored";
            if (uri != null) {
                applySticker("", uri.getPath(), null);
                return;
            }
            return;
        }
        Glide.with((FragmentActivity) this).asBitmap().load(uri).apply(RequestOptions.bitmapTransform(new Round_Corner_Transformation(this, i, i2, i3, i4))).listener(new RequestListener<Bitmap>() {
            public boolean onLoadFailed(@Nullable GlideException glideException, Object obj, Target<Bitmap> target, boolean z) {
                try {
                    PosterMAKERActivity.this.dialogIs.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                PosterMAKERActivity.this.showErrorDialog();
                return false;
            }

            public boolean onResourceReady(Bitmap bitmap, Object obj, Target<Bitmap> target, DataSource dataSource, boolean z) {
                PosterMAKERActivity.this.colorType = "colored";
                PosterMAKERActivity.this.applySticker("", "", bitmap);
                return false;
            }
        }).submit();
    }

    private void viewMoreSticker(ArrayList<BG_Image> arrayList, String str) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();
        Fragment_More_Sticker fragmentMoreSticker = (Fragment_More_Sticker) supportFragmentManager.findFragmentByTag("sticker_list");
        if (fragmentMoreSticker != null) {
            beginTransaction.remove(fragmentMoreSticker);
        }
        Fragment_More_Sticker newInstance = Fragment_More_Sticker.newInstance(arrayList, str);
        this.mFragments.add(new WeakReference(newInstance));
        beginTransaction.add(R.id.frameContainerSticker, newInstance, "sticker_list");
        beginTransaction.addToBackStack("sticker_list");
        try {
            beginTransaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void viewMore(ArrayList<BG_Image> arrayList) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();
        Fragment_BG2 fragmentBG2 = (Fragment_BG2) supportFragmentManager.findFragmentByTag("back_category_frgm_2");
        if (fragmentBG2 != null) {
            beginTransaction.remove(fragmentBG2);
        }
        Fragment_BG2 newInstance = Fragment_BG2.newInstance(arrayList);
        this.mFragments.add(new WeakReference(newInstance));
        beginTransaction.add(R.id.frameContainerBackground, newInstance, "back_category_frgm_2");
        beginTransaction.addToBackStack("back_category_frgm_2");
        try {
            beginTransaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class StickersAsyncTask extends AsyncTask<String, String, Boolean> {
        private StickersAsyncTask() {
        }


        @Override
        public void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        public void onPostExecute(Boolean bool) {
            super.onPostExecute(bool);
            try {
                PosterMAKERActivity.this.dialogIs.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
            ArrayList arrayList = new ArrayList(stickerObject.keySet());
            Collections.sort(arrayList);
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                Object obj = PosterMAKERActivity.this.stickerObject.get(arrayList.get(i));
                if (obj instanceof ViewElementInfo) {
                    ViewElementInfo viewElementInfo = (ViewElementInfo) obj;
                    String stkr_path = viewElementInfo.getSTKR_PATH();
                    AutoStickerView autoStickerView;
                    if (stkr_path.equals("")) {
                        try {
                            autoStickerView = new AutoStickerView(PosterMAKERActivity.this);
                            PosterMAKERActivity.stickerLayout.addView(autoStickerView);
                            autoStickerView.optimizeSTScreen(PosterMAKERActivity.this.screenWidth, PosterMAKERActivity.this.screenHeight);
                            autoStickerView.set_STViewWH((float) PosterMAKERActivity.this.mainRelative.getWidth(), (float) PosterMAKERActivity.this.mainRelative.getHeight());
                            autoStickerView.setFromAddText(false);
                            autoStickerView.set_STView_ComponentInfo((ViewElementInfo) obj);
                            autoStickerView.setId(View_ID_Finder.generateViewId());
                            autoStickerView.optimizeSTView(PosterMAKERActivity.this.wr, PosterMAKERActivity.this.hr);
                            autoStickerView.setOnTouchCallbackListener(PosterMAKERActivity.this);
                            autoStickerView.applyBorderVisibility(false);

                            autoStickerView.isMultiTouchEnabled = autoStickerView.applyDefaultTouchListener(false);

                            PosterMAKERActivity.this.sizeFull = PosterMAKERActivity.this.sizeFull + 1;
                        } catch (ArrayIndexOutOfBoundsException e2) {
                            e2.printStackTrace();
                        }
                    } else {
                        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), ".Poster Design Stickers/category1");
                        if (!file.exists() && !file.mkdirs()) {
                            Log.d("", "Can't create directory to save image.");
                            PosterMAKERActivity posterMAKERActivity = PosterMAKERActivity.this;
                            Toast.makeText(posterMAKERActivity, posterMAKERActivity.getResources().getString(R.string.create_dir_err), Toast.LENGTH_SHORT).show();
                            return;
                        } else if (new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), ".Poster Design Stickers/category1").exists()) {
                            file = new File(stkr_path);
                            if (file.exists()) {
                                autoStickerView = new AutoStickerView(PosterMAKERActivity.this);
                                PosterMAKERActivity.stickerLayout.addView(autoStickerView);
                                autoStickerView.optimizeSTScreen(PosterMAKERActivity.this.screenWidth, PosterMAKERActivity.this.screenHeight);
                                autoStickerView.set_STViewWH((float) PosterMAKERActivity.this.mainRelative.getWidth(), (float) PosterMAKERActivity.this.mainRelative.getHeight());
                                autoStickerView.setFromAddText(false);

                                autoStickerView.isMultiTouchEnabled = autoStickerView.applyDefaultTouchListener(false);

                                autoStickerView.set_STView_ComponentInfo(viewElementInfo);
                                autoStickerView.setId(View_ID_Finder.generateViewId());
                                autoStickerView.optimizeSTView(PosterMAKERActivity.this.wr, PosterMAKERActivity.this.hr);
                                autoStickerView.setOnTouchCallbackListener(PosterMAKERActivity.this);
                                autoStickerView.applyBorderVisibility(false);
                                PosterMAKERActivity.this.sizeFull = PosterMAKERActivity.this.sizeFull + 1;
                            } else if (file.getName().replace(".png", "").length() < 7) {
                                PosterMAKERActivity.this.dialogShow = false;
                                new SaveAsyncStickers(obj).execute(new String[]{stkr_path});
                            } else {
                                if (PosterMAKERActivity.this.OneShow) {
                                    PosterMAKERActivity.this.dialogShow = true;
                                    PosterMAKERActivity.this.errorTempInfoDialog();
                                    PosterMAKERActivity.this.OneShow = false;
                                }
                                PosterMAKERActivity.this.sizeFull = PosterMAKERActivity.this.sizeFull + 1;
                            }
                        } else {
                            file = new File(stkr_path);
                            if (file.exists()) {
                                autoStickerView = new AutoStickerView(PosterMAKERActivity.this);
                                PosterMAKERActivity.stickerLayout.addView(autoStickerView);
                                autoStickerView.optimizeSTScreen(PosterMAKERActivity.this.screenWidth, PosterMAKERActivity.this.screenHeight);
                                autoStickerView.set_STViewWH((float) PosterMAKERActivity.this.mainRelative.getWidth(), (float) PosterMAKERActivity.this.mainRelative.getHeight());
                                autoStickerView.setFromAddText(false);

                                autoStickerView.isMultiTouchEnabled = autoStickerView.applyDefaultTouchListener(false);

                                autoStickerView.set_STView_ComponentInfo(viewElementInfo);
                                autoStickerView.setId(View_ID_Finder.generateViewId());
                                autoStickerView.optimizeSTView(PosterMAKERActivity.this.wr, PosterMAKERActivity.this.hr);
                                autoStickerView.setOnTouchCallbackListener(PosterMAKERActivity.this);
                                autoStickerView.applyBorderVisibility(false);
                                PosterMAKERActivity.this.sizeFull = PosterMAKERActivity.this.sizeFull + 1;
                            } else if (file.getName().replace(".png", "").length() < 7) {
                                PosterMAKERActivity.this.dialogShow = false;
                                new SaveAsyncStickers(obj).execute(new String[]{stkr_path});
                            } else {
                                if (PosterMAKERActivity.this.OneShow) {
                                    PosterMAKERActivity.this.dialogShow = true;
                                    PosterMAKERActivity.this.errorTempInfoDialog();
                                    PosterMAKERActivity.this.OneShow = false;
                                }
                                PosterMAKERActivity.this.sizeFull = PosterMAKERActivity.this.sizeFull + 1;
                            }
                        }
                    }
                } else {
                    IntelligentTVAutoFit intelligentTVAutoFit = new IntelligentTVAutoFit(PosterMAKERActivity.this);
                    PosterMAKERActivity.stickerLayout.addView(intelligentTVAutoFit);
                    intelligentTVAutoFit.setViewWH((float) PosterMAKERActivity.this.mainRelative.getWidth(), (float) PosterMAKERActivity.this.mainRelative.getHeight());
                    intelligentTVAutoFit.setTVFromAddText(false);
                    TV_Info_Adapter textViewInfoAdapter = (TV_Info_Adapter) obj;
                    intelligentTVAutoFit.setTVTextInfo(textViewInfoAdapter, false);
                    intelligentTVAutoFit.setId(View_ID_Finder.generateViewId());
                    intelligentTVAutoFit.optimizeTV(PosterMAKERActivity.this.wr, PosterMAKERActivity.this.hr);
                    intelligentTVAutoFit.setOnTV_TouchCallbackListener(PosterMAKERActivity.this);
                    intelligentTVAutoFit.applyBorderVisibility(false);
                    PosterMAKERActivity.this.font_Name = textViewInfoAdapter.get_TV_FONT_NAME();
                    PosterMAKERActivity.this.textColor = textViewInfoAdapter.getTEXT_COLOR();
                    PosterMAKERActivity.this.textShadowColor = textViewInfoAdapter.getTV_SHADOW_COLOR();
                    PosterMAKERActivity.this.textShadowProg = textViewInfoAdapter.getTV_SHADOW_PROG();
                    PosterMAKERActivity.this.textAlpha = textViewInfoAdapter.getTEXT_ALPHA();
                    PosterMAKERActivity.this.backgroundDrawable = textViewInfoAdapter.getTV_BG_DRAWABLE();
                    PosterMAKERActivity.this.backgroundAlpha = textViewInfoAdapter.getTV_BG_ALPHA();
                    PosterMAKERActivity.this.rotation = textViewInfoAdapter.get_TV_ROTATION();
                    PosterMAKERActivity.this.backgroundColor = textViewInfoAdapter.getTV_BG_COLOR();
                    PosterMAKERActivity.this.sizeFull = PosterMAKERActivity.this.sizeFull + 1;
                }
            }
            if (PosterMAKERActivity.this.stickerObject.size() == PosterMAKERActivity.this.sizeFull && PosterMAKERActivity.this.dialogShow) {
                PosterMAKERActivity.this.dialogIs.dismiss();
                YoYo.with(Techniques.Shake).duration(700).repeat(1).playOn(PosterMAKERActivity.stickerLayout);
            }
        }

        @Override
        public Boolean doInBackground(String... strArr) {
            ArrayList textInfoList;
            ArrayList componentInfoList;
            DB_Handler dbHandler = DB_Handler.getDatabaseHandler(PosterMAKERActivity.this.getApplicationContext());
            if (PosterMAKERActivity.this.myDesignFlag == 0) {
                textInfoList = dbHandler.extract_Text_Info_List(PosterMAKERActivity.this.templateId);
                componentInfoList = dbHandler.extract_Component_InfoList(PosterMAKERActivity.this.templateId, "STICKER");
            } else {
                float parseFloat;
//                Crashlytics.setString("post id", String.valueOf(PosterMAKERActivity.this.postId));
                textInfoList = new ArrayList();
                componentInfoList = new ArrayList();
                for (int i = 0; i < PosterMAKERActivity.this.stickerInfoArrayList.size(); i++) {
                    PosterMAKERActivity posterMAKERActivity = PosterMAKERActivity.this;
                    int newWidht = posterMAKERActivity.getNewWidht(Float.parseFloat(stickerInfoArrayList.get(i).getSTICKER_x_pos()), Float.parseFloat(stickerInfoArrayList.get(i).getSTICKER_width()));
                    PosterMAKERActivity posterMAKERActivity2 = PosterMAKERActivity.this;
                    int newHeight = posterMAKERActivity2.getNewHeight(Float.parseFloat(((StickerInfo) posterMAKERActivity2.stickerInfoArrayList.get(i)).getSTICKER_y_pos()), Float.parseFloat(((StickerInfo) PosterMAKERActivity.this.stickerInfoArrayList.get(i)).getSTICKER_height()));
                    int i2 = newWidht < 10 ? 20 : (newWidht <= 10 || newWidht > 20) ? newWidht : 35;
                    int i3 = newHeight < 10 ? 20 : (newHeight <= 10 || newHeight > 20) ? newHeight : 35;
                    String st_field2 = ((StickerInfo) PosterMAKERActivity.this.stickerInfoArrayList.get(i)).getSTICKER_field2() != null ? ((StickerInfo) PosterMAKERActivity.this.stickerInfoArrayList.get(i)).getSTICKER_field2() : "";
                    parseFloat = (((StickerInfo) PosterMAKERActivity.this.stickerInfoArrayList.get(i)).getSTICKER_rotation() == null || ((StickerInfo) PosterMAKERActivity.this.stickerInfoArrayList.get(i)).getSTICKER_rotation().equals("")) ? 0.0f : Float.parseFloat(((StickerInfo) PosterMAKERActivity.this.stickerInfoArrayList.get(i)).getSTICKER_rotation());
                    int access$6500 = PosterMAKERActivity.this.postId;
                    posterMAKERActivity2 = PosterMAKERActivity.this;
                    float xpos = posterMAKERActivity2.getXpos(Float.parseFloat(((StickerInfo) posterMAKERActivity2.stickerInfoArrayList.get(i)).getSTICKER_x_pos()));
                    posterMAKERActivity2 = PosterMAKERActivity.this;

                    componentInfoList.add(new ViewElementInfo(access$6500, xpos, posterMAKERActivity2.getYpos(Float.parseFloat(((StickerInfo) posterMAKERActivity2.stickerInfoArrayList.get(i)).getSTICKER_y_pos())), i2, i3, parseFloat, 0.0f, "", "STICKER", Integer.parseInt(((StickerInfo) PosterMAKERActivity.this.stickerInfoArrayList.get(i)).getSt_order()), 0, 255, 0, 0, 0, 0, ((StickerInfo) PosterMAKERActivity.this.stickerInfoArrayList.get(i)).getSTICKER_image(), "colored", 1, 0, st_field2, "", "", null, null));
                }
                for (int i4 = 0; i4 < PosterMAKERActivity.this.textInfoArrayList.size(); i4++) {
                    PosterMAKERActivity posterMAKERActivity3 = PosterMAKERActivity.this;
                    parseFloat = posterMAKERActivity3.getXpos(Float.parseFloat(((Text_Info) posterMAKERActivity3.textInfoArrayList.get(i4)).getTxt_x_pos()));
                    posterMAKERActivity3 = PosterMAKERActivity.this;
                    float ypos = posterMAKERActivity3.getYpos(Float.parseFloat(((Text_Info) posterMAKERActivity3.textInfoArrayList.get(i4)).getTxt_y_pos()));
                    posterMAKERActivity3 = PosterMAKERActivity.this;
                    int newWidht2 = posterMAKERActivity3.getNewWidht(Float.parseFloat(((Text_Info) posterMAKERActivity3.textInfoArrayList.get(i4)).getTxt_x_pos()), Float.parseFloat(((Text_Info) PosterMAKERActivity.this.textInfoArrayList.get(i4)).getTxt_width()));
                    posterMAKERActivity3 = PosterMAKERActivity.this;
                    textInfoList.add(new TV_Info_Adapter(PosterMAKERActivity.this.postId, ((Text_Info) PosterMAKERActivity.this.textInfoArrayList.get(i4)).getText(), ((Text_Info) PosterMAKERActivity.this.textInfoArrayList.get(i4)).getFont_family(), Color.parseColor(((Text_Info) PosterMAKERActivity.this.textInfoArrayList.get(i4)).getTxt_color()), 100, -16777216, 0, "0", -16777216, 0, parseFloat, ypos, newWidht2, posterMAKERActivity3.getNewHeightText(Float.parseFloat(((Text_Info) posterMAKERActivity3.textInfoArrayList.get(i4)).getTxt_y_pos()), Float.parseFloat(((Text_Info) PosterMAKERActivity.this.textInfoArrayList.get(i4)).getTxt_height())), Float.parseFloat(((Text_Info) PosterMAKERActivity.this.textInfoArrayList.get(i4)).getTxt_rotation()), "TEXT", Integer.parseInt(((Text_Info) PosterMAKERActivity.this.textInfoArrayList.get(i4)).getTxt_order()), 0, 0, 0, 0, 0, "", "", ""));
                }
            }
            dbHandler.close();
            PosterMAKERActivity.this.stickerObject = new HashMap();
            Iterator it = textInfoList.iterator();
            while (it.hasNext()) {
                TV_Info_Adapter textViewInfoAdapter = (TV_Info_Adapter) it.next();
                stickerObject.put(Integer.valueOf(textViewInfoAdapter.getTV_ORDER()), textViewInfoAdapter);
            }
            it = componentInfoList.iterator();
            while (it.hasNext()) {
                ViewElementInfo viewElementInfo = (ViewElementInfo) it.next();
                stickerObject.put(Integer.valueOf(viewElementInfo.getORDER()), viewElementInfo);
            }
            return Boolean.valueOf(true);
        }

    }

}
