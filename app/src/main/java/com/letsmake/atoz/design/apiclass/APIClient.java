package com.letsmake.atoz.design.apiclass;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static Retrofit retrofit;

    public static Retrofit getClient(String str) {
        Gson create = new GsonBuilder().setLenient().create();
        OkHttpClient build = new Builder().connectTimeout(60000, TimeUnit.MILLISECONDS).build();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .client(build)
                    .baseUrl(str)
                    .addConverterFactory(GsonConverterFactory.create(create)).build();
        }
        return retrofit;
    }
}
