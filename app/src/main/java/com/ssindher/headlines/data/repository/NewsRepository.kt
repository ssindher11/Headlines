package com.ssindher.headlines.data.repository

import com.ssindher.headlines.data.database.NewsDao
import com.ssindher.headlines.data.model.NewsArticle
import com.ssindher.headlines.data.network.ApiInterface

class NewsRepository(
    private val apiInterface: ApiInterface,
    private val newsDao: NewsDao
) {
    // Network
    fun getAllNewsFromApi() = apiInterface.getAllNews()

    // DB
    fun getAllNewsFromDB() = newsDao.getAllNews()

    suspend fun updateData(list: List<NewsArticle>) = newsDao.updateData(list)
}