package com.letsmake.atoz.design.poster_builder;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Thumb_BG implements Parcelable {

    Thumb_BG(Parcel parcel) {
        this.category_name = parcel.readString();
    }

    ArrayList<Main_BG_Image> thumbnail_bg;

    String category_name;

    public int describeContents() {
        return 0;
    }

    public String getCategory_name() {
        return this.category_name;
    }

    public void setCategory_name(String str) {
        this.category_name = str;
    }

    public ArrayList<Main_BG_Image> getThumbnail_bg() {
        return this.thumbnail_bg;
    }

    public void setThumbnail_bg(ArrayList<Main_BG_Image> arrayList) {
        this.thumbnail_bg = arrayList;
    }

    public static final Creator<Thumb_BG> CREATOR = new Creator<Thumb_BG>() {
        public Thumb_BG createFromParcel(Parcel parcel) {
            return new Thumb_BG(parcel);
        }

        public Thumb_BG[] newArray(int i) {
            return new Thumb_BG[i];
        }
    };

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.category_name);
    }
}
