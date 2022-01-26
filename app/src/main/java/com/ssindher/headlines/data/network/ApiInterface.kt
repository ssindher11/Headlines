package com.ssindher.headlines.data.network

import com.ssindher.headlines.data.model.NewsDTO
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("v2/everything")
    suspend fun getAllNews(): Response<NewsDTO>
}