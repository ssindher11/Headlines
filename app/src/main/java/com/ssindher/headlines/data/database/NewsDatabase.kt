package com.ssindher.headlines.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ssindher.headlines.data.model.NewsDTO

@Database(
    entities = [NewsDTO::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun getNewsDao(): NewsDao
}