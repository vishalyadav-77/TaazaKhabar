package com.example.taazakhabar;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MyNewsApi {
    @GET("top-headlines")
    Call<Mynewsmodel> getTopHeadlines(
            @Query("country") String country,
            @Query("category") String category,  // Optional category
            @Query("apiKey") String apiKey
    );

    @GET("everything")
    Call<Mynewsmodel> getEverything(
            @Query("q") String query,
            @Query("apiKey") String apiKey
    );
}
