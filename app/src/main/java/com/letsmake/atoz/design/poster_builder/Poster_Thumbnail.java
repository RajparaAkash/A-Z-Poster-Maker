package com.letsmake.atoz.design.poster_builder;

import java.util.ArrayList;

public class Poster_Thumbnail {

    ArrayList<List_Poster_Thumb> data;

    String message, error;

    public String getPOSTERMessage() {
        return this.message;
    }

    public void setPOSTERMessage(String str) {
        this.message = str;
    }

    public String getPOSTERError() {
        return this.error;
    }

    public void setPOSTERError(String str) {
        this.error = str;
    }

    public ArrayList<List_Poster_Thumb> getPOSTERData() {
        return this.data;
    }

    public void setPOSTERData(ArrayList<List_Poster_Thumb> arrayList) {
        this.data = arrayList;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ClassPojo [message = ");
        stringBuilder.append(this.message);
        stringBuilder.append(", error = ");
        stringBuilder.append(this.error);
        stringBuilder.append(", data = ");
        stringBuilder.append(this.data);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
