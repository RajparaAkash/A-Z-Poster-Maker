package com.letsmake.atoz.design.poster_builder;

import android.os.Parcel;
import android.os.Parcelable;

public class Text_Info implements Parcelable {


    String font_family, text, txt_width, txt_x_pos, text_id, txt_y_pos, txt_color, txt_height, txt_order, txt_rotation;

    public int describeContents() {
        return 0;
    }

    public Text_Info(Parcel parcel) {
        this.txt_height = parcel.readString();
        this.txt_width = parcel.readString();
        this.text = parcel.readString();
        this.txt_x_pos = parcel.readString();
        this.font_family = parcel.readString();
        this.txt_y_pos = parcel.readString();
        this.txt_order = parcel.readString();
        this.text_id = parcel.readString();
        this.txt_rotation = parcel.readString();
        this.txt_color = parcel.readString();
    }

    public static final Creator<Text_Info> CREATOR = new Creator<Text_Info>() {
        public Text_Info createFromParcel(Parcel parcel) {
            return new Text_Info(parcel);
        }

        public Text_Info[] newArray(int i) {
            return new Text_Info[i];
        }
    };

    public String getTxt_height() {
        return this.txt_height;
    }

    public void setTxt_height(String str) {
        this.txt_height = str;
    }

    public String getTxt_width() {
        return this.txt_width;
    }

    public void setTxt_width(String str) {
        this.txt_width = str;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String str) {
        this.text = str;
    }

    public String getTxt_x_pos() {
        return this.txt_x_pos;
    }

    public void setTxt_x_pos(String str) {
        this.txt_x_pos = str;
    }

    public String getFont_family() {
        return this.font_family;
    }

    public void setFont_family(String str) {
        this.font_family = str;
    }

    public String getTxt_y_pos() {
        return this.txt_y_pos;
    }

    public void setTxt_y_pos(String str) {
        this.txt_y_pos = str;
    }

    public String getTxt_order() {
        return this.txt_order;
    }

    public void setTxt_order(String str) {
        this.txt_order = str;
    }

    public String getText_id() {
        return this.text_id;
    }

    public void setText_id(String str) {
        this.text_id = str;
    }

    public String getTxt_rotation() {
        return this.txt_rotation;
    }

    public void setTxt_rotation(String str) {
        this.txt_rotation = str;
    }

    public String getTxt_color() {
        return this.txt_color;
    }

    public void setTxt_color(String str) {
        this.txt_color = str;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ClassPojo [txt_height = ");
        stringBuilder.append(this.txt_height);
        stringBuilder.append(", txt_width = ");
        stringBuilder.append(this.txt_width);
        stringBuilder.append(", text = ");
        stringBuilder.append(this.text);
        stringBuilder.append(", txt_x_pos = ");
        stringBuilder.append(this.txt_x_pos);
        stringBuilder.append(", font_family = ");
        stringBuilder.append(this.font_family);
        stringBuilder.append(", txt_y_pos = ");
        stringBuilder.append(this.txt_y_pos);
        stringBuilder.append(", txt_order = ");
        stringBuilder.append(this.txt_order);
        stringBuilder.append(", text_id = ");
        stringBuilder.append(this.text_id);
        stringBuilder.append(", txt_rotation = ");
        stringBuilder.append(this.txt_rotation);
        stringBuilder.append(", txt_color = ");
        stringBuilder.append(this.txt_color);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.txt_height);
        parcel.writeString(this.txt_width);
        parcel.writeString(this.text);
        parcel.writeString(this.txt_x_pos);
        parcel.writeString(this.font_family);
        parcel.writeString(this.txt_y_pos);
        parcel.writeString(this.txt_order);
        parcel.writeString(this.text_id);
        parcel.writeString(this.txt_rotation);
        parcel.writeString(this.txt_color);
    }
}
