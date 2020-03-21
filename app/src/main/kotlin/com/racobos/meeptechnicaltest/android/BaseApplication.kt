package com.racobos.meeptechnicaltest.android

import android.app.Application
import com.racobos.meeptechnicaltest.di.androidModule
import com.racobos.meeptechnicaltest.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(dataModule, androidModule)
        }
    }
}