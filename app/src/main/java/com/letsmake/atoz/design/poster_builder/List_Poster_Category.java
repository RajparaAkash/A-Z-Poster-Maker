package com.letsmake.atoz.design.poster_builder;

import java.util.ArrayList;

public class List_Poster_Category {

    ArrayList<Poster_Info> data;
    String message, error;

    String getMessage() {
        return this.message;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public String getError() {
        return this.error;
    }

    public void setError(String str) {
        this.error = str;
    }

    public ArrayList<Poster_Info> getData() {
        return this.data;
    }

    public void setData(ArrayList<Poster_Info> arrayList) {
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
