package com.letsmake.atoz.design.poster_builder;

import java.util.ArrayList;

public class Poster_Data_List {

    ArrayList<Full_Poster_Thumb> poster_list;

    String thumb_img, cat_name, cat_id;

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ClassPojo [cat_id = ");
        stringBuilder.append(this.cat_id);
        stringBuilder.append(", thumb_img = ");
        stringBuilder.append(this.thumb_img);
        stringBuilder.append(", cat_name = ");
        stringBuilder.append(this.cat_name);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public String getPOSTERCat_name() {
        return this.cat_name;
    }

    public void setPOSTERCat_name(String str) {
        this.cat_name = str;
    }

    public ArrayList<Full_Poster_Thumb> getPoster_list() {
        return this.poster_list;
    }

    public String getPOSTERCat_id() {
        return this.cat_id;
    }

    public void setPOSTERCat_id(String str) {
        this.cat_id = str;
    }

    public String getPOSTERThumb_img() {
        return this.thumb_img;
    }

    public void setPOSTERThumb_img(String str) {
        this.thumb_img = str;
    }

    public void setPoster_list(ArrayList<Full_Poster_Thumb> arrayList) {
        this.poster_list = arrayList;
    }

}
