package com.ssindher.headlines.data.database

import androidx.room.*
import com.ssindher.headlines.data.model.NewsArticle
import com.ssindher.headlines.data.model.NewsDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Query("SELECT * from newsItems")
    fun getAllNews(): Flow<List<NewsArticle>>

    @Query("SELECT * from newsItems where articleId = :articleId")
    fun getNewsArticle(articleId: Int): NewsArticle?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertArticles(list: List<NewsArticle>)

    @Query("DELETE from newsItems")
    fun deleteAllArticles()

    @Transaction
    fun updateData(list: List<NewsArticle>) {
        deleteAllArticles()
        insertArticles(list)
    }
}