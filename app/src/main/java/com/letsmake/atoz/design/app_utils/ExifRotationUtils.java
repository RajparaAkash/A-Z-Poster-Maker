package com.letsmake.atoz.design.app_utils;

import android.media.ExifInterface;
import android.text.TextUtils;

import com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBar;


public class ExifRotationUtils {
    private ExifRotationUtils() {
    }

    public static int getExifRotation(String str) {
        try {
            str = new ExifInterface(str).getAttribute("Orientation");
            if (TextUtils.isEmpty(str)) {
                return 0;
            }
            int parseInt = Integer.parseInt(str);
            if (parseInt == 3) {
                return 180;
            }
            if (parseInt == 6) {
                return 90;
            }
            if (parseInt != 8) {
                return 0;
            }
            return VerticalSeekBar.ROTATION_ANGLE_CW_270;
        } catch (Exception unused) {
            return 0;
        }
    }
}
