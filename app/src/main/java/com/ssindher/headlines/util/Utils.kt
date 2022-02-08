package com.ssindher.headlines.util

import android.content.Context
import android.net.ConnectivityManager
import org.json.JSONObject
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.*

inline val Context.connectivityManager: ConnectivityManager
    get() = getSystemService(ConnectivityManager::class.java)

fun getErrorMessage(e: Throwable): String {
    return if (e is HttpException) {
        val error = e
        val errorBody = error.response()?.errorBody()!!.string()
        try {
            val json = JSONObject(errorBody)
            json.getString("message")
        } catch (e: Exception) {
            "Something went wrong"
        }
    } else {
        e.printStackTrace()
        e.localizedMessage ?: ""
    }
}

fun getFormattedDate(dateString: String) : String {
    val sdf1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val date = sdf1.parse(dateString)
    val sdf2 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf2.format(date!!)
}

fun getValidImageUrl(url: String): String {
    if (url.startsWith("http:")) {
        url.replaceFirst("http:", "https:")
    }
    return url
}