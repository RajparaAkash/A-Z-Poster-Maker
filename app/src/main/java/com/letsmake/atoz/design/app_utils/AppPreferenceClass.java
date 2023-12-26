package com.letsmake.atoz.design.app_utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;

import com.letsmake.atoz.design.editor_intelligence.AppConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.letsmake.atoz.design.editor_intelligence.AppConstants.ADX_PREF_SAVE_TIME_STAMP;
import static com.letsmake.atoz.design.editor_intelligence.AppConstants.PREF_SAVE_TIME_STAMP;

public class AppPreferenceClass {
    private String DEFAULT_APP_IMAGEDATA_DIRECTORY;
    private String lastImagePath = "";
    private static SharedPreferences preferences;

    private static final String PREF_EMAIL = "USER_EMAILS";

    public AppPreferenceClass(Context context) {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static boolean checkExternalStorageWritable() {
        return "mounted".equals(Environment.getExternalStorageState());
    }

    public static boolean checkExternalStorageReadable() {
        String externalStorageState = Environment.getExternalStorageState();
        return "mounted".equals(externalStorageState) || "mounted_ro".equals(externalStorageState);
    }

    public SharedPreferences getSharedPrefernce() {
        SharedPreferences sharedPreferences = this.preferences;
        return sharedPreferences != null ? sharedPreferences : null;
    }

    public Bitmap getImages(String str) {
        try {
            return BitmapFactory.decodeFile(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String addupFullPath(String str) {
        File file = new File(Environment.getExternalStorageDirectory(), this.DEFAULT_APP_IMAGEDATA_DIRECTORY);
        if (!checkExternalStorageReadable() || !checkExternalStorageWritable() || file.exists() || file.mkdirs()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(file.getPath());
            stringBuilder.append('/');
            stringBuilder.append(str);
            return stringBuilder.toString();
        }
        Log.e("ERROR", "Failed to setup folder");
        return "";
    }

    public String getSavedImagePath() {
        return this.lastImagePath;
    }

    public boolean putImage(String str, String str2, Bitmap bitmap) {
        if (str == null || str2 == null || bitmap == null) {
            return false;
        }
        this.DEFAULT_APP_IMAGEDATA_DIRECTORY = str;
        str = addupFullPath(str2);
        if (str.equals("")) {
            return false;
        }
        this.lastImagePath = str;
        return expoortBitmap(str, bitmap);
    }


    public int getInt(String str, int i) {
        return this.preferences.getInt(str, i);
    }


    public String getString(String str) {
        return this.preferences.getString(str, "");
    }


    public static String getUserEmails() {
        return preferences.getString(PREF_EMAIL, "");
    }

    public static void setUserEmails(String EMAIL) {
        preferences.edit().putString(PREF_EMAIL, EMAIL).apply();
    }

    public boolean getBoolean(String str, boolean z) {
        return this.preferences.getBoolean(str, z);
    }

    public void putInt(String str, int i) {
        this.preferences.edit().putInt(str, i).apply();
    }


/*
    public static Long getADXCurrentTimeStamp(Context context) {
        return preferences.getLong(ADX_PREF_SAVE_TIME_STAMP, -1);
    }

    public static void setADXCurrentTimeStamp(Context context, Long timestamp) {
        preferences.edit().putLong(ADX_PREF_SAVE_TIME_STAMP, timestamp).apply();
    }
*/

    public static Long getCurrentTimeStamp(Context context) {
        return preferences.getLong(PREF_SAVE_TIME_STAMP, -1);
    }

    public static void setCurrentTimeStamp(Context context, Long timestamp) {
        preferences.edit().putLong(PREF_SAVE_TIME_STAMP, timestamp).apply();
    }

    public void putString(String str, String str2) {
        this.preferences.edit().putString(str, str2).apply();
    }

    public void remove(String str) {
        this.preferences.edit().remove(str).apply();
    }

    private boolean expoortBitmap(String str, Bitmap bitmap) {
        boolean createNewFile;
        FileOutputStream fileOutputStream;
        Object obj;
        Exception e;
        Exception e2;
        if (str == null || bitmap == null) {
            return false;
        }
        File file = new File(str);
        if (file.exists() && !file.delete()) {
            return false;
        }
        try {
            createNewFile = file.createNewFile();
        } catch (IOException e3) {
            e3.printStackTrace();
            createNewFile = false;
        }
        boolean compress;
        try {
            fileOutputStream = new FileOutputStream(file);
            try {
                compress = bitmap.compress(CompressFormat.PNG, 100, fileOutputStream);
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    obj = 1;
                } catch (IOException e4) {
                    try {
                        e4.printStackTrace();
                        obj = null;
                    } catch (Exception e5) {
                        e = e5;
                        e.printStackTrace();
                        try {
                            fileOutputStream.flush();
                            fileOutputStream.close();
                        } catch (IOException e6) {
                            e6.printStackTrace();
                        } catch (Throwable unused) {
                            compress = false;
                            fileOutputStream.flush();
                            fileOutputStream.close();
                            obj = null;
                            if (!createNewFile) {
                            }
                            return false;
                        }
                        return false;
                    } catch (Throwable throwable) {
                        try {
                            fileOutputStream.flush();
                            fileOutputStream.close();
                            obj = null;
                        } catch (Exception e7) {
                            e2 = e7;
                            e2.printStackTrace();
                            if (fileOutputStream == null) {
                                try {
                                    fileOutputStream.flush();
                                    fileOutputStream.close();
                                } catch (IOException e62) {
                                    e62.printStackTrace();
                                }
                                obj = 1;
                            } else {
                                obj = null;
                            }
                            compress = false;
                            if (createNewFile) {
                            }
                            return false;
                        }
                    }
                }
            } catch (Exception e8) {
                e = e8;
                compress = false;
                e.printStackTrace();
                fileOutputStream.flush();
                fileOutputStream.close();
                return false;
            } catch (Throwable unused2) {
                compress = false;
                fileOutputStream.flush();
                fileOutputStream.close();
                obj = null;
                if (createNewFile) {
                }
                return false;
            }
        } catch (Exception e9) {
            e2 = e9;
            fileOutputStream = null;
            e2.printStackTrace();
            if (fileOutputStream == null) {
            }
            compress = false;
            if (createNewFile) {
            }
            return false;
        }
        if (createNewFile || !compress || obj == null) {
            return false;
        }
        return true;
    }

}
