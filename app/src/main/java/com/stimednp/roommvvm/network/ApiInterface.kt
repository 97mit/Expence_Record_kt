package com.stimednp.roommvvm.network

import com.stimednp.roommvvm.data.db.entity.NewResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String,
    ): Response<NewResponse>
}