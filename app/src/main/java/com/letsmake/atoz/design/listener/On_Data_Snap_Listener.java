package com.letsmake.atoz.design.listener;

import android.net.Uri;

import java.util.ArrayList;

import com.letsmake.atoz.design.poster_builder.BG_Image;

public interface On_Data_Snap_Listener {
    void onSnapFilter(int i, int i2, String str, String str2);

    void onSnapFilter(Uri uri, int i, int i2, int i3, int i4, boolean z);

    void onSnapFilter(ArrayList<BG_Image> arrayList, int i, String str);
}
