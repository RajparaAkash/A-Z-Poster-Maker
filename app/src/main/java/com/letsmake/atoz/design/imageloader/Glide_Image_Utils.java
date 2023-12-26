package com.letsmake.atoz.design.imageloader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build.VERSION;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.util.Log;

import java.io.IOException;

import com.letsmake.atoz.design.app_utils.ExifRotationUtils;
import com.letsmake.atoz.design.app_utils.OwnTypefaceSpan;

public class Glide_Image_Utils {

    public static Bitmap getResampleGlideImageBitmap(Uri uri, Context context, int i) throws IOException {
        try {
            return resampleGlideImage(getRealPathFromURI(uri, context), i);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap resizeImageBitmap(Bitmap bitmap, int i, int i2) {
        if (bitmap == null) {
            return null;
        }
        float f = (float) i;
        float f2 = (float) i2;
        float width = (float) bitmap.getWidth();
        float height = (float) bitmap.getHeight();
        float f3 = width / height;
        float f4 = height / width;
        if (width > f) {
            width = f * f4;
            if (width > f2) {
                f = f2 * f3;
            } else {
                f2 = width;
            }
        } else if (height > f2) {
            width = f2 * f3;
            if (width > f) {
                f2 = f * f4;
            } else {
                f = width;
            }
        } else if (f3 > 0.75f) {
            width = f * f4;
            if (width > f2) {
                f = f2 * f3;
            } else {
                f2 = width;
            }
        } else if (f4 > 1.5f) {
            width = f2 * f3;
            if (width > f) {
                f2 = f * f4;
            } else {
                f = width;
            }
        } else {
            width = f * f4;
            if (width > f2) {
                f = f2 * f3;
            } else {
                f2 = width;
            }
        }
        if (bitmap.getWidth() <= 0 || bitmap.getHeight() <= 0) {
            return null;
        }
        return Bitmap.createScaledBitmap(bitmap, (int) f, (int) f2, false);
    }


    public static Options extractResampling(int i, int i2, int i3) {
        Options options = new Options();
        float f = i > i2 ? ((float) i3) / ((float) i) : i2 > i ? ((float) i3) / ((float) i2) : ((float) i3) / ((float) i);
        options.outWidth = (int) ((((float) i) * f) + 0.5f);
        options.outHeight = (int) ((((float) i2) * f) + 0.5f);
        return options;
    }

    public static int extract_ClosestResampleSize(int i, int i2, int i3) {
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

    @SuppressLint({"UseValueOf"})
    public static Bitmap resampleGlideImage(String str, int i) throws Exception {
        try {
            Options options = new Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(str, options);
            Options options2 = new Options();
            options2.inSampleSize = extract_ClosestResampleSize(options.outWidth, options.outHeight, i);
            Bitmap decodeFile = BitmapFactory.decodeFile(str, options2);
            if (decodeFile == null) {
                return null;
            }
            Matrix matrix = new Matrix();
            if (decodeFile.getWidth() > i || decodeFile.getHeight() > i) {
                Options resampling = extractResampling(decodeFile.getWidth(), decodeFile.getHeight(), i);
                matrix.postScale(((float) resampling.outWidth) / ((float) decodeFile.getWidth()), ((float) resampling.outHeight) / ((float) decodeFile.getHeight()));
            }
            if (new Integer(VERSION.SDK).intValue() > 4) {
                int exifRotation = ExifRotationUtils.getExifRotation(str);
                if (exifRotation != 0) {
                    matrix.postRotate((float) exifRotation);
                }
            }
            return Bitmap.createBitmap(decodeFile, 0, 0, decodeFile.getWidth(), decodeFile.getHeight(), matrix, true);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Options extractBitmapDims(Uri uri, Context context) {
        String realPathFromURI = getRealPathFromURI(uri, context);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Path ");
        stringBuilder.append(realPathFromURI);
        Log.i("texting", stringBuilder.toString());
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(realPathFromURI, options);
        return options;
    }

    public static String getRealPathFromURI(Uri uri, Context context) {
        try {
            Cursor query = context.getContentResolver().query(uri, null, null, null, null);
            if (query == null) {
                return uri.getPath();
            }
            query.moveToFirst();
            String string = query.getString(query.getColumnIndex("_data"));
            query.close();
            return string;
        } catch (Exception e) {
            e.printStackTrace();
            return uri.toString();
        }
    }


    public static int convertDpToPx(Context context, float f) {
        context.getResources();
        return (int) (Resources.getSystem().getDisplayMetrics().density * f);
    }

    public static Bitmap CropGlideBitmapTransparency(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int i = -1;
        int height = bitmap.getHeight();
        int i2 = -1;
        int i3 = width;
        width = 0;
        while (width < bitmap.getHeight()) {
            int i4 = i2;
            i2 = i;
            i = i3;
            for (i3 = 0; i3 < bitmap.getWidth(); i3++) {
                if (((bitmap.getPixel(i3, width) >> 24) & 255) > 0) {
                    if (i3 < i) {
                        i = i3;
                    }
                    if (i3 > i2) {
                        i2 = i3;
                    }
                    if (width < height) {
                        height = width;
                    }
                    if (width > i4) {
                        i4 = width;
                    }
                }
            }
            width++;
            i3 = i;
            i = i2;
            i2 = i4;
        }
        return (i < i3 || i2 < height) ? null : Bitmap.createBitmap(bitmap, i3, height, (i - i3) + 1, (i2 - height) + 1);
    }

    public static Bitmap mergelogoWithView(Bitmap bitmap, Bitmap bitmap2) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        float width = (float) bitmap.getWidth();
        float height = (float) bitmap.getHeight();
        float width2 = (float) bitmap2.getWidth();
        float height2 = (float) bitmap2.getHeight();
        float f = width2 / height2;
        float f2 = height2 / width2;
        if (width2 > width) {
            bitmap2 = Bitmap.createScaledBitmap(bitmap2, (int) width, (int) (width * f2), false);
        } else if (height2 > height) {
            bitmap2 = Bitmap.createScaledBitmap(bitmap2, (int) (f * height), (int) height, false);
        }
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, null);
        canvas.drawBitmap(bitmap2, (float) (bitmap.getWidth() - bitmap2.getWidth()), (float) (bitmap.getHeight() - bitmap2.getHeight()), null);
        return createBitmap;
    }

    public static Bitmap mergelogoWithView(Bitmap bitmap, Bitmap bitmap2, float f) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, null);
        canvas.drawBitmap(Bitmap.createScaledBitmap(bitmap2, bitmap.getWidth(), bitmap.getHeight(), true), 0.0f, 0.0f, null);
        return createBitmap;
    }


    public static Bitmap bitmapGlideMasking(Bitmap bitmap, Bitmap bitmap2) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint(1);
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, null);
        canvas.drawBitmap(bitmap2, 0.0f, 0.0f, paint);
        paint.setXfermode(null);
        return createBitmap;
    }

    public static Bitmap getGlideTiledBitmap(Context context, int i, int i2, int i3) {
        Rect rect = new Rect(0, 0, i2, i3);
        Paint paint = new Paint();
        Options options = new Options();
        options.inScaled = false;
        paint.setShader(new BitmapShader(BitmapFactory.decodeResource(context.getResources(), i, options), TileMode.REPEAT, TileMode.REPEAT));
        Bitmap createBitmap = Bitmap.createBitmap(i2, i3, Config.ARGB_8888);
        new Canvas(createBitmap).drawRect(rect, paint);
        return createBitmap;
    }

    public static Bitmap cropGlideCenterBitmap(Bitmap bitmap, int i, int i2) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width < i && height < i2) {
            return bitmap;
        }
        int i3 = 0;
        int i4 = width > i ? (width - i) / 2 : 0;
        if (height > i2) {
            i3 = (height - i2) / 2;
        }
        if (i > width) {
            i = width;
        }
        if (i2 > height) {
            i2 = height;
        }
        return Bitmap.createBitmap(bitmap, i4, i3, i, i2);
    }

    public static Bitmap getGlideColoredBitmap(int i, int i2, int i3) {
        Bitmap createBitmap = Bitmap.createBitmap(i2, i3, Config.ARGB_8888);
        new Canvas(createBitmap).drawColor(i);
        return createBitmap;
    }

    public static CharSequence getGlideSpannableString(Context context, Typeface typeface, int i) {
        SpannableString spannableString = new SpannableString(context.getResources().getString(i));
        spannableString.setSpan(new OwnTypefaceSpan(typeface), 0, context.getResources().getString(i).length(), 0);
        SpannableStringBuilder append = new SpannableStringBuilder().append(spannableString);
        return append.subSequence(0, append.length());
    }

    public static Bitmap getGlideThumbnail(Bitmap bitmap, int i, int i2) {
        bitmap = bitmap.copy(bitmap.getConfig(), true);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (height > width) {
            bitmap = cropGlideCenterBitmap(bitmap, width, width);
        } else {
            bitmap = cropGlideCenterBitmap(bitmap, height, height);
        }
        return Bitmap.createScaledBitmap(bitmap, i, i2, true);
    }


    private void setTextSizeGlideForWidth(Paint paint, float f, String str) {
        paint.setTextSize(48.0f);
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        paint.setTextSize((f * 48.0f) / ((float) rect.width()));
    }

    public static Bitmap scaleGlideCenterCrop(Bitmap bitmap, int i, int i2) {
        float f = (float) i2;
        float width = (float) bitmap.getWidth();
        float f2 = (float) i;
        float height = (float) bitmap.getHeight();
        float max = Math.max(f / width, f2 / height);
        width *= max;
        max *= height;
        f = (f - width) / 2.0f;
        f2 = (f2 - max) / 2.0f;
        RectF rectF = new RectF(f, f2, width + f, max + f2);
        Bitmap createBitmap = Bitmap.createBitmap(i2, i, bitmap.getConfig());
        new Canvas(createBitmap).drawBitmap(bitmap, null, rectF, null);
        return createBitmap;
    }

}
