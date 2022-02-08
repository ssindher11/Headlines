package com.ssindher.headlines.data.database

import androidx.room.*
import com.ssindher.headlines.data.model.NewsArticle
import io.reactivex.rxjava3.core.Single

@Dao
interface NewsDao {

    @Query("SELECT * from newsItems")
    fun getAllNews(): Single<List<NewsArticle>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArticles(list: List<NewsArticle>)

    @Query("DELETE from newsItems")
    suspend fun deleteAllArticles()

    @Transaction
    suspend fun updateData(list: List<NewsArticle>) {
        deleteAllArticles()
        insertArticles(list)
    }
}