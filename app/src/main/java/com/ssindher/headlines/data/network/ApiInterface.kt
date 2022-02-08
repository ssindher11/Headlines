package com.ssindher.headlines.data.network

import com.ssindher.headlines.data.model.NewsDTO
import com.ssindher.headlines.util.Constants
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiInterface {

    @GET("top-headlines")
    fun getAllNews(
        @Header("X-Api-Key") apiKey: String = Constants.NEWS_API_KEY,
        @Query("country") country: String = "us"
    ): Single<NewsDTO>
}