package com.doug.domain.challenge.di

import com.doug.domain.challenge.BuildConfig
import com.doug.domain.challenge.network.DomainApi
import com.doug.domain.challenge.repository.PropertyRepository
import com.doug.domain.challenge.ui.properties.PropertiesViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Koin modules responsible for creating the dependencies and inject where necessary.
 */

val viewModelModule = module {
    viewModel {
        PropertiesViewModel(get())
    }
}

val repositoryModule = module {
    single {
        PropertyRepository(get())
    }
}

val apiModule = module {
    fun provideDomainApi(retrofit: Retrofit): DomainApi {
        return retrofit.create(DomainApi::class.java)
    }
    single { provideDomainApi(get()) }
}

val retrofitModule = module {

    fun provideMoshi(): Moshi = Moshi
        .Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return loggingInterceptor
    }


    fun provideHttpClient(
        interceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient().newBuilder()
        .addInterceptor(interceptor)
        .build()

    fun provideRetrofit(moshi: Moshi, client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(DomainApi.URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(client)
        .build()

    single { provideMoshi() }
    single { provideHttpLoggingInterceptor() }
    single { provideHttpClient(get()) }
    single { provideRetrofit(get(), get()) }
}
