package com.letsmake.atoz.design.poster_builder;

import java.util.ArrayList;

public class Poster_Datas {

    ArrayList<Poster_Co> data;

    String error, message;

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

    public String getMessage() {
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

    public ArrayList<Poster_Co> getData() {
        return this.data;
    }

    public void setData(ArrayList<Poster_Co> arrayList) {
        this.data = arrayList;
    }

}
