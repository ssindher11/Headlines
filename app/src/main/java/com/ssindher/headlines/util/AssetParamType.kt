package com.ssindher.headlines.util

import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson
import com.ssindher.headlines.data.model.NewsArticle

class AssetParamType : NavType<NewsArticle>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): NewsArticle? =
        bundle.getParcelable(key)

    override fun parseValue(value: String): NewsArticle =
        Gson().fromJson(value, NewsArticle::class.java)

    override fun put(bundle: Bundle, key: String, value: NewsArticle) =
        bundle.putParcelable(key, value)
}