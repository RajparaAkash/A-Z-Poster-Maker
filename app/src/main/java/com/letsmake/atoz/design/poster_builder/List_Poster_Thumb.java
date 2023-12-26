package com.letsmake.atoz.design.poster_builder;

public class List_Poster_Thumb {
    String back_image, cat_id, post_id, post_thumb, ratio;

    public String getCat_id() {
        return this.cat_id;
    }

    public void setCat_id(String str) {
        this.cat_id = str;
    }

    public String getRatio() {
        return this.ratio;
    }

    public void setRatio(String str) {
        this.ratio = str;
    }

    public String getBack_image() {
        return this.back_image;
    }

    public void setBack_image(String str) {
        this.back_image = str;
    }

    public String getPost_id() {
        return this.post_id;
    }

    public void setPost_id(String str) {
        this.post_id = str;
    }

    public String getPost_thumb() {
        return this.post_thumb;
    }

    public void setPost_thumb(String str) {
        this.post_thumb = str;
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
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
