package com.letsmake.atoz.design.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

import com.letsmake.atoz.design.app_utils.AppPreferenceClass;
import com.letsmake.atoz.design.editor_intelligence.AppConstants;

public class ShapeActivity extends AppCompatActivity {
    private static final String TAG = "ShapeActivity";

    public AppPreferenceClass appPreferenceClass;
    public Typeface typefaceTextBold, typefaceTextNormal;

    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        typefaceTextBold = Typeface.createFromAsset(getAssets(), "font/Montserrat-SemiBold.ttf");
        typefaceTextNormal = Typeface.createFromAsset(getAssets(), "font/Montserrat-Medium.ttf");
        appPreferenceClass = new AppPreferenceClass(this);
    }

    public void makeStickerDir() {
        this.appPreferenceClass = new AppPreferenceClass(this);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM));
        stringBuilder.append("/.Poster Design Stickers/sticker");
        File file = new File(stringBuilder.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM));
        stringBuilder2.append("/.Poster Design Stickers/sticker/bg");
        File file2 = new File(stringBuilder2.toString());
        if (!file2.exists()) {
            file2.mkdirs();
        }
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM));
        stringBuilder2.append("/.Poster Design Stickers/sticker/font");
        file2 = new File(stringBuilder2.toString());
        if (!file2.exists()) {
            file2.mkdirs();
        }
        for (int i = 0; i < 29; i++) {
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM));
            stringBuilder3.append("/.Poster Design Stickers/sticker/cat");
            stringBuilder3.append(i);
            File file3 = new File(stringBuilder3.toString());
            if (!file3.exists()) {
                file3.mkdirs();
            }
        }
        for (int i2 = 0; i2 < 11; i2++) {
            StringBuilder stringBuilder4 = new StringBuilder();
            stringBuilder4.append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM));
            stringBuilder4.append("/.Poster Design Stickers/sticker/art");
            stringBuilder4.append(i2);
            File file4 = new File(stringBuilder4.toString());
            if (!file4.exists()) {
                file4.mkdirs();
            }
        }
        this.appPreferenceClass.putString(AppConstants.sdcardPath, file.getPath());
        String str = TAG;
        stringBuilder = new StringBuilder();
        stringBuilder.append("onCreate: ");
        stringBuilder.append(AppConstants.sdcardPath);
        Log.e(str, stringBuilder.toString());
    }

    public void AdjustFontBold(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt instanceof ViewGroup) {
                AdjustFontNormal((ViewGroup) childAt);
            } else if (childAt instanceof TextView) {
                ((TextView) childAt).setTypeface(this.typefaceTextBold);
            } else if (childAt instanceof Button) {
                ((Button) childAt).setTypeface(this.typefaceTextBold);
            } else if (childAt instanceof EditText) {
                ((EditText) childAt).setTypeface(this.typefaceTextBold);
            }
        }
    }

    public Typeface adjustFontBold() {
        return this.typefaceTextBold;
    }

    public void AdjustFontNormal(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt instanceof ViewGroup) {
                AdjustFontNormal((ViewGroup) childAt);
            } else if (childAt instanceof TextView) {
                ((TextView) childAt).setTypeface(this.typefaceTextNormal);
            } else if (childAt instanceof Button) {
                ((Button) childAt).setTypeface(this.typefaceTextNormal);
            } else if (childAt instanceof EditText) {
                ((EditText) childAt).setTypeface(this.typefaceTextNormal);
            }
        }
    }

    public Typeface applyFontNormal() {
        return this.typefaceTextNormal;
    }


}
