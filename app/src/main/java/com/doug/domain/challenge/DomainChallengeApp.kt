package com.doug.domain.challenge

import android.app.Application
import com.doug.domain.challenge.di.apiModule
import com.doug.domain.challenge.di.repositoryModule
import com.doug.domain.challenge.di.retrofitModule
import com.doug.domain.challenge.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class DomainChallengeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.DEBUG else Level.ERROR)
            androidContext(this@DomainChallengeApp)
            modules(listOf(repositoryModule, viewModelModule, retrofitModule, apiModule))
        }
    }
}
