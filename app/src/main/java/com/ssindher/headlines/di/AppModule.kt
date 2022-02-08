package com.ssindher.headlines.di

import androidx.room.Room
import com.ssindher.headlines.data.database.NewsDatabase
import com.ssindher.headlines.data.network.ApiInterface
import com.ssindher.headlines.data.repository.NewsRepository
import com.ssindher.headlines.ui.home.HomeViewModel
import com.ssindher.headlines.util.ConnectionLiveData
import com.ssindher.headlines.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
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
    }

    val netModule = module {
        single {
            val interceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            OkHttpClient.Builder().addInterceptor(interceptor).build()
        }
        factory {
            Retrofit.Builder()
                .client(get())
                .baseUrl(Constants.NEWS_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
        }
        single { get<Retrofit>().create(ApiInterface::class.java) }
    }
}