package com.letsmake.atoz.design.poster_builder;

import android.os.Parcel;
import android.os.Parcelable;

public class Full_Poster_Thumb implements Parcelable {

    int post_id, pro;
    String post_thumb, ratio;

    public static final Creator<Full_Poster_Thumb> CREATOR = new Creator<Full_Poster_Thumb>() {
        public Full_Poster_Thumb createFromParcel(Parcel parcel) {
            return new Full_Poster_Thumb(parcel);
        }

        public Full_Poster_Thumb[] newArray(int i) {
            return new Full_Poster_Thumb[i];
        }
    };

    public int describeContents() {
        return 0;
    }

    private Full_Poster_Thumb(Parcel parcel) {
        this.post_id = parcel.readInt();
        this.pro = parcel.readInt();
        this.post_thumb = parcel.readString();
        this.ratio = parcel.readString();
    }

    public int getPost_id() {
        return this.post_id;
    }

    public void setPost_id(int i) {
        this.post_id = i;
    }

    public int getPRO() {
        return this.pro;
    }

    public void setPRO(int i) {
        this.pro = i;
    }

    public String get_POST_Ratio() {
        return ratio;
    }

    public void set_POSTER_Ratio(String ratio) {
        this.ratio = ratio;
    }

    public String getPost_thumb() {
        return this.post_thumb;
    }

    public void setPost_thumb(String str) {
        this.post_thumb = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.post_id);
        parcel.writeString(this.post_thumb);
        parcel.writeString(this.ratio);
    }
}
