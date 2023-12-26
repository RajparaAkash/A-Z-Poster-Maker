package com.letsmake.atoz.design.editor_intelligence;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.michael.easydialog.EasyDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;

import com.letsmake.atoz.design.Application;
import com.letsmake.atoz.design.R;
import com.letsmake.atoz.design.app_utils.ExifRotationUtils;
import com.letsmake.atoz.design.imageloader.Glide_Image_Utils;

public class AppConstants {

    public static int[] overlayArr = new int[]{R.drawable.thumbnail_filter_1, R.drawable.thumbnail_filter_2, R.drawable.thumbnail_filter_3, R.drawable.thumbnail_filter_4, R.drawable.thumbnail_filter_5, R.drawable.thumbnail_filter_6, R.drawable.thumbnail_filter_7, R.drawable.thumbnail_filter_8, R.drawable.thumbnail_filter_9, R.drawable.thumbnail_filter_10, R.drawable.thumbnail_filter_11, R.drawable.thumbnail_filter_12, R.drawable.thumbnail_filter_13, R.drawable.thumbnail_filter_14, R.drawable.thumbnail_filter_15, R.drawable.thumbnail_filter_16, R.drawable.thumbnail_filter_17, R.drawable.thumbnail_filter_18, R.drawable.thumbnail_filter_19, R.drawable.thumbnail_filter_20, R.drawable.thumbnail_filter_21, R.drawable.thumbnail_filter_22, R.drawable.thumbnail_filter_23, R.drawable.thumbnail_filter_24, R.drawable.thumbnail_filter_25, R.drawable.thumbnail_filter_26, R.drawable.thumbnail_filter_27, R.drawable.thumbnail_filter_28, R.drawable.thumbnail_filter_29, R.drawable.thumbnail_filter_30, R.drawable.thumbnail_filter_31, R.drawable.thumbnail_filter_32, R.drawable.thumbnail_filter_33, R.drawable.thumbnail_filter_34, R.drawable.thumbnail_filter_35, R.drawable.thumbnail_filter_36, R.drawable.thumbnail_filter_37, R.drawable.thumbnail_filter_38, R.drawable.thumbnail_filter_39, R.drawable.thumbnail_filter_40, R.drawable.thumbnail_filter_41, R.drawable.thumbnail_filter_42, R.drawable.thumbnail_filter_43, R.drawable.thumbnail_filter_44, R.drawable.thumbnail_filter_45};
    public static int[] finaloverlayArr = new int[]{R.drawable.filter_original_1, R.drawable.filter_original_2, R.drawable.filter_original_3, R.drawable.filter_original_4, R.drawable.filter_original_5, R.drawable.filter_original_6, R.drawable.filter_original_7, R.drawable.filter_original_8, R.drawable.filter_original_9, R.drawable.filter_original_10, R.drawable.filter_original_11, R.drawable.filter_original_12, R.drawable.filter_original_13, R.drawable.filter_original_14, R.drawable.filter_original_15, R.drawable.filter_original_16, R.drawable.filter_original_17, R.drawable.filter_original_18, R.drawable.filter_original_19, R.drawable.filter_original_20, R.drawable.filter_original_21, R.drawable.filter_original_22, R.drawable.filter_original_23, R.drawable.filter_original_24, R.drawable.filter_original_25, R.drawable.filter_original_26, R.drawable.filter_original_27, R.drawable.filter_original_28, R.drawable.filter_original_29, R.drawable.filter_original_30, R.drawable.filter_original_31, R.drawable.filter_original_32, R.drawable.filter_original_33, R.drawable.filter_original_34, R.drawable.filter_original_35, R.drawable.filter_original_36, R.drawable.filter_original_37, R.drawable.filter_original_38, R.drawable.filter_original_39, R.drawable.filter_original_40, R.drawable.filter_original_41, R.drawable.filter_original_42, R.drawable.filter_original_43, R.drawable.filter_original_44, R.drawable.filter_original_45};

    public static int[] imageId = new int[]{R.drawable.bg_tv0, R.drawable.bg_tv1, R.drawable.bg_tv2, R.drawable.bg_tv3, R.drawable.bg_tv4, R.drawable.bg_tv5, R.drawable.bg_tv6, R.drawable.bg_tv7, R.drawable.bg_tv8, R.drawable.bg_tv9, R.drawable.btxt10, R.drawable.bg_tv11, R.drawable.bg_tv12, R.drawable.bg_tv13, R.drawable.bg_tv14, R.drawable.bg_tv15, R.drawable.bg_tv16, R.drawable.btxt17, R.drawable.btxt18, R.drawable.btxt19, R.drawable.bg_tv20, R.drawable.bg_tv21, R.drawable.bg_tv22, R.drawable.bg_tv23, R.drawable.bg_tv24, R.drawable.bg_tv25, R.drawable.bg_tv26, R.drawable.bg_tv27, R.drawable.bg_tv28, R.drawable.bg_tv29, R.drawable.bg_tv30, R.drawable.bg_tv31, R.drawable.bg_tv32, R.drawable.bg_tv33, R.drawable.bg_tv34, R.drawable.bg_tv35, R.drawable.bg_tv36, R.drawable.bg_tv37, R.drawable.bg_tv38, R.drawable.bg_tv39};

    public static String BASE_URL = "";
    public static String BASE_URL_BG = null;
    public static String BASE_URL_BG_SECOND = null;
    public static String BASE_URL_POSTER = null;

    public static int aspectRatioHeight = 1;
    public static int aspectRatioWidth = 1;
    public static String bgURL = "";
    public static String bgURL_SECOND = "";
    public static Bitmap bitmap = null;
    public static int currentScreenHeight = 1;
    public static int currentScreenWidth = 1;
    public static String fURL = "";
    public static String fURL_SECOND = "";
    public static String isRated = "isRated";
    public static String isFirstTimeINTRO = "isFirstTimeINTRO";

    public static String PREF_SAVE_TIME_STAMP = "saveCurrentTimeStamp";

    public static String ADX_PREF_SAVE_TIME_STAMP = "ADXPREFSAVETIMESTAMP";

    public static String jsonData = "jsonData";
    static int multiplier = 10000;
    public static String onTimeHint = "onTimeHint";
    public static String onTimeLayerScroll = "onTimeLayerScroll";
    public static String onTimeRecentHint = "onTimeRecentHint";

    public static String BASE_URL_POSTER_SECOND = null;
    public static String BASE_URL_SECOND = "";
    public static String BASE_URL_STICKER = null;
    public static String BASE_URL_STICKER_SECOND = null;

    static int DesignerScreenHeight = 1519, DesignerScreenWidth = 1080;

    public static int Priority = 3;
    public static String openfirtstime = "openfirtstime";
    public static String sdcardPath;
    public static String stickerURL = " ";
    public static String stickerURL_SECOND = " ";

    public static float[] pickOptimumSize(int i, int i2, int i3, int i4) {
        float f = (float) i3;
        float f2 = (float) i4;
        float f3 = (float) i;
        float f4 = (float) i2;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(f);
        stringBuilder.append("  ");
        stringBuilder.append(f2);
        stringBuilder.append("  and  ");
        stringBuilder.append(f3);
        stringBuilder.append("  ");
        stringBuilder.append(f4);
        Log.i("testings", stringBuilder.toString());
        float f5 = f3 / f4;
        float f6 = f4 / f3;
        StringBuilder stringBuilder2;
        StringBuilder stringBuilder3;
        if (f3 > f) {
            f3 = f * f6;
            stringBuilder = new StringBuilder();
            stringBuilder.append("if (wd > wr) ");
            stringBuilder.append(f);
            stringBuilder.append("  ");
            stringBuilder.append(f3);
            Log.i("testings", stringBuilder.toString());
            if (f3 > f2) {
                f = f2 * f5;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("  if (he > hr) ");
                stringBuilder2.append(f);
                stringBuilder2.append("  ");
                stringBuilder2.append(f2);
                Log.i("testings", stringBuilder2.toString());
            } else {
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append(" in else ");
                stringBuilder3.append(f);
                stringBuilder3.append("  ");
                stringBuilder3.append(f3);
                Log.i("testings", stringBuilder3.toString());
                f2 = f3;
            }
        } else if (f4 > f2) {
            f3 = f2 * f5;
            StringBuilder stringBuilder4 = new StringBuilder();
            stringBuilder4.append("  if (he > hr) ");
            stringBuilder4.append(f3);
            stringBuilder4.append("  ");
            stringBuilder4.append(f2);
            Log.i("testings", stringBuilder4.toString());
            if (f3 > f) {
                f2 = f * f6;
            } else {
                StringBuilder stringBuilder5 = new StringBuilder();
                stringBuilder5.append(" in else ");
                stringBuilder5.append(f3);
                stringBuilder5.append("  ");
                stringBuilder5.append(f2);
                Log.i("testings", stringBuilder5.toString());
                f = f3;
            }
        } else if (f5 > 0.75f) {
            f2 = f * f6;
            Log.i("testings", " if (rat1 > .75f) ");
        } else if (f6 > 1.5f) {
            f = f2 * f5;
            Log.i("testings", " if (rat2 > 1.5f) ");
        } else {
            f3 = f * f6;
            Log.i("testings", " in else ");
            if (f3 > f2) {
                f = f2 * f5;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("  if (he > hr) ");
                stringBuilder2.append(f);
                stringBuilder2.append("  ");
                stringBuilder2.append(f2);
                Log.i("testings", stringBuilder2.toString());
            } else {
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append(" in else ");
                stringBuilder3.append(f);
                stringBuilder3.append("  ");
                stringBuilder3.append(f3);
                Log.i("testings", stringBuilder3.toString());
                f2 = f3;
            }
        }
        return new float[]{f, f2};
    }

    public static final Uri getALLUriToResource(@NonNull Context context, String str) throws NotFoundException {
        context.getResources();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("android.resource://");
        stringBuilder.append(context.getPackageName());
        stringBuilder.append("/drawable/");
        stringBuilder.append(str);
        return Uri.parse(stringBuilder.toString());
    }

    public static int getAPPVersionInfo() {
        try {
            PackageInfo packageInfo = Application.getInstance().getPackageManager().getPackageInfo(Application.getInstance().getPackageName(), 0);
            String str = packageInfo.versionName;
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static Bitmap mergeAndExportBitmap(Bitmap bitmap, Bitmap bitmap2, int i) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Drawable[] drawableArr = new Drawable[]{new BitmapDrawable(bitmap), new BitmapDrawable(bitmap2)};
        drawableArr[1].setAlpha(i);
        LayerDrawable layerDrawable = new LayerDrawable(drawableArr);
        Canvas canvas = new Canvas(createBitmap);
        layerDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        layerDrawable.draw(canvas);
        return createBitmap;
    }

    public static CharSequence findSpannableString(Context context, Typeface typeface, int i) {
        SpannableStringBuilder append = new SpannableStringBuilder().append(new SpannableString(context.getResources().getString(i)));
        return append.subSequence(0, append.length());
    }


    public static Bitmap getFinalBitmapFromUri(Context context, Uri uri, float f, float f2) throws IOException {
        try {
            ParcelFileDescriptor openFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
            FileDescriptor fileDescriptor = openFileDescriptor.getFileDescriptor();
            Options options = new Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
            Options options2 = new Options();
            if (f <= f2) {
                f = f2;
            }
            int i = (int) f;
            options2.inSampleSize = Glide_Image_Utils.extract_ClosestResampleSize(options.outWidth, options.outHeight, i);
            Bitmap decodeFileDescriptor = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options2);
            Matrix matrix = new Matrix();
            if (decodeFileDescriptor.getWidth() > i || decodeFileDescriptor.getHeight() > i) {
                Options resampling = Glide_Image_Utils.extractResampling(decodeFileDescriptor.getWidth(), decodeFileDescriptor.getHeight(), i);
                matrix.postScale(((float) resampling.outWidth) / ((float) decodeFileDescriptor.getWidth()), ((float) resampling.outHeight) / ((float) decodeFileDescriptor.getHeight()));
            }
            String realPathFromURI = Glide_Image_Utils.getRealPathFromURI(uri, context);
            if (new Integer(VERSION.SDK).intValue() > 4) {
                int exifRotation = ExifRotationUtils.getExifRotation(realPathFromURI);
                if (exifRotation != 0) {
                    matrix.postRotate((float) exifRotation);
                }
            }
            Bitmap createBitmap = Bitmap.createBitmap(decodeFileDescriptor, 0, 0, decodeFileDescriptor.getWidth(), decodeFileDescriptor.getHeight(), matrix, true);
            openFileDescriptor.close();
            return createBitmap;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }


    public static boolean exportFinalBitmapObject(Activity activity, Bitmap bitmap, String str) {
        bitmap = bitmap.copy(bitmap.getConfig(), true);
        File file = new File(str);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            boolean compress = bitmap.compress(CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            bitmap.recycle();
            activity.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(file)));
            return compress;
        } catch (Exception e) {
            e.printStackTrace();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Exception");
            stringBuilder.append(e.getMessage());
            Log.i("testing", stringBuilder.toString());
            return false;
        }
    }

    public static Typeface getHeaderTypeface(Activity activity) {
        return Typeface.createFromAsset(activity.getAssets(), "font/Montserrat-SemiBold.ttf");
    }


    public static int getFinalClosestResampleSize(int i, int i2, int i3) {
        i = Math.max(i, i2);
        int i4 = 1;
        while (true) {
            if (i4 >= Integer.MAX_VALUE) {
                break;
            } else if (i4 * i3 > i) {
                i4--;
                break;
            } else {
                i4++;
            }
        }
        return i4 > 0 ? i4 : 1;
    }

    public static Typeface getTextTypeface(Activity activity) {
        return Typeface.createFromAsset(activity.getAssets(), "font/Montserrat-Medium.ttf");
    }

    public static Typeface getTextTypefaceFont(Activity activity, String str) {
        return Typeface.createFromAsset(activity.getAssets(), str);
    }


    public static String exportFinalBitmapObject(Activity activity, Bitmap bitmap) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), ".Poster Maker Stickers/Mydesigns");
        file.mkdirs();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("thumb-");
        stringBuilder.append(System.currentTimeMillis());
        stringBuilder.append(".png");
        File file2 = new File(file, stringBuilder.toString());
        if (file2.exists()) {
            file2.delete();
        }
        try {
            bitmap.compress(CompressFormat.PNG, 100, new FileOutputStream(file2));
            String activity2 = file2.getAbsolutePath();
            return activity2;
        } catch (Exception e) {
            e.printStackTrace();
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Exception");
            stringBuilder2.append(e.getMessage());
            Log.i("MAINACTIVITY", stringBuilder2.toString());
            Toast.makeText(activity, activity.getResources().getString(R.string.save_err), 0).show();
            return null;
        }
    }


    public static File findSaveFileLocation(String str) {
        File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(".Poster Maker Stickers/");
        stringBuilder.append(str);
        return new File(externalStoragePublicDirectory, stringBuilder.toString());
    }


    public static String exportBitmapObject1(Bitmap bitmap) {
        File saveFileLocation = findSaveFileLocation("category1");
        saveFileLocation.mkdirs();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("raw1-");
        stringBuilder.append(System.currentTimeMillis());
        stringBuilder.append(".png");
        File file = new File(saveFileLocation, stringBuilder.toString());
        String absolutePath = file.getAbsolutePath();
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();
            return absolutePath;
        } catch (Exception e) {
            e.printStackTrace();
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Exception");
            stringBuilder2.append(e.getMessage());
            Log.i("testing", stringBuilder2.toString());
            return "";
        }
    }


    public static byte[] findBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static Bitmap guidelines_final_bitmap(Activity activity, int i, int i2) {
        Context context = activity;
        int i3 = i;
        int i4 = i2;
        try {
            Bitmap createBitmap = Bitmap.createBitmap(i3, i4, Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            Paint paint = new Paint();
            paint.setColor(-1);
            paint.setStrokeWidth((float) Glide_Image_Utils.convertDpToPx(context, 2.0f));
            paint.setPathEffect(new DashPathEffect(new float[]{5.0f, 5.0f}, 1.0f));
            paint.setStyle(Style.STROKE);
            Paint paint2 = new Paint();
            paint2.setColor(-16777216);
            paint2.setStrokeWidth((float) Glide_Image_Utils.convertDpToPx(context, 2.0f));
            paint2.setPathEffect(new DashPathEffect(new float[]{5.0f, 5.0f}, 1.0f));
            paint2.setStyle(Style.STROKE);
            float f = (float) i4;
            canvas.drawLine((float) (i3 / 4), 0.0f, (float) (i3 / 4), f, paint);
            canvas.drawLine((float) (i3 / 2), 0.0f, (float) (i3 / 2), f, paint);
            int i5 = i3 * 3;
            canvas.drawLine((float) (i5 / 4), 0.0f, (float) (i5 / 4), f, paint);
            float f2 = (float) i3;
            canvas.drawLine(0.0f, (float) (i4 / 4), f2, (float) (i4 / 4), paint);
            canvas.drawLine(0.0f, (float) (i4 / 2), f2, (float) (i4 / 2), paint);
            int i6 = i4 * 3;
            canvas.drawLine(0.0f, (float) (i6 / 4), f2, (float) (i6 / 4), paint);
            canvas.drawLine((float) ((i3 / 4) + 2), 0.0f, (float) ((i3 / 4) + 2), f, paint2);
            canvas.drawLine((float) ((i3 / 2) + 2), 0.0f, (float) ((i3 / 2) + 2), f, paint2);
            canvas.drawLine((float) ((i5 / 4) + 2), 0.0f, (float) ((i5 / 4) + 2), f, paint2);
            canvas.drawLine(0.0f, (float) ((i4 / 4) + 2), f2, (float) ((i4 / 4) + 2), paint2);
            canvas.drawLine(0.0f, (float) ((i4 / 2) + 2), f2, (float) ((i4 / 2) + 2), paint2);
            canvas.drawLine(0.0f, (float) ((i6 / 4) + 2), f2, (float) ((i6 / 4) + 2), paint2);
            return createBitmap;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap extractTiledBitmap(Activity activity, int i, Bitmap bitmap, SeekBar seekBar) {
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        Paint paint = new Paint();
        int progress = seekBar.getProgress() + 10;
        Options options = new Options();
        options.inScaled = false;
        paint.setShader(new BitmapShader(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(activity.getResources(), i, options), progress, progress, true), TileMode.REPEAT, TileMode.REPEAT));
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        new Canvas(createBitmap).drawRect(rect, paint);
        return createBitmap;
    }

    public static Bitmap extractTiledBitmap(Context context, int i, int i2, int i3) {
        Rect rect = new Rect(0, 0, i2, i3);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(BitmapFactory.decodeResource(context.getResources(), i, new Options()), TileMode.REPEAT, TileMode.REPEAT));
        Bitmap createBitmap = Bitmap.createBitmap(i2, i3, Config.ARGB_8888);
        new Canvas(createBitmap).drawRect(rect, paint);
        return createBitmap;
    }


    public static void displayProjectHindDialog(View view, Activity activity) {
        View inflate = activity.getLayoutInflater().inflate(R.layout.intro_tooltip_more_option, null);
        ((TextView) inflate.findViewById(R.id.txthint)).setTypeface(getTextTypeface(activity));
        new EasyDialog(activity).setLayout(inflate).setBackgroundColor(activity.getResources().getColor(R.color.titlecolor)).setLocationByAttachedView(view).setGravity(0).setAnimationTranslationShow(0, 1000, -600.0f, 100.0f, -50.0f, 50.0f, 0.0f).setAnimationAlphaShow(1000, 0.3f, 1.0f).setAnimationTranslationDismiss(0, 500, -50.0f, 800.0f).setAnimationAlphaDismiss(500, 1.0f, 0.0f).setMatchParent(false).setMarginLeftAndRight(24, 24).show();
    }

    public static void showRecentProjectHindDialog(View view, Activity activity) {
        View inflate = activity.getLayoutInflater().inflate(R.layout.intro_tooltip_view_layer, null);
        ((TextView) inflate.findViewById(R.id.txthint)).setTypeface(getTextTypeface(activity));
        new EasyDialog(activity).setLayout(inflate).setBackgroundColor(activity.getResources().getColor(R.color.titlecolor)).setLocationByAttachedView(view).setGravity(3).setAnimationTranslationShow(0, 1000, -600.0f, 100.0f, -50.0f, 50.0f, 0.0f).setAnimationAlphaShow(1000, 0.3f, 1.0f).setAnimationTranslationDismiss(0, 500, -50.0f, 800.0f).setAnimationAlphaDismiss(500, 1.0f, 0.0f).setMatchParent(false).setMarginLeftAndRight(24, 24).show();
    }

    public static void displayScrollLayerDialog(View view, Activity activity) {
        View inflate = activity.getLayoutInflater().inflate(R.layout.intro_tooltip_scroll_layer, null);
        ((TextView) inflate.findViewById(R.id.txthint)).setTypeface(getTextTypeface(activity));
        new EasyDialog(activity).setLayout(inflate).setBackgroundColor(activity.getResources().getColor(R.color.titlecolor)).setLocationByAttachedView(view).setGravity(3).setAnimationTranslationShow(0, 1000, -600.0f, 100.0f, -50.0f, 50.0f, 0.0f).setAnimationAlphaShow(1000, 0.3f, 1.0f).setAnimationTranslationDismiss(0, 500, -50.0f, 800.0f).setAnimationAlphaDismiss(500, 1.0f, 0.0f).setMatchParent(false).setMarginLeftAndRight(24, 24).show();
    }


    public static float getViewNewX(float f) {
        int i = aspectRatioWidth;
        int i2 = multiplier;
        float[] optimumSize = pickOptimumSize(i * i2, aspectRatioHeight * i2, DesignerScreenWidth, DesignerScreenHeight);
        i2 = aspectRatioWidth;
        int i3 = multiplier;
        float[] optimumSize2 = pickOptimumSize(i2 * i3, aspectRatioHeight * i3, currentScreenWidth, currentScreenHeight);
        float f2 = optimumSize2[1];
        float f3 = optimumSize[1];
        return (optimumSize2[0] / optimumSize[0]) * f;
    }

    public static Animation getAnimTopToBootom(Activity activity) {
        return AnimationUtils.loadAnimation(activity, R.anim.top2bottom_view);
    }

    public static Animation getAnimUp(Activity activity) {
        return AnimationUtils.loadAnimation(activity, R.anim.view_slide_up);
    }

    public static Animation getAnimDown(Activity activity) {
        return AnimationUtils.loadAnimation(activity, R.anim.view_slide_down);
    }

    public static Animation getAnimUpDown(Activity activity) {
        return AnimationUtils.loadAnimation(activity, R.anim.slide_view_lefttoright);
    }

    public static Animation getAnimDownUp(Activity activity) {
        return AnimationUtils.loadAnimation(activity, R.anim.right_left_slideview);
    }


    public static String getViewMargin(String str) {
        String[] split = str.split(",");
        int parseInt = Integer.parseInt(split[0]);
        int parseInt2 = Integer.parseInt(split[1]);
        int i = aspectRatioWidth;
        int i2 = multiplier;
        float[] optimumSize = pickOptimumSize(i * i2, aspectRatioHeight * i2, DesignerScreenWidth, DesignerScreenHeight);
        i2 = aspectRatioWidth;
        int i3 = multiplier;
        float[] optimumSize2 = pickOptimumSize(i2 * i3, aspectRatioHeight * i3, currentScreenWidth, currentScreenHeight);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.valueOf((int) (((float) parseInt) * (optimumSize2[0] / optimumSize[0]))));
        stringBuilder.append(",");
        stringBuilder.append(String.valueOf((int) (((float) parseInt2) * (optimumSize2[1] / optimumSize[1]))));
        return stringBuilder.toString();
    }

    public static float getViewNewY(float f) {
        int i = aspectRatioWidth;
        int i2 = multiplier;
        float[] optimumSize = pickOptimumSize(i * i2, aspectRatioHeight * i2, DesignerScreenWidth, DesignerScreenHeight);
        i2 = aspectRatioWidth;
        int i3 = multiplier;
        float[] optimumSize2 = pickOptimumSize(i2 * i3, aspectRatioHeight * i3, currentScreenWidth, currentScreenHeight);
        float f2 = optimumSize2[0];
        float f3 = optimumSize[0];
        return (optimumSize2[1] / optimumSize[1]) * f;
    }

    public static int getNewViewWidth(float f) {
        int i = aspectRatioWidth;
        int i2 = multiplier;
        float[] optimumSize = pickOptimumSize(i * i2, aspectRatioHeight * i2, DesignerScreenWidth, DesignerScreenHeight);
        i2 = aspectRatioWidth;
        int i3 = multiplier;
        float[] optimumSize2 = pickOptimumSize(i2 * i3, aspectRatioHeight * i3, currentScreenWidth, currentScreenHeight);
        float f2 = optimumSize2[0];
        float f3 = optimumSize[0];
        return (int) ((optimumSize2[1] / optimumSize[1]) * f);
    }

    public static Bitmap resizeFinalBitmap(Bitmap bitmap, int i, int i2) {
        float f = (float) i;
        float f2 = (float) i2;
        float width = (float) bitmap.getWidth();
        float height = (float) bitmap.getHeight();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(f);
        stringBuilder.append("  ");
        stringBuilder.append(f2);
        stringBuilder.append("  and  ");
        stringBuilder.append(width);
        stringBuilder.append("  ");
        stringBuilder.append(height);
        Log.i("testings", stringBuilder.toString());
        float f3 = width / height;
        float f4 = height / width;
        StringBuilder stringBuilder2;
        if (width > f) {
            width = f * f4;
            stringBuilder = new StringBuilder();
            stringBuilder.append("if (wd > wr) ");
            stringBuilder.append(f);
            stringBuilder.append("  ");
            stringBuilder.append(width);
            Log.i("testings", stringBuilder.toString());
            if (width > f2) {
                f = f2 * f3;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("  if (he > hr) ");
                stringBuilder2.append(f);
                stringBuilder2.append("  ");
                stringBuilder2.append(f2);
                Log.i("testings", stringBuilder2.toString());
            } else {
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(" in else ");
                stringBuilder2.append(f);
                stringBuilder2.append("  ");
                stringBuilder2.append(width);
                Log.i("testings", stringBuilder2.toString());
                f2 = width;
            }
        } else if (height > f2) {
            width = f2 * f3;
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("  if (he > hr) ");
            stringBuilder3.append(width);
            stringBuilder3.append("  ");
            stringBuilder3.append(f2);
            Log.i("testings", stringBuilder3.toString());
            if (width > f) {
                f2 = f * f4;
            } else {
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(" in else ");
                stringBuilder2.append(width);
                stringBuilder2.append("  ");
                stringBuilder2.append(f2);
                Log.i("testings", stringBuilder2.toString());
                f = width;
            }
        } else if (f3 > 0.75f) {
            f2 = f * f4;
            Log.i("testings", " if (rat1 > .75f) ");
        } else if (f4 > 1.5f) {
            f = f2 * f3;
            Log.i("testings", " if (rat2 > 1.5f) ");
        } else {
            width = f * f4;
            Log.i("testings", " in else ");
            if (width > f2) {
                f = f2 * f3;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("  if (he > hr) ");
                stringBuilder2.append(f);
                stringBuilder2.append("  ");
                stringBuilder2.append(f2);
                Log.i("testings", stringBuilder2.toString());
            } else {
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(" in else ");
                stringBuilder2.append(f);
                stringBuilder2.append("  ");
                stringBuilder2.append(width);
                Log.i("testings", stringBuilder2.toString());
                f2 = width;
            }
        }
        return Bitmap.createScaledBitmap(bitmap, (int) f, (int) f2, false);
    }

    public static int getNewViewHeight(float f) {
        int i = aspectRatioWidth;
        int i2 = multiplier;
        float[] optimumSize = pickOptimumSize(i * i2, aspectRatioHeight * i2, DesignerScreenWidth, DesignerScreenHeight);
        i2 = aspectRatioWidth;
        int i3 = multiplier;
        float[] optimumSize2 = pickOptimumSize(i2 * i3, aspectRatioHeight * i3, currentScreenWidth, currentScreenHeight);
        float f2 = optimumSize2[0];
        float f3 = optimumSize[0];
        return (int) ((optimumSize2[1] / optimumSize[1]) * f);
    }

    public static float getNewViewSize(Context context, float f) {
        return (context.getResources().getDisplayMetrics().density / 3.0f) * f;
    }

}
