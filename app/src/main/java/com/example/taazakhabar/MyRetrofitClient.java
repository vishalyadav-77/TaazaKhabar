package com.example.taazakhabar;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//THIS IS RETROFIT CONFIG CLASS
public class MyRetrofitClient {
    private static final String BASE_URL = "https://newsapi.org/v2/"; // Base URL for the API
    private static Retrofit retrofit;

    public static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
