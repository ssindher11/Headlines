package com.ssindher.headlines.util

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import org.json.JSONObject
import retrofit2.HttpException

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