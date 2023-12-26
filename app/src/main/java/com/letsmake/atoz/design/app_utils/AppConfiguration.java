package com.letsmake.atoz.design.app_utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

import com.letsmake.atoz.design.R;

public class AppConfiguration {
    public static File takeFileDir(Context context) {
        if ("mounted".equals(Environment.getExternalStorageState())) {
            return context.getExternalFilesDir(null);
        }
        return context.getFilesDir();
    }

    public static File findTextDir(Context context) {
        File GetFileDir = takeFileDir(context);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(GetFileDir.getPath());
        stringBuilder.append(File.pathSeparator);
        stringBuilder.append("text");
        File file = new File(stringBuilder.toString());
        return (file.exists() || file.mkdirs()) ? file : GetFileDir;
    }


    public static File findProjectDir(Context context) {
        File GetFileDir = takeFileDir(context);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(GetFileDir.getPath());
        stringBuilder.append(File.separator);
        stringBuilder.append("project");
        File file = new File(stringBuilder.toString());
        return (file.exists() || file.mkdirs()) ? file : GetFileDir;
    }

    public static File findProjectBitmapDir(Context context) {
        File GetFileDir = takeFileDir(context);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(GetFileDir.getPath());
        stringBuilder.append(File.separator);
        stringBuilder.append("project");
        File file = new File(stringBuilder.toString());
        return (file.exists() || file.mkdirs()) ? file : GetFileDir;
    }


    public static File takeCacheDir(Context context) {
        if ("mounted".equals(Environment.getExternalStorageState())) {
            return context.getExternalCacheDir();
        }
        return context.getCacheDir();
    }

    public static File findSaveDir(Context context) {
        File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(externalStoragePublicDirectory.getPath());
        stringBuilder.append(File.separator);
        stringBuilder.append(context.getString(R.string.app_name));
        File file = new File(stringBuilder.toString());
        return (file.exists() || file.mkdirs()) ? file : externalStoragePublicDirectory;
    }

    public static File findFontDir(Context context) {
        File GetFileDir = takeFileDir(context);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(GetFileDir.getPath());
        stringBuilder.append(File.separator);
        stringBuilder.append("font");
        File file = new File(stringBuilder.toString());
        return (file.exists() || file.mkdirs()) ? file : GetFileDir;
    }


    public static File findUnsplashJsonDir(Context context) {
        File GetFileDir = takeFileDir(context);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(GetFileDir.getPath());
        stringBuilder.append(File.separator);
        stringBuilder.append("unsplash");
        File file = new File(stringBuilder.toString());
        return (file.exists() || file.mkdirs()) ? file : GetFileDir;
    }
}
