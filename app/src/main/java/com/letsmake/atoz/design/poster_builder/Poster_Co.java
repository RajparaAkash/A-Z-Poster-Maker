package com.letsmake.atoz.design.poster_builder;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Poster_Co implements Parcelable {

    ArrayList<Text_Info> text_info;
    ArrayList<StickerInfo> sticker_info;

    String cat_id, post_id, post_thumb, ratio, back_image;

    public int describeContents() {
        return 0;
    }

    private Poster_Co(Parcel parcel) {
        this.cat_id = parcel.readString();
        this.ratio = parcel.readString();
        this.back_image = parcel.readString();
        this.post_id = parcel.readString();
        this.post_thumb = parcel.readString();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ClassPojo [cat_id = ");
        stringBuilder.append(this.cat_id);
        stringBuilder.append(", ratio = ");
        stringBuilder.append(this.ratio);
        stringBuilder.append(", back_image = ");
        stringBuilder.append(this.back_image);
        stringBuilder.append(", post_id = ");
        stringBuilder.append(this.post_id);
        stringBuilder.append(", post_thumb = ");
        stringBuilder.append(this.post_thumb);
        stringBuilder.append(", text_info = ");
        stringBuilder.append(this.text_info);
        stringBuilder.append(", sticker_info = ");
        stringBuilder.append(this.sticker_info);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.cat_id);
        parcel.writeString(this.ratio);
        parcel.writeString(this.back_image);
        parcel.writeString(this.post_id);
        parcel.writeString(this.post_thumb);
    }

    public String getPOSTERCat_id() {
        return this.cat_id;
    }

    public void setPOSTERCat_id(String str) {
        this.cat_id = str;
    }

    public String getPOSTERRatio() {
        return this.ratio;
    }

    public void setPOSTERRatio(String str) {
        this.ratio = str;
    }

    public String getPOSTERBack_image() {
        return this.back_image;
    }

    public void setPOSTERBack_image(String str) {
        this.back_image = str;
    }

    public String getPost_id() {
        return this.post_id;
    }

    public void setPost_id(String str) {
        this.post_id = str;
    }

    public String getPOSTERPost_thumb() {
        return this.post_thumb;
    }

    public void setPOSTERPost_thumb(String str) {
        this.post_thumb = str;
    }

    public ArrayList<Text_Info> getPOSTERText_info() {
        return this.text_info;
    }

    public void setPOSTERText_info(ArrayList<Text_Info> arrayList) {
        this.text_info = arrayList;
    }

    public ArrayList<StickerInfo> getPOSTERSticker_info() {
        return this.sticker_info;
    }

    public void setPOSTERSticker_info(ArrayList<StickerInfo> arrayList) {
        this.sticker_info = arrayList;
    }

    public static final Creator<Poster_Co> CREATOR = new Creator<Poster_Co>() {
        public Poster_Co createFromParcel(Parcel parcel) {
            return new Poster_Co(parcel);
        }

        public Poster_Co[] newArray(int i) {
            return new Poster_Co[i];
        }
    };

}
