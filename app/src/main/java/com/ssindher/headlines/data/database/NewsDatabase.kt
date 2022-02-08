package com.ssindher.headlines.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ssindher.headlines.data.model.ArticleSourceConvertor
import com.ssindher.headlines.data.model.NewsArticle

@Database(
    entities = [NewsArticle::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ArticleSourceConvertor::class)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun getNewsDao(): NewsDao
}