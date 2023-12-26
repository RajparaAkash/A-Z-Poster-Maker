package com.letsmake.atoz.design.model;

public class ScratchModel {

    public String ratio;
    public int image;

    ScratchModel(String ratio, int image){
        this.ratio = ratio;
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }
}
