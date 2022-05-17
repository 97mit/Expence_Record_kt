package com.stimednp.roommvvm.di

import com.stimednp.roommvvm.data.db.entity.NewResponse
import com.stimednp.roommvvm.network.ApiInterface
import retrofit2.Response
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    val topHeadlinesApi: ApiInterface
) {

    suspend fun getTopHeadlines(country: String, apiKey: String): Response<NewResponse> {
        return topHeadlinesApi.getTopHeadlines(country, apiKey)
    }

}