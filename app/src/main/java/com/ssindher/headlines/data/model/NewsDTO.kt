package com.ssindher.headlines.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

data class NewsDTO(
    val articles: List<NewsArticle>? = listOf(),
    val status: String? = "",
    val totalResults: Int? = 0
)

@Parcelize
@Entity(tableName = "newsItems")
data class NewsArticle(
    @PrimaryKey(autoGenerate = true)
    var articleId: Int = 0,

    val author: String? = "",
    val content: String? = "",
    val description: String? = "",
    val publishedAt: String? = "",
    val source: Source? = Source(),
    val title: String? = "",
    val url: String? = "",
    val urlToImage: String? = ""
) : Parcelable

@Parcelize
data class Source(
    val id: String? = "",
    val name: String? = ""
) : Parcelable