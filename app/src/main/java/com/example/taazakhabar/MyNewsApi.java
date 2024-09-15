package com.example.taazakhabar;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MyNewsApi {
    @GET("top-headlines")
    Call<Mynewsmodel> getTopHeadlines(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );
}
