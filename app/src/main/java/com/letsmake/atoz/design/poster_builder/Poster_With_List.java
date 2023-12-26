package com.letsmake.atoz.design.poster_builder;

import java.util.ArrayList;

public class Poster_With_List {

    ArrayList<Poster_Data_List> data;

    String error,message;

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

    public ArrayList<Poster_Data_List> getData() {
        return this.data;
    }

    public void setData(ArrayList<Poster_Data_List> arrayList) {
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
