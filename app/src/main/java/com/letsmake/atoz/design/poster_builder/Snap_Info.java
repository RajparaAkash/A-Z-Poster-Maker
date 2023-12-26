package com.letsmake.atoz.design.poster_builder;

import java.util.ArrayList;

public class Snap_Info {

    ArrayList<BG_Image> BG_Images;

    int mGravity;
    String mText;

    public Snap_Info(int i, String str, ArrayList<BG_Image> arrayList) {
        this.mGravity = i;
        this.mText = str;
        this.BG_Images = arrayList;
    }

    public String getText() {
        return this.mText;
    }

    public int getGravity() {
        return this.mGravity;
    }

    public ArrayList<BG_Image> getBG_Images() {
        return this.BG_Images;
    }
}
