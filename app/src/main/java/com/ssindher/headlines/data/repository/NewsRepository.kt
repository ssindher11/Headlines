package com.ssindher.headlines.data.repository

import com.ssindher.headlines.data.database.NewsDao
import com.ssindher.headlines.data.model.NewsArticle
import com.ssindher.headlines.data.model.NewsDTO
import com.ssindher.headlines.data.network.ApiInterface
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepository(
    private val apiInterface: ApiInterface,
    private val newsDao: NewsDao
) {

    // Network
    suspend fun getAllNewsFromApi(): Response<NewsDTO> = apiInterface.getAllNews()

    // DB
    fun getAllNewsFromDB(): Flow<List<NewsArticle>> = newsDao.getAllNews()

    fun getNewsArticle(articleId: Int): NewsArticle? = newsDao.getNewsArticle(articleId)

    fun updateData(list: List<NewsArticle>) = newsDao.updateData(list)
}