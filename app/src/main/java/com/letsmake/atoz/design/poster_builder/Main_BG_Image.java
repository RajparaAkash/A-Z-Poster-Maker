package com.letsmake.atoz.design.poster_builder;

import java.util.ArrayList;

public class Main_BG_Image {

     ArrayList<BG_Image> category_list;

     String category_name;
     int category_id;

    public Main_BG_Image(int i, String str, ArrayList<BG_Image> arrayList) {
        this.category_id = i;
        this.category_name = str;
        this.category_list = arrayList;
    }

    public int getCategory_id() {
        return this.category_id;
    }

    public void setCategory_id(int i) {
        this.category_id = i;
    }

    public String getCategory_name() {
        return this.category_name;
    }

    public void setCategory_name(String str) {
        this.category_name = str;
    }

    public ArrayList<BG_Image> getCategory_list() {
        return this.category_list;
    }

    public void setCategory_list(ArrayList<BG_Image> arrayList) {
        this.category_list = arrayList;
    }
}
