package com.letsmake.atoz.design.poster_builder;

import android.os.Parcel;
import android.os.Parcelable;

public class StickerInfo implements Parcelable {

    public static final Creator<StickerInfo> CREATOR = new Creator<StickerInfo>() {
        public StickerInfo createFromParcel(Parcel parcel) {
            return new StickerInfo(parcel);
        }

        public StickerInfo[] newArray(int i) {
            return new StickerInfo[i];
        }
    };

    String st_y_rotateprog, sticker_id, st_y_pos, st_field1, st_field2, st_field3, st_image, st_hue, st_field4, st_height, st_z_rotateprog, st_y_rotation, st_x_rotateprog, st_order, st_res_id, st_width, st_x_pos, st_res_uri, st_scale_prog, st_rotation;

    public int describeContents() {
        return 0;
    }

    private StickerInfo(Parcel parcel) {
        this.st_order = parcel.readString();
        this.st_image = parcel.readString();
        this.st_rotation = parcel.readString();
        this.st_height = parcel.readString();
        this.st_y_pos = parcel.readString();
        this.st_x_pos = parcel.readString();
        this.st_res_uri = parcel.readString();
        this.st_width = parcel.readString();
        this.sticker_id = parcel.readString();
        this.st_res_id = parcel.readString();
        this.st_y_rotation = parcel.readString();
        this.st_x_rotateprog = parcel.readString();
        this.st_y_rotateprog = parcel.readString();
        this.st_z_rotateprog = parcel.readString();
        this.st_scale_prog = parcel.readString();
        this.st_hue = parcel.readString();
        this.st_field1 = parcel.readString();
        this.st_field2 = parcel.readString();
        this.st_field3 = parcel.readString();
        this.st_field4 = parcel.readString();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ClassPojo [st_order = ");
        stringBuilder.append(this.st_order);
        stringBuilder.append(", st_image = ");
        stringBuilder.append(this.st_image);
        stringBuilder.append(", st_rotation = ");
        stringBuilder.append(this.st_rotation);
        stringBuilder.append(", st_height = ");
        stringBuilder.append(this.st_height);
        stringBuilder.append(", st_y_pos = ");
        stringBuilder.append(this.st_y_pos);
        stringBuilder.append(", st_x_pos = ");
        stringBuilder.append(this.st_x_pos);
        stringBuilder.append(", st_res_uri = ");
        stringBuilder.append(this.st_res_uri);
        stringBuilder.append(", st_width = ");
        stringBuilder.append(this.st_width);
        stringBuilder.append(", sticker_id = ");
        stringBuilder.append(this.sticker_id);
        stringBuilder.append(", st_res_id = ");
        stringBuilder.append(this.st_res_id);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }


    public void setSt_hue(String str) {
        this.st_hue = str;
    }

    public String getSt_field1() {
        return this.st_field1;
    }

    public void setSt_field1(String str) {
        this.st_field1 = str;
    }

    public String getSTICKER_field2() {
        return this.st_field2;
    }

    public void setSt_field2(String str) {
        this.st_field2 = str;
    }

    public String getSt_field3() {
        return this.st_field3;
    }

    public void setSt_field3(String str) {
        this.st_field3 = str;
    }

    public String getSt_field4() {
        return this.st_field4;
    }

    public void setSt_field4(String str) {
        this.st_field4 = str;
    }

    public String getSt_order() {
        return this.st_order;
    }

    public void setSt_order(String str) {
        this.st_order = str;
    }


    public String getSt_res_id() {
        return this.st_res_id;
    }

    public void setSt_res_id(String str) {
        this.st_res_id = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.st_order);
        parcel.writeString(this.st_image);
        parcel.writeString(this.st_rotation);
        parcel.writeString(this.st_height);
        parcel.writeString(this.st_y_pos);
        parcel.writeString(this.st_x_pos);
        parcel.writeString(this.st_res_uri);
        parcel.writeString(this.st_width);
        parcel.writeString(this.sticker_id);
        parcel.writeString(this.st_res_id);
        parcel.writeString(this.st_y_rotation);
        parcel.writeString(this.st_x_rotateprog);
        parcel.writeString(this.st_y_rotateprog);
        parcel.writeString(this.st_z_rotateprog);
        parcel.writeString(this.st_scale_prog);
        parcel.writeString(this.st_hue);
        parcel.writeString(this.st_field1);
        parcel.writeString(this.st_field2);
        parcel.writeString(this.st_field3);
        parcel.writeString(this.st_field4);
    }

    public String getSt_y_rotation() {
        return this.st_y_rotation;
    }

    public void setSt_y_rotation(String str) {
        this.st_y_rotation = str;
    }

    public String getSt_x_rotateprog() {
        return this.st_x_rotateprog;
    }

    public void setSt_x_rotateprog(String str) {
        this.st_x_rotateprog = str;
    }

    public String getSt_y_rotateprog() {
        return this.st_y_rotateprog;
    }

    public void setSt_y_rotateprog(String str) {
        this.st_y_rotateprog = str;
    }

    public String getSt_z_rotateprog() {
        return this.st_z_rotateprog;
    }

    public void setSt_z_rotateprog(String str) {
        this.st_z_rotateprog = str;
    }

    public String getSt_scale_prog() {
        return this.st_scale_prog;
    }

    public void setSt_scale_prog(String str) {
        this.st_scale_prog = str;
    }

    public String getSt_hue() {
        return this.st_hue;
    }

    public String getSTICKER_image() {
        return this.st_image;
    }

    public void setSTICKER_image(String str) {
        this.st_image = str;
    }

    public String getSTICKER_rotation() {
        return this.st_rotation;
    }

    public void setSTICKER_rotation(String str) {
        this.st_rotation = str;
    }

    public String getSTICKER_height() {
        return this.st_height;
    }

    public void setSt_height(String str) {
        this.st_height = str;
    }

    public String getSTICKER_y_pos() {
        return this.st_y_pos;
    }

    public void setSt_y_pos(String str) {
        this.st_y_pos = str;
    }

    public String getSTICKER_x_pos() {
        return this.st_x_pos;
    }

    public void setSt_x_pos(String str) {
        this.st_x_pos = str;
    }

    public String getSt_res_uri() {
        return this.st_res_uri;
    }

    public void setSt_res_uri(String str) {
        this.st_res_uri = str;
    }

    public String getSTICKER_width() {
        return this.st_width;
    }

    public void setSt_width(String str) {
        this.st_width = str;
    }

    public String getSticker_id() {
        return this.sticker_id;
    }

    public void setSticker_id(String str) {
        this.sticker_id = str;
    }

}
