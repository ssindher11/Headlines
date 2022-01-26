package com.ssindher.headlines

import android.app.Application
import com.ssindher.headlines.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@App)
            modules(listOf(AppModule().appModule, AppModule().netModule))
        }
    }
}