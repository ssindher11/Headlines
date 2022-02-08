package com.ssindher.headlines.data.model

import androidx.room.TypeConverter
import com.google.gson.Gson

class ArticleSourceConvertor {

    private val gson = Gson()

    @TypeConverter
    fun toSource(sourceString: String): Source = gson.fromJson(sourceString, Source::class.java)

    @TypeConverter
    fun fromSource(source: Source): String = gson.toJson(source)
}