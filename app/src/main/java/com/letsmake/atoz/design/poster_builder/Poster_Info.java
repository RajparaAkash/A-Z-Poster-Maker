package com.letsmake.atoz.design.poster_builder;

public class Poster_Info {

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

     String cat_id, cat_name, thumb_img;

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

    public String getPOSTERCat_name() {
        return this.cat_name;
    }

    public void setPOSTERCat_name(String str) {
        this.cat_name = str;
    }

}
