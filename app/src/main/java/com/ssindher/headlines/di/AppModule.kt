package com.ssindher.headlines.di

import androidx.room.Room
import com.ssindher.headlines.data.database.NewsDatabase
import com.ssindher.headlines.data.network.ApiInterface
import com.ssindher.headlines.data.repository.NewsRepository
import com.ssindher.headlines.ui.details.NewsDetailsViewModel
import com.ssindher.headlines.ui.home.HomeViewModel
import com.ssindher.headlines.util.ConnectionLiveData
import com.ssindher.headlines.util.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppModule {

    val appModule = module {
        single {
            Room.databaseBuilder(get(), NewsDatabase::class.java, "news.db")
                .fallbackToDestructiveMigration()
                .build()
        }
        single { get<NewsDatabase>().getNewsDao() }

        single { ConnectionLiveData(androidContext()) }
        single { NewsRepository(get(), get()) }
        viewModel { HomeViewModel(get()) }
        viewModel { NewsDetailsViewModel(get()) }
    }

    val netModule = module {
        single {
            val interceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            val networkInterceptor = Interceptor { chain ->
                val requestBuilder: Request.Builder = chain.request().newBuilder()
                requestBuilder.header("X-Api-Key", Constants.NEWS_API_KEY)
                chain.proceed(requestBuilder.build())
            }
            OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addNetworkInterceptor(networkInterceptor)
        }
        factory {
            Retrofit.Builder()
                .client(get())
                .baseUrl(Constants.NEWS_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        single { get<Retrofit>().create(ApiInterface::class.java) }
    }
}