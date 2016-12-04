package com.systemcare.systemcare.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by aline on 02/12/2016.
 */

public class ApiClient {

    public static final String BASE_URL = "http://192.168.33.249:55139/";
    private  static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        }

        return retrofit;
    }
}
