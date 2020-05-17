package com.doug.showcase

import android.app.Application
import com.doug.showcase.photo.di.apiModule
import com.doug.showcase.photo.di.repositoryModule
import com.doug.showcase.photo.di.retrofitModule
import com.doug.showcase.photo.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class ShowcaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.DEBUG else Level.ERROR)
            androidContext(this@ShowcaseApp)
            modules(listOf(repositoryModule, viewModelModule, retrofitModule, apiModule))
        }
    }
}
