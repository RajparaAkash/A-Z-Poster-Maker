package com.letsmake.atoz.design.editor_intelligence;

import android.app.Activity;

import java.util.ArrayList;

import com.letsmake.atoz.design.poster_builder.BG_Image;

public interface OnClickCallback<T, P, A, V, Q> {
    void onClickCallBack(ArrayList<String> arrayList, ArrayList<BG_Image> arrayList2, String str, Activity activity, String str2);
}
