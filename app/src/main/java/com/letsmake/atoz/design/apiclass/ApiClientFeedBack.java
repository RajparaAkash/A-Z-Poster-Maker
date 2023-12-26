package com.letsmake.atoz.design.apiclass;

import com.letsmake.atoz.design.editor_intelligence.AppConstants;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientFeedBack {
    public static final String BASE_URL = AppConstants.BASE_URL_BG;
    private static Retrofit retrofit1 = null;

    public static Retrofit getClient() {
        if (retrofit1 == null) {
            retrofit1 = new Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit1;
    }
}
