package com.letsmake.atoz.design.poster_builder;

import android.os.Parcel;
import android.os.Parcelable;

public class BG_Image implements Parcelable {

     int id;
     String category_name, image_url, thumb_url;

    public int describeContents() {
        return 0;
    }

    protected BG_Image(Parcel parcel) {
        this.id = parcel.readInt();
        this.category_name = parcel.readString();
        this.thumb_url = parcel.readString();
        this.image_url = parcel.readString();
    }

    public BG_Image(int i, String str, String str2, String str3) {
        this.id = i;
        this.thumb_url = str;
        this.image_url = str2;
        this.category_name = str3;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.category_name);
        parcel.writeString(this.thumb_url);
        parcel.writeString(this.image_url);
    }

    public static final Creator<BG_Image> CREATOR = new Creator<BG_Image>() {
        public BG_Image createFromParcel(Parcel parcel) {
            return new BG_Image(parcel);
        }

        public BG_Image[] newArray(int i) {
            return new BG_Image[i];
        }
    };

    public int getId() {
        return this.id;
    }

    public String getBGCategory_name() {
        return this.category_name;
    }

    public void setBGCategory_name(String str) {
        this.category_name = str;
    }

    public void setId(int i) {
        this.id = i;
    }

    public String getThumb_url() {
        return this.thumb_url;
    }

    public void setBGThumb_url(String str) {
        this.thumb_url = str;
    }

    public String getBGImage_url() {
        return this.image_url;
    }

    public void setImage_url(String str) {
        this.image_url = str;
    }

}
